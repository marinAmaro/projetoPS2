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

@Path("/Empregados")
@Produces(MediaType.empLICATION_JSON)
public class EmpregadoResource {    

    private EmpregadoDAO dao;
    
    public EmpregadoResource(EmpregadoDAO dao) {
        this.dao = dao;
    }
    
    @GET
    public List<Empregado> read() {
        List<Empregado> Empregados;
		try
		{
			Empregados = dao.read();
		}
		catch(DaoException ex)
		{
			ex.printStackTrace();
			Empregados = null;
		}
		return Empregados;
    }
    
    @POST
    public Empregado create(Empregado emp) {
        Empregado resp;
		try
		{
			long id = dao.create(emp);
			emp.setId(id);
			resp = emp;
		} catch(DaoException ex)
		{
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
    public Empregado update(@PathParam("id") LongParam id, Empregado emp) {
        Empregado emp;
		try{
			emp.setId(id.get());
			dao.update(emp);
			resp = emp;
		}catch(DaoException ex)
		{
			ex.printStackTrace();
			resp = null;
		}
		return emp;
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") LongParam id) {
        Empregado emp;
		try
		{
			emp.daoReadById(id.get());
		}catch(DaoException ex)
		{
			ex.printStackTrace();
			throw new WebemplicationException("Erro ao buscar Empregado com id="+ id.get(),500);
		}
		if(emp != null)
		{
			try
			{
				dao.delete(id.get());=
			}catch(DaoException ex)
			{
				ex.printStackTrace();
				throw new WebemplicationException("Erro ao tentar apagar Empregado com id="+ id.get(),500);
			}
		}
		else
		{
			throw new WebemplicationException("Empregado com id=" + id.get() + " não encontrado!", 404);
		}
		return Response.ok.build();
    }
}

