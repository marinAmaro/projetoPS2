package main;

import integracaoJDBC.AplicativoDAO;
import integracaoJDBC.CarroDAO;
import integracaoJDBC.ConexaoJDBC;
import integracaoJDBC.EmpregadoDAO;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import services.AplicativoResource;
import services.CarroResource;
import services.EmpregadoResource;


public class AplicativoApp extends Application<Configuration> {

	public static void main(String[] args) throws Exception {
		new AplicativoApp().run(new String[] {"server"});
	}
	
    @Override
    public void initialize(final Bootstrap<Configuration> bootstrap) {
        //Mapeia a pasta "src/html" para a url "http://localhost:8080/" e
        // por padrao abre o arquivo index.html quando um recurso especifico
        // nao for informado
        bootstrap.addBundle(new AssetsBundle("/html", "/", "index.html"));
    }	
    
	@Override
	public void run(Configuration configuration, Environment environment) {
		try {
			ConexaoJDBC conexao;
			conexao = new ConexaoJDBC("root", "projeto", "localhost", 3306, "ps2");
			
			//
			//
			AplicativoDAO professorDao = new AplicativoDAO(conexao);
			environment.jersey().register(new AplicativoResource(professorDao));
			
			//
			//
			CarroDAO carroDao = new CarroDAO(conexao);
			environment.jersey().register(new CarroResource(carroDao));
			
			//
			//
			EmpregadoDAO empregadoDao = new EmpregadoDAO(conexao);
			environment.jersey().register(new EmpregadoResource(empregadoDao));
			
			environment.jersey().setUrlPattern("/api/*");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
