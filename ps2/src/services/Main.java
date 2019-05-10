package services;

import java.util.List;

import domains.Aplicativo;
import integracaoJDBC.AplicativoDAO;
import integracaoJDBC.ConexaoJDBC;

public class Main {
	public static void main(String [] args) throws Exception {
		
		ConexaoJDBC conexao = new ConexaoJDBC("root", "projeto", "localhost", 3306, "ps2");
		AplicativoDAO dao = new AplicativoDAO(conexao);

		List<Aplicativo> aplicativo = dao.read();

		System.out.println("LISTA DE aplicativo:");
		for (int i = 0; i<aplicativo.size(); i++) {
			Aplicativo p = (Aplicativo) aplicativo.get(i);
			System.out.println("* " + p.getNome());
	}
		
		//IMPORTANTE!!!!!!!
		dao.close();
		conexao.close();
				
	}
}
