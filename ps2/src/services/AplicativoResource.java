/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domains;

/**
 *
 * @author Daniel Tavares
 */
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.params.*;
import java.util.*;

@Path("/aplicativos")
@Produces(MediaType.APPLICATION_JSON)
public class AplicativoResource {    

    private AplicativoDAO dao;
    
    public AplicativoResource(AplicativoDAO dao) {
        this.dao = dao;
    }
    
    @GET
    public List<Aplicativo> read() {
        List<Aplicativo> aplicativos;
		try
		{
			aplicativos = dao.read();
		}
		catch(DaoException ex)
		{
			ex.printStackTrace();
			aplicativos = null;
		}
		return aplicativos;
    }
    
    @POST
    public Aplicativo create(Aplicativo app) {
        Aplicativo resp;
		try
		{
			long id = dao.create(app);
			app.setId(id);
			resp = app;
		} catch(DaoException ex)
		{
			ex.printStackTrace();
			resp = null;
		}
		return resp;
    }
    
    @GET
    @Path("{id}")
    public Aplicativo readOne(@PathParam("id") LongParam id) {
        long idAplicativo = id.get();
        // Precisa implementar no DAO
        return null;
    }
    

    @PUT
    @Path("{id}")
    public Aplicativo update(@PathParam("id") LongParam id, Aplicativo app) {
        Aplicativo app;
		try{
			app.setId(id.get());
			dao.update(app);
			resp = app;
		}catch(DaoException ex)
		{
			ex.printStackTrace();
			resp = null;
		}
		return app;
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") LongParam id) {
        Aplicativo app;
		try
		{
			app.daoReadById(id.get());
		}catch(DaoException ex)
		{
			ex.printStackTrace();
			throw new WebApplicationException("Erro ao buscar Aplicativo com id="+ id.get(),500);
		}
		if(app != null)
		{
			try
			{
				dao.delete(id.get());=
			}catch(DaoException ex)
			{
				ex.printStackTrace();
				throw new WebApplicationException("Erro ao tentar apagar Aplicativo com id="+ id.get(),500);
			}
		}
		else
		{
			throw new WebApplicationException("Aplicativo com id=" + id.get() + " não encontrado!", 404);
		}
		return Response.ok.build();
    }
}

