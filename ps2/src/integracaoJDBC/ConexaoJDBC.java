package integracaoJDBC;
import java.sql.*;

public class ConexaoJDBC {

	private String usuario;
	private String senha;
	private String host;
	private int port;
	private String dbName;
	private Connection connection;

	public ConexaoJDBC(String usuario, String senha, String host, int port, String dataBase) {
		this.usuario = usuario;
		this.senha = senha;
		this.host = host; //localhost
		this.port = port; //3306
		this.dbName = dataBase; //ps2
		connection = null;
	}

	public Connection getConnection() throws Exception{
		if (connection == null) {
			try {
				String url;
				url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useTimezone=true&serverTimezone=UTC";
				Class.forName("org.apache.derby.jdbc.ClientDriver");

				connection = DriverManager.getConnection(url, usuario, senha);

			} catch (ClassNotFoundException ex) {
				System.out.println("Falha ao carregar driver!");
				ex.printStackTrace();

			} catch (SQLException ex) {
				System.out.println("Falha ao abrir conexão!");
				ex.printStackTrace();
			}
		}
		return connection;
	}

	public void close() throws Exception {
		if (connection != null) {
			try {
				connection.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("Problema ao fechar conexão!");
			}
		}

	}
}