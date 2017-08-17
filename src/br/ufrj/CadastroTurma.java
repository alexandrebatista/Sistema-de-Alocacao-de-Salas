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

public class CadastroTurma {
	
	public ArrayList<String> incluir(FormTurma turma) throws IOException {
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroTurma.jsp");
		try{
			verificaExcecoes(turma, false);
			escreveTurma(constroiString(turma), true);
			resultado.add("Turma incluída!");
		}
		catch(Exception e) {
			resultado.add(e.getMessage());
		}
		return resultado;
	}

	public void verificaExcecoes(FormTurma turma,boolean altera) throws Exception {
		if(altera == false)
			if(! consultar(turma).get(1).equals("Turma não encontrada!") && ! consultar(turma).get(1).equals("Turma não encontrada - Lista vazia!")) 
				throw new Exception("ID já existe!");	
		verificaVariaveis(turma);
		verificaHorario(turma.getHorario());
		verificaOcupado(turma, false);
	}
	
	public void verificaVariaveis(FormTurma turma) throws Exception {
		CadastroProfessor cadastroProfessor = new CadastroProfessor();
		CadastroDisciplina cadastroDisciplina = new CadastroDisciplina();
		CadastroSala cadastroSala = new CadastroSala();
		if(cadastroProfessor.consultar(turma.getProfessor()).get(1).equals("Professor não encontrado!") || cadastroProfessor.consultar(turma.getProfessor()).get(1).equals("Professor não encontrado - Lista vazia!")) {
			throw new Exception("Professor inválido!");
		}
		else if(cadastroDisciplina.consultar(turma.getDisciplina()).get(1).equals("Disciplina não encontrada!") || cadastroDisciplina.consultar(turma.getDisciplina()).get(1).equals("Disciplina não encontrada - Lista vazia!")) {
			throw new Exception("Disciplina inválida!");
		}
		else if(cadastroSala.consultar(turma.getSala()).get(1).equals("Sala não encontrada!") || cadastroSala.consultar(turma.getSala()).get(1).equals("Sala não encontrada - Lista vazia!")) {
			throw new Exception("Sala inválida!");
		}
	}	
	
	public void verificaOcupado(FormTurma turma, boolean altera) throws Exception {			
		File file = new File("listaTurmas.csv");
		String linha;
		if (file.exists()) {
			Scanner scanner = new Scanner(file);
			String nomeProfessor;
			String numeroSala;
			String horario;
			scanner.useDelimiter("\",\"|\n");
			while(scanner.hasNext()) {
				if(altera == true) {
					if(turma.getId().equals(removeChar(scanner.next(), 0))) {
						linha = scanner.nextLine();
						if (linha.equals("")) {
							break;
						}	
					}
				}
				else
					scanner.next();				
				nomeProfessor = scanner.next();
				scanner.next();
				numeroSala = scanner.next();
				horario = removeN(scanner.next());
				if(turma.getProfessor().getNome().equals(nomeProfessor) && turma.getHorario().equals(removeChar(horario, horario.length() - 1))) {
					scanner.close();
					throw new Exception("Professor ocupado!");
				}
				if(turma.getSala().getNumero().equals(numeroSala) && turma.getHorario().equals(removeChar(horario, horario.length() - 1))) {
					scanner.close();
					throw new Exception("Sala ocupada!");
				}
			}
			scanner.close();
		}	
	}
	
	public void verificaHorario(String horario) throws Exception {
		String campo[] = horario.split(":");
		int contaSplit = 0;
		for(int i = 0; i < horario.length() - 1; i++ ) {
			if(horario.charAt(i) == ':')
				contaSplit++;
		}
		if(contaSplit != 1 || horario.length() < 5)
			throw new Exception("Horário inválido!");
		else if (Integer.parseInt(campo[0]) > 23 || Integer.parseInt(campo[0]) < 0  || Integer.parseInt(campo[1]) > 60 || Integer.parseInt(campo[1]) < 0)
			throw new Exception("Horário inválido!");
	}
	
	public String constroiString(FormTurma turma) {
		StringBuilder sb = new StringBuilder();
    	sb.append("\"" + turma.getId() + "\",");
    	sb.append("\"" + turma.getProfessor().getNome() + "\",");
    	sb.append("\"" + turma.getDisciplina().getCodigo() + "\",");
    	sb.append("\"" + turma.getSala().getNumero() + "\",");
    	sb.append("\"" + turma.getHorario() + "\"");
		return sb.toString();
	}
	
	public void escreveTurma(String sb, boolean manter) throws IOException {
		PrintWriter print = new PrintWriter(new FileWriter("listaTurmas.csv", manter)); 
		print.println(sb);
		print.close();
	}
	
