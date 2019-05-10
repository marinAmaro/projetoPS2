package integracaoJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domains.Aplicativo;
import domains.Carro;

public class CarroDAO {

	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmU;
	private PreparedStatement stmD;

	Connection conexao;

	public CarroDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/ps2?useTimezone=true&serverTimezone=UTC"; // Erro de fuso
																								// horário::
																								// ?useTimezone=true&serverTimezone=UTC
			String usuario = "root";
			String senha = "projeto123";

			this.conexao = DriverManager.getConnection(url, usuario, senha);

			this.stmC = this.conexao.prepareStatement("INSERT INTO aplicativo(modelo, marca, ano, categoria) VALUES(?, ?, ?, ?)");
			this.stmR = this.conexao.prepareStatement("SELECT * FROM carro");
			this.stmU = this.conexao.prepareStatement("UPDATE aplicativo SET modelo=?, marca=?, ano=?, categoria=? WHERE id=?");
			this.stmD = this.conexao.prepareStatement("DELETE FROM carro WHERE id=?");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Carro> listarAplicativos() {
		List<Carro> carros = new ArrayList<>();

		try {
			ResultSet rs = this.stmR.executeQuery();

			while (rs.next()) {
				Carro carro = new Carro();

				carro.setId(rs.getLong("id"));
				carro.setAno(rs.getInt("ano"));
				carro.setCategoria(rs.getString("categoria"));
				carro.setMarca(rs.getString("marca"));
				carro.setModelo(rs.getString("modelo"));

				carros.add(carro);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return carros;
	}

	public Carro create (Carro carro) {
		try {
			   this.stmC.setString(1, carro.getModelo());
			   this.stmC.setString(2, carro.getMarca());
			   this.stmC.setInt(3, carro.getAno());
			   this.stmC.setString(4, carro.getCategoria());
			   
			   this.stmC.executeUpdate();
			   
			   ResultSet rs = this.stmC.getGeneratedKeys();
			   rs.next();
			   long id = rs.getLong(1);
			   carro.setId(id);
			   
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return carro;
	
	}
	
	public void update(Carro carro) {
		try {
			this.stmU.setString(1, carro.getModelo());
			this.stmU.setString(2, carro.getMarca());
			this.stmU.setInt(3, carro.getAno());
			this.stmU.setString(4, carro.getCategoria());
			
			int r = this.stmU.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(long id) throws SQLException {
		this.stmD.setLong(1, id);
		int r = this.stmD.executeUpdate();
	}
	
}
