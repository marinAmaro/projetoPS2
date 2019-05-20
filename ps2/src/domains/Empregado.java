package domains;

public class Empregado {
	long id;
	String nome;
	String cargo;
	double salario;
	
	public Empregado() {}
	
	public Empregado(long id, String nome, String cargo, double salario) {
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.salario = salario;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public double getSalario() {
		return this.salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}
}