	public ArrayList<String> remover(FormTurma turma) throws IOException {
		String sb = constroiString(turma);	
		boolean removido = false;
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroTurma.jsp");
		ArrayList<String> arquivo = new ArrayList<String>();
		String linha;
		Scanner scanner = new Scanner(new File("listaTurmas.csv"));
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
			File file = new File("listaTurmas.csv");
			file.delete();	
		}	
		else {
			escreveTurma(arquivo.get(0), false);
			for(int i = 1; i < arquivo.size(); i++) 
				escreveTurma(arquivo.get(i), true);
		}
		if (removido)
			resultado.add("Turma removida!");
		else
			resultado.add("Turma não encontrada!");
		
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

	public ArrayList<String> consultar(FormTurma turma) throws Exception {		
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroTurma.jsp");
		Dispatcher dis = new Dispatcher();
		Map<String, String> parametros = new TreeMap<>();
		String horario;
		File file = new File("listaTurmas.csv");
		if(file.exists()) {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\",\"|\n");
			while(scanner.hasNext()) {
				if (turma.getId().equals(scanner.next().substring(1))) {
					parametros.put("codigo", turma.getId());
					parametros.put("professor", scanner.next());
					parametros.put("disciplina", scanner.next());
					parametros.put("sala", scanner.next());
					horario = removeN(scanner.next());
					parametros.put("horario", removeChar(horario, horario.length() - 1));					
					dis.preencheForm(turma, parametros);
					scanner.close();
					resultado.add(turma.toString());
					return resultado;				
				}
				else
					scanner.nextLine();
			}
			scanner.close();
			resultado.add("Turma não encontrada!");
			return resultado;
		}
		resultado.add("Turma não encontrada - Lista vazia!");
		return resultado;
	}
	
	public ArrayList<String> alterar(FormTurma turma) throws Exception {
		ArrayList<String> resultado = new ArrayList<String>();
		resultado.add("cadastroTurma.jsp");	
		try {
			verificaExcecoes(turma, true);					
			FormProfessor professorNovo = turma.getProfessor();		
			FormDisciplina disciplinaNova = turma.getDisciplina();		
			FormSala salaNova = turma.getSala();		
			String horarioNovo = turma.getHorario();
			
			if (! consultar(turma).get(1).equals("Turma não encontrada!")) {
				remover(turma);
				turma.setProfessor(professorNovo);
				turma.setDisciplina(disciplinaNova);
				turma.setSala(salaNova);
				turma.setHorario(horarioNovo);
				escreveTurma(constroiString(turma), true);
				resultado.add("Turma alterada!");
				return resultado;
			}	
			resultado.add("ID inválido!");
			return resultado;
		}
		catch(Exception e) {
			resultado.add(e.getMessage());
			return resultado;
		}
	}
	
	public Map<String, ArrayList<FormTurma>> visualizar() throws Exception {
		File file = new File("listaTurmas.csv");
		Scanner scanner = new Scanner(file);
		Map<String, ArrayList<FormTurma>> saida = new TreeMap<>();
		Dispatcher dis = new Dispatcher();
		ArrayList<FormTurma> resultado = new ArrayList<FormTurma>();
		Map<String, String> parametros = new TreeMap<>();
		String horario;
		scanner.useDelimiter("\",\"|\n");
		while(scanner.hasNext()) {
			FormTurma turma = new FormTurma();
			parametros.put("id", removeChar(scanner.next(), 0));
			parametros.put("professor", scanner.next());
			parametros.put("disciplina", scanner.next());
			parametros.put("sala", scanner.next());
			horario = removeN(scanner.next());
			parametros.put("horario", removeChar(horario, horario.length() - 1));					
			dis.preencheForm(turma, parametros);
			resultado.add(turma);
		}
		scanner.close();
		saida.put("listaTurmas.jsp", resultado);
		return saida;
	}

	public Map<String, ArrayList<ArrayList<String>>> exibir() throws FileNotFoundException {
		Map<String, ArrayList<ArrayList<String>>> saida = new TreeMap<>();
		ArrayList<ArrayList<String>> resultado = new ArrayList<ArrayList<String>>();
		ArrayList<String> professores = new ArrayList<String>();
		ArrayList<String> disciplinas = new ArrayList<String>();
		ArrayList<String> salas = new ArrayList<String>();
		File file = new File("listaProfessores.csv");
				
		if(file.exists()) {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\"|\n");	
			while(scanner.hasNext()) {
				professores.add(scanner.next());
				scanner.nextLine();
			}
			scanner.close();
		}	
		else
			professores.add("Lista vazia!");
		
		
		file = new File("listaDisciplinas.csv");

		if(file.exists()) {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\"|\n");
			while(scanner.hasNext()) {
				disciplinas.add(scanner.next());
				scanner.nextLine();
			}
			scanner.close();
		}	
		else
			disciplinas.add("Lista vazia!");
		
		file = new File("listaSalas.csv");

		if(file.exists()) { 
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\"|\n");
			while(scanner.hasNext()) {
				salas.add(scanner.next());
				scanner.nextLine();
			}
			scanner.close();
		}	
		else
			salas.add("Lista vazia!");
		
		resultado.add(professores);
		resultado.add(disciplinas);
		resultado.add(salas);
		saida.put("indexTurma.jsp", resultado);
		return saida;
	}
	
}
