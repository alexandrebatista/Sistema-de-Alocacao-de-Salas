package br.ufrj;

public class FormTurma {
	@Obrigatorio(true)
	String id = "";
	@Obrigatorio(false)
	FormProfessor professor = new FormProfessor();
	@Obrigatorio(false)
	FormDisciplina disciplina = new FormDisciplina();
	@Obrigatorio(false)
	FormSala sala = new FormSala();
	@Obrigatorio(false)
	String horario = "";
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public FormProfessor getProfessor() {
		return professor;
	}
	@Consultavel
	public void setProfessor(FormProfessor professor) {
		this.professor = professor;
		
	}
	
	public FormDisciplina getDisciplina() {
		return disciplina;
	}
	@Consultavel
	public void setDisciplina(FormDisciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	public FormSala getSala() {
		return sala;
	}
	@Consultavel
	public void setSala(FormSala sala) {
		this.sala = sala;
	}
	
	public String getHorario() {
		return horario;
	}
	
	public void setHorario(String horario) {
		this.horario = horario;
	}
	
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("ID: " + getId() + "\n");
    	sb.append("Professor: " + professor.getNome() + "\n");
    	sb.append("Disciplina: " + disciplina.getCodigo() + "\n");
    	sb.append("Sala: " + sala.getNumero() + "\n");
    	sb.append("Horário: " + getHorario());
    	return sb.toString();
    }
}
