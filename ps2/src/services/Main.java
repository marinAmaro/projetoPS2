package services;

import java.util.List;

import domains.Aplicativo;
import integracaoJDBC.AplicativoDAO;

public class Main {
	public static void main(String [] args) {
		AplicativoDAO app = new AplicativoDAO();
		List<Aplicativo> aplica = app.listarAplicativos();
		
		for (int i = 0; i < aplica.size(); i++) {
			System.out.println(aplica.get(i).getNome());
			System.out.println(aplica.get(i).getNumDownloads());
			System.out.println(aplica.get(i).getDesenvolvedor());
			System.out.println(aplica.get(i).getId());
			System.out.println("\n\n");
		
		}
	}
}
