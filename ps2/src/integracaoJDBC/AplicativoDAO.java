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
	private final String sqlReadById = "SELECT * FROM aplicativo where id=?";
	private final String sqlReadByNome = "SELECT * FROM aplicativo where nome=?";
	private final String sqlReadByDesenvolvedor = "SELECT * FROM aplicativo where desenvolvedor=?";
	private final String sqlU = "UPDATE aplicativo SET nome=?, desenvolvedor=?, numero_downloads=? WHERE id=?";
	private final String sqlD = "DELETE FROM aplicativo WHERE id=?";

	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmRById;
	private PreparedStatement stmRByNome;
	private PreparedStatement stmRByDesenvolvedor;
	private PreparedStatement stmU;
	private PreparedStatement stmD;

	Connection conexao;

	public AplicativoDAO(ConexaoJDBC conexao) throws Exception {
		try {
			Connection con = conexao.getConnection();
			stmC = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
			stmR = con.prepareStatement(sqlR);
			stmRById = con.prepareStatement(sqlReadById);
			stmRByNome = con.prepareStatement(sqlReadByNome);
			stmRByDesenvolvedor = con.prepareStatement(sqlReadByDesenvolvedor);
			stmU = con.prepareStatement(sqlU);
			stmD = con.prepareStatement(sqlD);

		} catch (SQLException ex) {
			throw new DaoException("Falha ao preparar statement: " + ex.getMessage());
		}

	}

	public List<Aplicativo> read() throws DaoException {
		List<Aplicativo> aplicativos = new ArrayList<>();
		try {
			ResultSet rs = this.stmR.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				String nome = rs.getString("nome");
				String desenvolvedor = rs.getString("desenvolvedor");
				int numDownloads = rs.getInt("numero_downloads");

				Aplicativo app = new Aplicativo(id, nome, desenvolvedor, numDownloads);
				aplicativos.add(app);
			}

			rs.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao ler registros: " + ex.getMessage());
		}

		return aplicativos;
	}

	public Aplicativo readById(long id) throws DaoException {
		Aplicativo app = null;

		try {
			this.stmRById.setLong(1, id);
			ResultSet rs = this.stmRById.executeQuery();
			if (rs.next()) {
				String nome = rs.getString("nome");
				String desenvolvedor = rs.getString("desenvolvedor");
				int numDownloads = rs.getInt("numero_downloads");
				app = new Aplicativo(id, nome, desenvolvedor, numDownloads);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return app;
	}

	public Aplicativo readByNome(String nome) throws DaoException {
		Aplicativo app = null;

		try {
			this.stmRByNome.setString(1, nome);
			ResultSet rs = this.stmRByNome.executeQuery();
			if (rs.next()) {
				String desenvolvedor = rs.getString("desenvolvedor");
				int numDownloads = rs.getInt("numero_downloads");
				long id = rs.getLong("id");

				app = new Aplicativo(id, nome, desenvolvedor, numDownloads);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return app;
	}

	public Aplicativo readByDesenvolvedor(String desenvolvedor) throws DaoException {
		Aplicativo app = null;

		try {
			this.stmRByDesenvolvedor.setString(1, desenvolvedor);
			ResultSet rs = this.stmRByDesenvolvedor.executeQuery();
			if (rs.next()) {
				long id = rs.getLong("id");
				String nome = rs.getString("nome");
				int numDownloads = rs.getInt("numero_downloads");
				
				app = new Aplicativo(id, nome, desenvolvedor, numDownloads);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return app;
	}
	
	public long create(Aplicativo aplicativo) throws DaoException {
		long id = 0;
		try {
			this.stmC.setString(1, aplicativo.getNome());
			this.stmC.setString(2, aplicativo.getDesenvolvedor());
			System.out.println("->>>>> " + aplicativo.getNumDownloads());
			this.stmC.setInt(3, aplicativo.getNumDownloads());
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

	public int update(Aplicativo aplicativo) throws DaoException {
		int r = 0;
		try {
			this.stmU.setString(1, aplicativo.getNome());
			this.stmU.setString(2, aplicativo.getDesenvolvedor());
			this.stmU.setInt(3, aplicativo.getNumDownloads());
			this.stmU.setLong(4, aplicativo.getId());
			r = this.stmU.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Falha ao atualizar registro: " + e.getMessage());
		}

		return r;
	}

	public int delete(long id) throws DaoException {
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
			stmRByDesenvolvedor.close();
			stmRByNome.close();
			stmRById.close();
			stmU.close();
			stmD.close();

		} catch (SQLException ex) {
			throw new DaoException("Falha ao fechar DAO: " + ex.getMessage());
		}
	}
}