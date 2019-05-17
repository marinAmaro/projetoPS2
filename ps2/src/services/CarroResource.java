package services;

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

import domains.Aplicativo;
import domains.Carro;
import integracaoJDBC.CarroDAO;
import integracaoJDBC.DaoException;
import io.dropwizard.jersey.params.LongParam;

@Path("/carros")
@Produces(MediaType.APPLICATION_JSON)
public class CarroResource {

	private CarroDAO dao;

	public CarroResource(CarroDAO dao) {
		this.dao = dao;
	}

	@GET
	public List<Carro> read() throws DaoException {
		List<Carro> carros;
		carros = dao.read();
		return carros;
	}

	@POST
	public Carro create(Carro car) throws DaoException {
		Carro resp;
		try {
			long id = dao.create(car);
			car.setId(id);
			resp = car;
		} catch (DaoException ex) {
			ex.printStackTrace();
			resp = null;
		}
		return resp;
	}

	@GET
	@Path("{id}")
	public Aplicativo readOne(@PathParam("id") LongParam id) {
		long idCarro = id.get();
		// Precisa implementar no DAO
		return null;
	}

	@PUT
	@Path("{id}")
	public Carro update(@PathParam("id") LongParam id, Carro car) throws DaoException {
		Carro resp;
		car.setId(id.get());
		dao.update(car);
		resp = car;
		return resp;
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") LongParam id) throws DaoException {
		Carro car;
		try {
			car = dao.readById(id.get());
		} catch (DaoException ex) {
			ex.printStackTrace();
			throw new WebApplicationException("Erro ao buscar Aplicativo com id=" + id.get(), 500);
		}
		if (car != null) {
			try {
				dao.delete(id.get());
			} catch (DaoException ex) {
				ex.printStackTrace();
				throw new WebApplicationException("Erro ao tentar apagar Aplicativo com id=" + id.get(), 500);
			}
		} else {
			throw new WebApplicationException("Aplicativo com id=" + id.get() + " não encontrado!", 404);
		}
		return Response.ok().build();
	}
}
