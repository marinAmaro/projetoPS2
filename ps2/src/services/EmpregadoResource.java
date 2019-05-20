package services;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domains.Empregado;
import integracaoJDBC.DaoException;
import integracaoJDBC.EmpregadoDAO;
import io.dropwizard.jersey.params.LongParam;

@Path("/empregados")
@Produces(MediaType.APPLICATION_JSON)
public class EmpregadoResource {

	private EmpregadoDAO dao;

	public EmpregadoResource(EmpregadoDAO dao) {
		this.dao = dao;
	}

	@GET
	public List<Empregado> read() throws DaoException {
		List<Empregado> empregados;
		empregados = dao.read();
		return empregados;
	}

	@POST
	public Empregado create(Empregado emp) throws DaoException {
		Empregado resp;
		try {
			long id = dao.create(emp);
			emp.setId(id);
			resp = emp;
		} catch (DaoException ex) {
			ex.printStackTrace();
			resp = null;
		}
		return resp;
	}

	@GET
	@Path("{id}")
	public Empregado readOne(@PathParam("id") LongParam id) {
		long idEmpregado = id.get();
		// Precisa implementar no DAO
		return null;
	}

	@PUT
	@Path("{id}")
	public Empregado update(@PathParam("id") LongParam id, Empregado emp) throws DaoException {
		Empregado resp;
		emp.setId(id.get());
		dao.update(emp);
		resp = emp;
		return resp;
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") LongParam id) throws DaoException, SQLException {
		Empregado emp;
		try {
			emp = dao.readById(id.get());
		} catch (DaoException ex) {
			ex.printStackTrace();
			throw new WebApplicationException("Erro ao buscar Aplicativo com id=" + id.get(), 500);
		}
		if (emp != null) {
			dao.delete(id.get());
		} else {
			throw new WebApplicationException("Aplicativo com id=" + id.get() + " não encontrado!", 404);
		}
		return Response.ok().build();
	}
}
