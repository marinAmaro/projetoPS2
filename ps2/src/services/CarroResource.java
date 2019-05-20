package services;

import java.sql.SQLException;
import java.util.ArrayList;
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
    @Path("{id}")
    public Carro readById(@PathParam("id") LongParam id) {
        long idCarro = id.get();
        Carro car = null;
        
        try {
        	car = dao.readById(idCarro);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return car;
    }

	@GET
	@Path("{modelo}")
	public List<Carro> readByName(@PathParam("nome") LongParam modelo) throws DaoException{
		String nomeCarro = String.valueOf(modelo.get());
		List<Carro> car = null;
		
		try {
			car = dao.readByModelo(nomeCarro);	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return car;
	}
	
	@GET
	@Path("{ano}")
	public List<Carro> readByAno(@PathParam("ano") LongParam ano) throws DaoException{
		int anoCarros = Integer.parseInt(String.valueOf(ano.get()));
		List<Carro> car = new ArrayList<Carro>();
		
		try {
			car = dao.readByAno(anoCarros);	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return car;
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
	public Response delete(@PathParam("id") LongParam id) throws DaoException, SQLException {
        Carro car;
		try
		{
			car = dao.readById(id.get());
		}catch(DaoException ex)
		{
			ex.printStackTrace();
			throw new WebApplicationException("Erro ao buscar Carro com id="+ id.get(),500);
		}
		if(car != null)
		{
			dao.delete(id.get());
		}
		else
		{
			throw new WebApplicationException("Carro com id=" + id.get() + " não encontrado!", 404);
		}
		return Response.ok().build();
    }
}
