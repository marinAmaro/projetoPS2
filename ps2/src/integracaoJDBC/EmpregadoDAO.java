package integracaoJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domains.Empregado;

public class EmpregadoDAO {

		private PreparedStatement stmC;
		private PreparedStatement stmR;
		private PreparedStatement stmU;
		private PreparedStatement stmD;

		Connection conexao;
		
		public EmpregadoDAO() {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String url = "jdbc:mysql://localhost:3306/ps2?useTimezone=true&serverTimezone=UTC";	//Erro de fuso horário:: ?useTimezone=true&serverTimezone=UTC 
				String usuario = "root";
				String senha = "projeto123";

				this.conexao = DriverManager.getConnection(url, usuario, senha);
				
				this.stmC = this.conexao.prepareStatement("INSERT INTO aplicativo(nome, cargo, salario) VALUES(?, ?, ?)");
				this.stmR = this.conexao.prepareStatement("SELECT * FROM empregado");
				this.stmU = this.conexao.prepareStatement("UPDATE aplicativo SET nome=?, cargo=?, salario=? WHERE id=?");
				this.stmD = this.conexao.prepareStatement("DELETE FROM empregado WHERE id=?");
				
		
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public List<Empregado> read(){
			List<Empregado> empregados = new ArrayList<>();
			
			try {
				ResultSet rs = this.stmR.executeQuery();	
				
				while(rs.next()) {
					Empregado empregado = new Empregado();
					
					empregado.setId(rs.getLong("id"));
					empregado.setNome(rs.getString("nome"));
					empregado.setCargo(rs.getString("cargo"));
					empregado.setSalario(rs.getDouble("salario"));
					
					empregados.add(empregado);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return empregados;
		}
		
		public Empregado create(Empregado empregado) {
			try {
				   this.stmC.setString(1, empregado.getNome());
				   this.stmC.setString(2, empregado.getCargo());
				   this.stmC.setDouble(3, empregado.getSalario());
				
					this.stmC.executeUpdate();
				   ResultSet rs = this.stmC.getGeneratedKeys();
				   rs.next();
				   long id = rs.getLong(1);
				   empregado.setId(id);
				   
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return empregado;
		
		}
		
		public void update(Empregado empregado) {
			try {
				this.stmU.setString(1, empregado.getNome());
				this.stmU.setString(2, empregado.getCargo());
				this.stmU.setDouble(3, empregado.getSalario());
				
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
