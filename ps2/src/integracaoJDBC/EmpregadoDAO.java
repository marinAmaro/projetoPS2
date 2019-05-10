package integracaoJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domains.Empregado;

public class EmpregadoDAO {

	private final String sqlC = "INSERT INTO aplicativo(nome, cargo, salario) VALUES(?, ?, ?)";
	private final String sqlR = "SELECT * FROM empregado";
	private final String sqlReadFilter = "SELECT * FROM empregado where id=?";
	private final String sqlU = "UPDATE aplicativo SET nome=?, cargo=?, salario=? WHERE id=?";
	private final String sqlD = "DELETE FROM empregado WHERE id=?";
	
	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmRFilter;
	private PreparedStatement stmU;
	private PreparedStatement stmD;

	Connection conexao;
		
		public EmpregadoDAO(ConexaoJDBC conexao) throws Exception {
			try {
				Connection con = conexao.getConnection();
				stmC = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
				stmR = con.prepareStatement(sqlR);
				stmRFilter = con.prepareStatement(sqlReadFilter);
				stmU = con.prepareStatement(sqlU);
				stmD = con.prepareStatement(sqlD);

			} catch(SQLException ex) {
				ex.printStackTrace();
				//throw new DaoException("Falha ao preparar statement: " + ex.getMessage());
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
		
		public int update(Empregado empregado) {
			int r = 0;
			
			try {
				this.stmU.setString(1, empregado.getNome());
				this.stmU.setString(2, empregado.getCargo());
				this.stmU.setDouble(3, empregado.getSalario());
				
				r = this.stmU.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return r;
		}
		
		public int delete(long id) throws SQLException {
			this.stmD.setLong(1, id);
			int r = this.stmD.executeUpdate();
			return r;
		}
		
		public void close() throws Exception {
			try {
				stmC.close();
				stmR.close();
				stmRFilter.close();
				stmU.close();
				stmD.close();

			} catch(SQLException ex) {
				ex.printStackTrace();
				//throw new DaoException("Falha ao fechar DAO: " + ex.getMessage());
			}
		}
	}
