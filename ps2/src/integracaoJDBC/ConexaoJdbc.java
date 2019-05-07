	package integracaoJDBC;

import java.sql.*;

public class ConexaoJdbc {
	private String tabela;
	private String query;
	
	public ConexaoJdbc(String tabela) {
		this.tabela = tabela;
	}
	
	public PreparedStatement conectar() {
		PreparedStatement stm = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/" + tabela;
			String usuario = "root";
			String senha= null;

			Connection conexao = DriverManager.getConnection(url, usuario, senha);
			
			stm = conexao.prepareStatement(query);
			
		} catch (ClassNotFoundException ex) {
			System.out.println("Falha ao executar o driver");
		} catch (Exception e) {
			System.out.println("Falha ao acessar base de dados" + e.getMessage());
		}
		
		return stm;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
}

