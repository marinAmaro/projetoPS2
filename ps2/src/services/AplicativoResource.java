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
import integracaoJDBC.AplicativoDAO;
import integracaoJDBC.DaoException;
import io.dropwizard.jersey.params.LongParam;

@Path("/aplicativos")
@Produces(MediaType.APPLICATION_JSON)
public class AplicativoResource {

	private AplicativoDAO dao;

	public AplicativoResource(AplicativoDAO dao) {
		this.dao = dao;
	}

	@GET
	public List<Aplicativo> read() throws DaoException {
		List<Aplicativo> aplicativos;
		try {
			aplicativos = dao.read();
		} catch (DaoException ex) {
			ex.printStackTrace();
			aplicativos = null;
		}
		return aplicativos;
	}

	@GET
    @Path("/id/{id}")
    public Aplicativo readById(@PathParam("id") LongParam id) {
        long idAplicativo = id.get();
        Aplicativo app = null;
        
        try {
        	app = dao.readById(idAplicativo);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return app;
    }

	@GET
	@Path("/nome/{nome}")
	public Aplicativo readByName(@PathParam("nome") LongParam nome) throws DaoException{
		String nomeApp = String.valueOf(nome.get());
		Aplicativo app = null;
		
		try {
			app = dao.readByNome(nomeApp);	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return app;
	}
	
	@GET
	@Path("/desenvolvedor/{desenvolvedor}")
	public Aplicativo readByDesenvolvedor(@PathParam("desenvolvedor") LongParam desenvolvedor) throws DaoException{
		String desenvolvedorApp = String.valueOf(desenvolvedor.get());
		Aplicativo app = null;
		
		try {
			app = dao.readByDesenvolvedor(desenvolvedorApp);	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return app;
	}

	@POST
	public Aplicativo create(Aplicativo app) throws DaoException {
		Aplicativo resp;
		try {
			long id = dao.create(app);
			app.setId(id);
			resp = app;
		} catch (DaoException ex) {
			ex.printStackTrace();
			resp = null;
		}
		return resp;
	}

	@PUT
	@Path("{id}")
	public Aplicativo update(@PathParam("id") LongParam id, Aplicativo app) throws DaoException {
		Aplicativo resp;
		try {
			app.setId(id.get());
			dao.update(app);
			resp = app;
		} catch (DaoException ex) {
			ex.printStackTrace();
			resp = null;
		}
		return resp;
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") LongParam id) throws DaoException {
		Aplicativo app;
		try {
			app = dao.readById(id.get());
		} catch (DaoException ex) {
			ex.printStackTrace();
			throw new WebApplicationException("Erro ao buscar Aplicativo com id=" + id.get(), 500);
		}
		if (app != null) {
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
