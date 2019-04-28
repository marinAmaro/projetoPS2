package domains;

public class Aplicativo {
	long id;
	String nome;
	String desenvolvedor;
	int numDownloads;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id= id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDesenvolvedor() {
		return this.desenvolvedor;
	}
	
	public void setDesenvolvedor(String desenvolvedor) {
		this.desenvolvedor = desenvolvedor;
	}
	
	public int getNumDownloads() {
		return numDownloads;
	}
	
	public void setNumDownLoads(int num) {
		this.numDownloads = num;
	}
}
