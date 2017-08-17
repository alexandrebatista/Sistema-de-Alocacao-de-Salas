package br.ufrj;

public class FormDisciplina {
	@Obrigatorio( true )
	String codigo = "";
	@Obrigatorio( false )
	String nome = "";
	@Obrigatorio( false )
	String ementa = "";
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmenta() {
		return ementa;
	}
	public void setEmenta(String ementa) {
		this.ementa = ementa;
	}
	
	public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Código: " + codigo + "\n");
    	sb.append("Nome: " + nome + "\n");
    	sb.append("Ementa: " + ementa);
    	return sb.toString();
    }
	
}
