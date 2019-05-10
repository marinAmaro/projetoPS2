package integracaoJDBC;

import java.sql.Connection;
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
	private final String sqlReadFilter = "SELECT * FROM aplicativo where id=?";
	private final String sqlU = "UPDATE aplicativo SET nome=?, desenvolvedor=?, numero_downloads=? WHERE id=?";
	private final String sqlD = "DELETE FROM aplicativo WHERE id=?";
	
	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmRFilter;
	private PreparedStatement stmU;
	private PreparedStatement stmD;

	Connection conexao;
	
	public AplicativoDAO(ConexaoJDBC conexao) throws Exception{
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
			
			rs.close();
			
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
	
	public int update(Aplicativo aplicativo) {
		int r = 0;
		
		try {
			this.stmU.setString(1, aplicativo.getNome());
			this.stmU.setString(2, aplicativo.getDesenvolvedor());
			this.stmU.setInt(3, aplicativo.getNumDownloads());
			this.stmU.setLong(4, aplicativo.getId());
			
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
