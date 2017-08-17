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

public class CadastroDisciplina {
	
	public ArrayList<String> incluir(FormDisciplina disciplina) throws IOException {
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroDisciplina.jsp");
		try {
			verificaExcecoes(disciplina);
			escreveDisciplina(constroiString(disciplina), true);
			resultado.add("Disciplina incluída!");
		}
		catch(Exception e) {
			resultado.add(e.getMessage());
		}
		return resultado;
	}
	
	public void verificaExcecoes(FormDisciplina disciplina) throws Exception {
		if(! consultar(disciplina).get(1).equals("Disciplina não encontrada!") && ! consultar(disciplina).get(1).equals("Disciplina não encontrada - Lista vazia!")) 
			throw new Exception("Código já existe!");	
	}
	
	public String constroiString(FormDisciplina disciplina) {
		StringBuilder sb = new StringBuilder();
    	sb.append("\"" + disciplina.getCodigo() + "\",");
		sb.append("\"" + disciplina.getNome() + "\",");
		sb.append("\"" + disciplina.getEmenta() + "\"");
		return sb.toString();
	}
	
	public void escreveDisciplina(String sb, boolean manter) throws IOException {
		PrintWriter print = new PrintWriter(new FileWriter("listaDisciplinas.csv", manter)); 
		print.println(sb);
		print.close();
	}
	
	public ArrayList<String> remover(FormDisciplina disciplina) throws IOException {
		String sb = constroiString(disciplina);
		boolean removido = false;
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroDisciplina.jsp");
		ArrayList<String> arquivo = new ArrayList<String>();
		String linha;
		Scanner scanner = new Scanner(new File("listaDisciplinas.csv"));
		scanner.useDelimiter("[\n]");
		while (scanner.hasNext()) {
			linha = scanner.nextLine();
			if(! sb.equals(linha)) { 
				arquivo.add(linha);
			}
			else
				removido = true;
		}
		scanner.close();
		if(arquivo.size() == 0){
			File file = new File("listaDisciplinas.csv");
			file.delete();	
		}	
		else {
			escreveDisciplina(arquivo.get(0), false);
			for(int i = 1; i < arquivo.size(); i++) 
				escreveDisciplina(arquivo.get(i), true);
		}
		if (removido)
			resultado.add("Disciplina removida!");
		else
			resultado.add("Disciplina não encontrada!");
		
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

	public ArrayList<String> consultar(FormDisciplina disciplina) throws FileNotFoundException {
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroDisciplina.jsp");
		String ementa;
		File file = new File("listaDisciplinas.csv");
		if(file.exists()) {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\",\"|\n");
			while(scanner.hasNext()) {
				if (disciplina.getCodigo().equals(scanner.next().substring(1))) {
					disciplina.setNome(scanner.next());
					ementa = removeN(scanner.next());
					disciplina.setEmenta(removeChar(ementa, ementa.length() - 1));
					resultado.add(disciplina.toString());
					scanner.close();

					return resultado;				
				}
				else
					scanner.nextLine();
			}
			scanner.close();
			resultado.add("Disciplina não encontrada!");
			return resultado;
		}
		resultado.add("Disciplina não encontrada - Lista vazia!");
		return resultado;
	}
	
	public ArrayList<String> alterar(FormDisciplina disciplina) throws IOException {	
		String nomeNovo = disciplina.getNome();
		String ementaNova = disciplina.getEmenta();
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroDisciplina.jsp");		
		if (! consultar(disciplina).get(1).equals("Disciplina não encontrada!")) {
			remover(disciplina);
			disciplina.setNome(nomeNovo);
			disciplina.setEmenta(ementaNova);
			escreveDisciplina(constroiString(disciplina), true);
			resultado.add("Disciplina alterada!");
			return resultado;
			
		}
		else {
			resultado.add("Disciplina não encontrada!");
			return resultado;
		}	
	}
	
	public Map<String, ArrayList<Object>> visualizar() throws FileNotFoundException {
		File file = new File("listaDisciplinas.csv");
		Map<String, ArrayList<Object>> saida = new TreeMap<>();
		ArrayList<Object> resultado = new ArrayList<Object>();
		if(file.exists()) {
			Scanner scanner = new Scanner(file);
			String ementa;
			scanner.useDelimiter("\",\"|\n");
			while(scanner.hasNext()) {
				FormDisciplina disciplina = new FormDisciplina();
				disciplina.setCodigo(removeChar(scanner.next(), 0));
				disciplina.setNome(scanner.next());
				ementa = removeN(scanner.next());
				disciplina.setEmenta(removeChar(ementa, ementa.length() - 1));
				resultado.add(disciplina);
			}
			scanner.close();
			saida.put("listaDisciplinas.jsp", resultado);
			return saida;
		}
		else {
			resultado.add("Lista vazia!");
			saida.put("cadastroDisciplina.jsp", resultado);
			return saida;
		}
	}
}
