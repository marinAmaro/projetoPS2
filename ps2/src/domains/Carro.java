package domains;

public class Carro {
	private long id;
	private int ano;
	private String modelo;
	private String marca;

	private String categoria;

	public Carro() {

	}

	public Carro(long id, int ano, String modelo, String marca, String categoria) {
		this.id = id;
		this.marca = marca;
		this.ano = ano;
		this.categoria = categoria;
	}

	// GET
	public long getId() {
		return this.id;
	}

	public String getModelo() {
		return this.modelo;
	}

	public String getMarca() {
		return this.marca;
	}

	public int getAno() {
		return this.ano;
	}

	public String getCategoria() {
		return this.categoria;
	}

	// SET
	public void setId(long id) {
		this.id = id;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
