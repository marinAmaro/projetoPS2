package integracaoJDBC;

import java.sql.*;

public class ConexaoJdbc {
	
	public void conectar() {
		try {
			Class.forName("");
			String url = "url com porta que está conectada p sql";
			String usuario = null;
			String senha= null;
			String queryString= null ;
			
			Connection conexao = DriverManager.getConnection(url, usuario, senha);
		
		
		} catch (ClassNotFoundException ex) {
			System.out.println("Falha ao executar o driver");
		} catch (Exception e) {
			System.out.println("Falha ao acessar base de dados" + e.getMessage());
		}
		
	}
}

