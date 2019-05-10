package services;

import java.sql.SQLException;
import java.util.List;

import domains.Aplicativo;
import domains.Carro;
import integracaoJDBC.AplicativoDAO;
import integracaoJDBC.CarroDAO;

public class Main {
	public static void main(String [] args) throws SQLException {
		
		//------------------------- TESTE APLICATIVO
		AplicativoDAO app = new AplicativoDAO();
		List<Aplicativo> aplica = app.read();
		
		for (int i = 0; i < aplica.size(); i++) {
			System.out.println(aplica.get(i).getNome());
			System.out.println(aplica.get(i).getNumDownloads());
			System.out.println(aplica.get(i).getDesenvolvedor());
			System.out.println(aplica.get(i).getId());
			System.out.println("\n\n");
		
		}
		
		//------------------------- TESTE CARRO
		CarroDAO car = new CarroDAO();

		System.out.println("TESTE READ:: ");
		List<Carro> carros = car.read();
		for (int i = 0; i < carros.size(); i++) {
			System.out.println(carros.get(i).getId());
			System.out.println(carros.get(i).getAno());
			System.out.println(carros.get(i).getCategoria());
			System.out.println(carros.get(i).getModelo());
			System.out.println(carros.get(i).getMarca());
			System.out.println("\n\n");
		}
		
		System.out.println("TESTE CREATE:: ");
		Carro carro = new Carro();
		carro.setAno(2013);
		carro.setCategoria("minivan");
		carro.setModelo("desconhecido");
		carro.setMarca("chevrolet");
		
		Carro carroCreate = car.create(carro);
		System.out.println(carroCreate.toString());
		
		System.out.println("TESTE UPDATE:: ");
		carroCreate.setModelo("Gol");
		int teste = car.update(carroCreate);
		System.out.println(teste);
		
		System.out.println("TESTE DELETE:: ");
		long id = 5;
		int teste2 = car.delete(id);
		System.out.println(teste2);
		
		
		//------------------------- TESTE EMPREGADO
		
	}
}
