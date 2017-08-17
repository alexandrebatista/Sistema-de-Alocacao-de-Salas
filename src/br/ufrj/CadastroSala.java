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

public class CadastroSala {
	
	public ArrayList<String> incluir(FormSala sala) throws IOException {
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroSala.jsp");
		try {
			verificaExcecoes(sala);
			escreveSala(constroiString(sala), true);
			resultado.add("Sala incluída!");
		}
		catch(Exception e) {
			resultado.add(e.getMessage());
		}
		return resultado;
	}

	public void verificaExcecoes(FormSala sala) throws Exception {
		if(! consultar(sala).get(1).equals("Sala não encontrada!") && ! consultar(sala).get(1).equals("Sala não encontrada - Lista vazia!")) 
			throw new Exception("Número já existe!");
		if(! sala.getCapacidade().equals(""))
			verificaCapacidade(sala.getCapacidade());

	}
	
	public void verificaCapacidade(String capacidade) throws Exception {
		for(int i = 0; i < capacidade.length(); i++) 
			if(capacidade.charAt(i) < (int)'0' || capacidade.charAt(i) > (int)'9')
				throw new Exception("Capacidade inválida!");
	}
	
	public String constroiString(FormSala sala) {
		StringBuilder sb = new StringBuilder();
    	sb.append("\"" + sala.getNumero() + "\",");
		sb.append("\"" + sala.getCapacidade() + "\"");
		return sb.toString();
	}
	
	public void escreveSala(String sb, boolean manter) throws IOException {
		PrintWriter print = new PrintWriter(new FileWriter("listaSalas.csv", manter)); 
		print.println(sb);
		print.close();
	}
	
	public ArrayList<String> remover(FormSala sala) throws IOException {
		String sb = constroiString(sala);
		boolean removido = false;
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroSala.jsp");
		ArrayList<String> arquivo = new ArrayList<String>();
		String linha;
		Scanner scanner = new Scanner(new File("listaSalas.csv"));
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
			File file = new File("listaSalas.csv");
			file.delete();	
		}	
		else {
			escreveSala(arquivo.get(0), false);
			for(int i = 1; i < arquivo.size(); i++) 
				escreveSala(arquivo.get(i), true);
		}
		if (removido)
			resultado.add("Sala removida!");
		else
			resultado.add("Sala não encontrada!");
		
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
	
	public ArrayList<String> consultar(FormSala sala) throws FileNotFoundException {
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroSala.jsp");
		String capacidade;
		File file = new File("listaSalas.csv");
		if(file.exists()) {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\",\"|\n");
			while(scanner.hasNext()) {
				if (sala.getNumero().equals(scanner.next().substring(1))) {	
					capacidade = removeN(scanner.next());
					sala.setCapacidade(removeChar(capacidade, capacidade.length() - 1));
					scanner.close();
					resultado.add(sala.toString());
					return resultado;				
				}
				else
					scanner.nextLine();
			}
			scanner.close();
			resultado.add("Sala não encontrada!");
			return resultado;
		}
		resultado.add("Sala não encontrada - Lista vazia!");
		return resultado;
	}
	
	public ArrayList<String> alterar(FormSala sala) throws IOException {	
		String capacidadeNova = sala.getCapacidade();
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroSala.jsp");		
		if (! consultar(sala).get(1).equals("Sala não encontrada!")) {
			remover(sala);
			sala.setCapacidade(capacidadeNova);
			escreveSala(constroiString(sala), true);
			resultado.add("Sala alterada!");
			return resultado;
			
		}
		else {
			resultado.add("Sala não encontrada!");
			return resultado;
		}	
	}
	
	public Map<String, ArrayList<Object>> visualizar() throws FileNotFoundException {
		File file = new File("listaSalas.csv");
		Map<String, ArrayList<Object>> saida = new TreeMap<>();
		ArrayList<Object> resultado = new ArrayList<Object>();
		if(file.exists()) {
			Scanner scanner = new Scanner(file);
			String capacidade;
			scanner.useDelimiter("\",\"|\n");
			while(scanner.hasNext()) {
				FormSala sala = new FormSala();
				sala.setNumero(removeChar(scanner.next(), 0));
				capacidade = removeN(scanner.next());
				sala.setCapacidade(removeChar(capacidade, capacidade.length() - 1));
				resultado.add(sala);
			}
			scanner.close();
			saida.put("listaSalas.jsp", resultado);
			return saida;
		}
		else {
			resultado.add("Lista vazia!");
			saida.put("cadastroSala.jsp", resultado);
			return saida;
		}
	}
}
