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
	private final String sqlRById = "SELECT * FROM empregado where id=?";
	private final String sqlRByNome = "SELECT * FROM empregado where nome=?";
	private final String sqlRByCargo = "SELECT * FROM empregado where cargo=?";
	private final String sqlU = "UPDATE aplicativo SET nome=?, cargo=?, salario=? WHERE id=?";
	private final String sqlD = "DELETE FROM empregado WHERE id=?";

	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmRById;
	private PreparedStatement stmRByNome;
	private PreparedStatement stmRByCargo;
	private PreparedStatement stmU;
	private PreparedStatement stmD;

	Connection conexao;

	public EmpregadoDAO(ConexaoJDBC conexao) throws Exception {
		try {
			Connection con = conexao.getConnection();
			stmC = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
			stmR = con.prepareStatement(sqlR);
			stmRById = con.prepareStatement(sqlRById);
			stmRByNome = con.prepareStatement(sqlRByNome);
			stmRByCargo = con.prepareStatement(sqlRByCargo);
			stmU = con.prepareStatement(sqlU);
			stmD = con.prepareStatement(sqlD);

		} catch (SQLException ex) {
			throw new DaoException("Falha ao preparar statement: " + ex.getMessage());
		}

	}

	public List<Empregado> read() {
		List<Empregado> empregados = new ArrayList<>();

		try {
			ResultSet rs = this.stmR.executeQuery();

			while (rs.next()) {
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

	public Empregado readById(long id) throws DaoException {
		Empregado emp = null;

		try {
			this.stmRById.setLong(1, id);
			ResultSet rs = this.stmRById.executeQuery();
			if (rs.next()) {
				String nome = rs.getString("nome");
				String cargo = rs.getString("cargo");
				double salario = rs.getDouble("salario");
				emp = new Empregado(id, nome, cargo, salario);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}

		return emp;
	}
	
	public Empregado readByNome(String nome) throws DaoException {
		Empregado emp = null;

		try {
			this.stmRByNome.setString(1, nome);
			ResultSet rs = this.stmRByNome.executeQuery();
			if (rs.next()) {
				long id = rs.getLong("id")
;				String cargo = rs.getString("cargo");
				double salario = rs.getDouble("salario");
				emp = new Empregado(id, nome, cargo, salario);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}

		return emp;
	}
	
	public List<Empregado> readByCargo(String cargo) throws DaoException {
		
		List<Empregado> empregados = new ArrayList<>();
		Empregado emp = null;

		try {
			this.stmRByCargo.setString(1, cargo);
			ResultSet rs = this.stmRByCargo.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				String nome = rs.getString("nome");
				double salario = rs.getDouble("salario");
				emp = new Empregado(id, nome, cargo, salario);

				empregados.add(emp);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}

		return empregados;
	}

	public long create(Empregado empregado) throws DaoException {
		long id = 0;
		try {
			this.stmC.setString(1, empregado.getNome());
			this.stmC.setString(2, empregado.getCargo());
			this.stmC.setDouble(3, empregado.getSalario());
			this.stmC.executeUpdate();
			ResultSet rs = this.stmC.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao criar registro: " + ex.getMessage());
		}
		return id;
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

	public int delete(long id) throws SQLException, DaoException {
		int r = 0;
		try {
			this.stmD.setLong(1, id);
			r = this.stmD.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Falha ao apagar registro: " + e.getMessage());
		}

		return r;
	}

	public void close() throws Exception {
		try {
			stmC.close();
			stmR.close();
			stmRById.close();
			stmRByNome.close();
			stmRByCargo.close();
			stmU.close();
			stmD.close();

		} catch (SQLException ex) {
			throw new DaoException("Falha ao fechar DAO: " + ex.getMessage());
		}
	}
}
