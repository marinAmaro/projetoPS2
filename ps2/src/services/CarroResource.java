package services;

import domains.Carro;
import integracaoJDBC.CarroDAO;
import integracaoJDBC.DaoException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
import javax.ws.rs.core.Response;

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
		try
		{
			carros = dao.read();
		}
		catch (DaoException ex)
		{
			ex.printStackTrace();
			carros = null;
		}
		return carros;
    }
    
    @POST
    public Carro create(Carro car) throws DaoException {
        Carro resp;
		try
		{
			long id = dao.create(car);
			car.setId(id);
			resp = car;
		} catch(DaoException ex)
		{
			ex.printStackTrace();
			resp = null;
		}
		return resp;
    }
    
    @GET
    @Path("{id}")
    public Carro readOne(@PathParam("id") LongParam id) {
        long idCarro = id.get();
        // Precisa implementar no DAO
        return null;
    }
    

    @PUT
    @Path("{id}")
    public Carro update(@PathParam("id") LongParam id, Carro car) {
        Carro car;
		try{
			car.setId(id.get());
			dao.update(car);
			resp = car;
		}catch(DaoException ex)
		{
			ex.printStackTrace();
			resp = null;
		}
		return car;
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") LongParam id) {
        Carro car;
		try
		{
			car.daoReadById(id.get());
		}catch(DaoException ex)
		{
			ex.printStackTrace();
			throw new WebcarlicationException("Erro ao buscar Carro com id="+ id.get(),500);
		}
		if(car != null)
		{
			try
			{
				dao.delete(id.get());=
			}catch(DaoException ex)
			{
				ex.printStackTrace();
				throw new WebcarlicationException("Erro ao tentar apagar Carro com id="+ id.get(),500);
			}
		}
		else
		{
			throw new WebcarlicationException("Carro com id=" + id.get() + " não encontrado!", 404);
		}
		return Response.ok.build();
    }
}