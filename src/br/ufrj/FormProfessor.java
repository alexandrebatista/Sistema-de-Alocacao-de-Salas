package br.ufrj;

public class FormProfessor {
	@Obrigatorio( true )
	String nome = "";
	@Obrigatorio( false )
	String email = "";
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Nome: " + nome + "\n");
    	sb.append("Email: " + email);
    	return sb.toString();
    }
}
