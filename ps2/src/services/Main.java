package services;

import java.util.List;

import domains.Aplicativo;
import integracaoJDBC.AplicativoDAO;
import integracaoJDBC.ConexaoJDBC;

public class Main {
	public static void main(String [] args) throws Exception {
		
		ConexaoJDBC conexao = new ConexaoJDBC("root", "projeto", "localhost", 3306, "ps2");
		
		//TESTE APLICATIVO ---- READ
		AplicativoDAO dao = new AplicativoDAO(conexao);
		List<Aplicativo> aplicativo = dao.read();

		System.out.println("LISTA DE APLICATIVO:: ");
		for (int i = 0; i<aplicativo.size(); i++) {
			Aplicativo p = (Aplicativo) aplicativo.get(i);
			System.out.println("* " + p.getNome());
	}
				
		//TESTE APLICATIVO ---- CREATE
		Aplicativo appCriar= new Aplicativo();
		appCriar.setDesenvolvedor("Marina teste");
		appCriar.setNumDownLoads(550);
		appCriar.setNome("CRUD");
		
		
		System.out.println("CRIAÇÃO APLICATIVO:: ");
		Aplicativo appCriado = dao.create(appCriar);
		System.out.println("ID:: " + appCriado.getId());
		
		//TESTE APLICATIVO ---- UPDATE
		appCriado.setNumDownLoads(1573);
		int nUpdates = dao.update(appCriado);
		System.out.println("nUpdates->> " + nUpdates);
		
		
		//TESTE APLICATIVO ---- DELETE
		long id = appCriado.getId();
		int deletados = dao.delete(id);
		System.out.println("numero de deleados:: " + deletados);
		
		//IMPORTANTE!!!!!!!
		dao.close();
		conexao.close();
	}
}
