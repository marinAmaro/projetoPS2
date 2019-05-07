package integracaoJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domains.Aplicativo;

public class AplicativoDAO {
	
	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmU;
	private PreparedStatement stmD;

	Connection conexao;
	
	public AplicativoDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/ps2?useTimezone=true&serverTimezone=UTC";			//Erro de fuso horário:: ?useTimezone=true&serverTimezone=UTC 
			String usuario = "root";
			String senha = "projeto";

			this.conexao = DriverManager.getConnection(url, usuario, senha);
			
			this.stmC = this.conexao.prepareStatement("INSERT INTO aplicativo(nome, desenvolvedor, numDownloads) VALUES(?, ?, ?)");
			this.stmR = this.conexao.prepareStatement("SELECT * FROM aplicativo");
			this.stmU = this.conexao.prepareStatement("UPDATE aplicativo SET nome=?, desenvolvedor=?, numDownloads=? WHERE id=?");
			this.stmD = this.conexao.prepareStatement("DELETE FROM aplicativo WHERE id=?");
			
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Aplicativo> listarAplicativos(){
		List<Aplicativo> aplicativos = new ArrayList<>();
		
		try {
			ResultSet rs = this.stmR.executeQuery();	
			
			while(rs.next()) {
				Aplicativo aplicativo = new Aplicativo();
				
				aplicativo.setId(rs.getLong("Id"));
				aplicativo.setNome(rs.getString("Nome"));
				aplicativo.setDesenvolvedor(rs.getString("Desenvolvedor"));
				aplicativo.setNumDownLoads(rs.getInt("NumeroDeDownloads"));
				
				aplicativos.add(aplicativo);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return aplicativos;
	}
	
}
