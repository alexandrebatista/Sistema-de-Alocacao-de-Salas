package br.ufrj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class CadastroProfessor {
	
	public ArrayList<String> incluir(FormProfessor professor) throws IOException {
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroProfessor.jsp");
		try{
			verificaExcecoes(professor, false);
			escreveProfessor(constroiString(professor), true);			
			resultado.add("Professor incluído!");
		}
		catch(Exception e) {
			resultado.add(e.getMessage());
		}		
		return resultado;
	}
	
	public void verificaExcecoes(FormProfessor professor, boolean altera) throws Exception {
		if(altera == false)
			if(! consultar(professor).get(1).equals("Professor não encontrado!") && ! consultar(professor).get(1).equals("Professor não encontrado - Lista vazia!")) 
				throw new Exception("Nome já existe!");	
		if(! professor.getEmail().equals(""))
			verificaEmail(professor.getEmail());
	}
	
	public void verificaEmail(String email) throws Exception {
		boolean charEncontrado = false;
		int contaDigitosAntes = 0;
		int contaDigitosDepois = 0;
		for(int i = 0; i < email.length() - 1; i++ ) {
			if(email.charAt(i) == '@') 
				charEncontrado = true;
			if(charEncontrado == false)
				contaDigitosAntes++;
			if(charEncontrado)
				contaDigitosDepois++;
		}
		if(! charEncontrado || contaDigitosAntes == 0 || contaDigitosDepois < 3)
			throw new Exception("Email inválido!");
	}
	
	public String constroiString(FormProfessor professor) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("\"" + professor.getNome() + "\",");
		sb.append("\"" + professor.getEmail() + "\"");
		return sb.toString();
    }
	
	public void escreveProfessor(String sb, boolean manter) throws IOException {
		PrintWriter print = new PrintWriter(new FileWriter("listaProfessores.csv", manter)); 
		print.println(sb);
		print.close();
	}
	
	public ArrayList<String> remover(FormProfessor professor) throws IOException {
		String sb = constroiString(professor);
		boolean removido = false;
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroProfessor.jsp");
		ArrayList<String> arquivo = new ArrayList<String>();
		String linha;
		Scanner scanner = new Scanner(new File("listaProfessores.csv"));
		scanner.useDelimiter("[\n]");
		while (scanner.hasNext()) {
			linha = scanner.nextLine();
			if(! sb.equalsIgnoreCase(linha)) { 
				arquivo.add(linha);
			}
			else
				removido = true;
		}
		scanner.close();
		if(arquivo.size() == 0){
			File file = new File("listaProfessores.csv");
			file.delete();	
		}	
		else {
			escreveProfessor(arquivo.get(0), false);
			for(int i = 1; i < arquivo.size(); i++) 
				escreveProfessor(arquivo.get(i), true);
		}
		if (removido)
			resultado.add("Professor removido!");
		else
			resultado.add("Professor não encontrado!");
		
		return resultado;
			
	}

	public String removeN(String email) {
		StringBuilder semN = new StringBuilder();
		for(int i = 0; i < email.length() - 1; i++ ) {
			semN = semN.append(email.charAt(i));
		}
		return semN.toString();
	}
	
	public String removeChar(String elemento, int k) {
		StringBuilder semChar = new StringBuilder();
		for(int i = 0; i < elemento.length(); i++ ) {
			if (i != k)
				semChar = semChar.append(elemento.charAt(i));
		}
		return semChar.toString();
	}
	
	public ArrayList<String> consultar(FormProfessor professor) throws FileNotFoundException {
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroProfessor.jsp");
		String email;
		File file = new File("listaProfessores.csv");
		if(file.exists()) {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\",\"|\n");
			while(scanner.hasNext()) {
				if (professor.getNome().equalsIgnoreCase(scanner.next().substring(1))) {	
					email = removeN(scanner.next());
					professor.setEmail(removeChar(email, email.length() - 1));					
					scanner.close();
					resultado.add(professor.toString());
					return resultado;				
				}
				else
					scanner.nextLine();
			}
			scanner.close();
			resultado.add("Professor não encontrado!");
			return resultado;
		}
		resultado.add("Professor não encontrado - Lista vazia!");
		return resultado;
	}
	
	public ArrayList<String> alterar(FormProfessor professor) throws IOException {
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroProfessor.jsp");	
		try {
			String emailNovo = professor.getEmail();	
			if (! consultar(professor).get(1).equals("Professor não encontrado!")) {
				remover(professor);
				professor.setEmail(emailNovo);
				escreveProfessor(constroiString(professor), true);
				resultado.add("Professor alterado!");
				return resultado;				
			}
			else {
				resultado.add("Professor não encontrado!");
				return resultado;
			}	
		}
		catch(Exception e) {
			resultado.add(e.getMessage());
			return resultado;
		}
		
	}	
	
	public Map<String, ArrayList<Object>> visualizar() throws FileNotFoundException {
		File file = new File("listaProfessores.csv");
		Map<String, ArrayList<Object>> saida = new TreeMap<>();
		ArrayList<Object> resultado = new ArrayList<Object>();
		if(file.exists()) {
			Scanner scanner = new Scanner(file);
			String email;
			scanner.useDelimiter("\",\"|\n");
			while(scanner.hasNext()) {
				FormProfessor professor = new FormProfessor();
				professor.setNome(removeChar(scanner.next(), 0));
				email = removeN(scanner.next());
				professor.setEmail(removeChar(email, email.length() - 1));
				resultado.add(professor);
			}
			scanner.close();
			saida.put("listaProfessores.jsp", resultado);
			return saida;
		}
		else {
			resultado.add("Lista vazia!");
			saida.put("cadastroProfessor.jsp", resultado);
			return saida;
		}
	}
}
