package integracaoJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domains.Aplicativo;

public class AplicativoDAO {
	private final String sqlC = "INSERT INTO aplicativo(nome, desenvolvedor, numero_downloads) VALUES(?, ?, ?)";
	private final String sqlR = "SELECT * FROM aplicativo";
	private final String sqlReadOne = "SELECT * FROM aplicativo where id=?";
	private final String sqlU = "UPDATE aplicativo SET nome=?, desenvolvedor=?, numero_downloads=? WHERE id=?";
	private final String sqlD = "DELETE FROM aplicativo WHERE id=?";
	
	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmU;
	private PreparedStatement stmD;

	Connection conexao;
	
	public AplicativoDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/ps2?useTimezone=true&serverTimezone=UTC";	//Erro de fuso horário:: ?useTimezone=true&serverTimezone=UTC 
			String usuario = "root";
			String senha = "projeto";

			this.conexao = DriverManager.getConnection(url, usuario, senha);
			
			this.stmC = this.conexao.prepareStatement(", Statement.RETURN_GENERATED_KEYS);
			this.stmR = this.conexao.prepareStatement("");
			this.stmU = this.conexao.prepareStatement();
			this.stmD = this.conexao.prepareStatement();
			
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Aplicativo> read(){
		List<Aplicativo> aplicativos = new ArrayList<>();
		
		try {
			ResultSet rs = this.stmR.executeQuery();	
			
			while(rs.next()) {
				Aplicativo aplicativo = new Aplicativo();
				
				aplicativo.setId(rs.getLong("id"));
				aplicativo.setNome(rs.getString("nome"));
				aplicativo.setDesenvolvedor(rs.getString("desenvolvedor"));
				aplicativo.setNumDownLoads(rs.getInt("numero_downloads"));
				
				aplicativos.add(aplicativo);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return aplicativos;
	}
	
	public Aplicativo create (Aplicativo aplicativo) {
		try {
			   this.stmC.setString(1, aplicativo.getNome());
			   this.stmC.setString(2, aplicativo.getDesenvolvedor());
			   this.stmC.setInt(3, aplicativo.getNumDownloads());
			   this.stmC.executeUpdate();
			   ResultSet rs = this.stmC.getGeneratedKeys();
			   rs.next();
			   long id = rs.getLong(1);
			   aplicativo.setId(id);
			   
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return aplicativo;
	
	}
	
	public void update(Aplicativo aplicativo) {
		try {
			this.stmU.setString(1, aplicativo.getNome());
			this.stmU.setString(2, aplicativo.getDesenvolvedor());
			this.stmU.setInt(3, aplicativo.getNumDownloads());
			this.stmU.setLong(4, aplicativo.getId());
			
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
