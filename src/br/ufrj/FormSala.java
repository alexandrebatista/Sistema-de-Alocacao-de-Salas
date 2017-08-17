package br.ufrj;

public class FormSala {
	@Obrigatorio(true)
	String numero = "";
	@Obrigatorio(false)
	String capacidade = "";
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getCapacidade() {
		return capacidade;
	}
	
	public void setCapacidade(String capacidade) {
		this.capacidade = capacidade;
	}
	
	public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Número: " + numero + "\n");
    	sb.append("Capacidade: " + capacidade);
    	return sb.toString();
    }
	
}
