package integracaoJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domains.Carro;

public class CarroDAO {
	private final String sqlC = "INSERT INTO carro(modelo, marca, ano, categoria) VALUES(?, ?, ?, ?)";
	private final String sqlR = "SELECT * FROM carro";
	private final String sqlRById = "SELECT * FROM carro where id=?";
	private final String sqlRByAno = "SELECT * FROM carro where ano=?";
	private final String sqlRByModelo = "SELECT * FROM carro where modelo=?";
	private final String sqlU = "UPDATE carro SET modelo=?, marca=?, ano=?, categoria=? WHERE id=?";
	private final String sqlD = "DELETE FROM carro WHERE id=?";

	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmRById;
	private PreparedStatement stmRByAno;
	private PreparedStatement stmRByModelo;
	private PreparedStatement stmU;
	private PreparedStatement stmD;

	Connection conexao;

	public CarroDAO(ConexaoJDBC conexao) throws Exception {
		try {
			Connection con = conexao.getConnection();
			stmC = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
			stmR = con.prepareStatement(sqlR);
			stmRById = con.prepareStatement(sqlRById);
			stmRByAno = con.prepareStatement(sqlRByAno);
			stmRByModelo = con.prepareStatement(sqlRByModelo);
			stmU = con.prepareStatement(sqlU);
			stmD = con.prepareStatement(sqlD);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public List<Carro> read() {
		List<Carro> carros = new ArrayList<>();

		try {
			ResultSet rs = this.stmR.executeQuery();

			while (rs.next()) {
				Carro carro = new Carro();

				carro.setId(rs.getLong("id"));
				carro.setAno(rs.getInt("ano"));
				carro.setCategoria(rs.getString("categoria"));
				carro.setMarca(rs.getString("marca"));
				carro.setModelo(rs.getString("modelo"));

				carros.add(carro);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return carros;
	}

	public Carro readById(long id) throws DaoException {
		Carro car = null;

		try {
			this.stmRById.setLong(1, id);
			ResultSet rs = this.stmRById.executeQuery();
			if (rs.next()) {

				int ano = rs.getInt("ano");
				String marca = rs.getString("marca");
				String modelo = rs.getString("modelo");
				String categoria = rs.getString("categoria");

				car = new Carro(id, ano, modelo, marca, categoria);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return car;
	}

	public List<Carro> readByAno(int ano) throws DaoException {
		List<Carro> carros = new ArrayList<>();
		Carro car;

		try {
			this.stmRByAno.setInt(1, ano);
			ResultSet rs = this.stmRByAno.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				String marca = rs.getString("marca");
				String modelo = rs.getString("modelo");
				System.out.println("modelo ->> " + modelo);
				String categoria = rs.getString("categoria");

				car = new Carro(id, ano, modelo, marca, categoria);
				carros.add(car);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}

		return carros;
	}

	public List<Carro> readByModelo(String modelo) throws DaoException {
		List<Carro> carros = new ArrayList<>();
		Carro car;

		try {
			this.stmRByModelo.setString(1, modelo);
			ResultSet rs = this.stmRByModelo.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				int ano = rs.getInt("ano");
				String marca = rs.getString("marca");
				String categoria = rs.getString("categoria");

				car = new Carro(id, ano, modelo, marca, categoria);
				carros.add(car);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}

		return carros;
	}

	public long create(Carro carro) throws DaoException {

		long id = 0;
		try {
			System.out.println("modelo>> " + carro.getModelo());
			this.stmC.setString(1, carro.getModelo());
			this.stmC.setString(2, carro.getMarca());
			this.stmC.setInt(3, carro.getAno());
			this.stmC.setString(4, carro.getCategoria());

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

	public int update(Carro carro) {
		int r = 0;

		try {
			this.stmU.setString(1, carro.getModelo());
			this.stmU.setString(2, carro.getMarca());
			this.stmU.setInt(3, carro.getAno());
			this.stmU.setString(4, carro.getCategoria());
			this.stmU.setLong(5, carro.getId());

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
			stmRById.close();
			stmRByAno.close();
			stmRByModelo.close();
			stmU.close();
			stmD.close();

		} catch (SQLException ex) {
			throw new DaoException("Falha ao fechar DAO: " + ex.getMessage());
		}
	}

}
