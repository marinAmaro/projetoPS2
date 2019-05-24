//------------------------------------------------
//Código criação EMPREGADO

CREATE TABLE Empregado (
  id INT AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  cargo VARCHAR(32) NOT NULL,
  salario DECIMAL(8,2),
  PRIMARY KEY (id)
);

//Valores para teste
INSERT INTO Empregado (nome,cargo,salario) VALUES ('Marina', 'estagiária', 1500.00);
INSERT INTO Empregado (nome,cargo,salario) VALUES ('Daniel', 'desenvolvedor', 4000.00);
INSERT INTO Empregado (nome,cargo,salario) VALUES ('Gabrielle', 'gerente de projetos', 3500.00);

//------------------------------------------------
//Código criação APLICATIVO

CREATE TABLE Aplicativo (
id INT AUTO_INCREMENT,
nome VARCHAR (30) NOT NULL,
desenvolvedor VARCHAR (30) NOT NULL,
numero_downloads INT,
PRIMARY KEY (id)
 );

 //Valores para teste
INSERT INTO Aplicativo (nome,desenvolvedor,numero_downloads) VALUES ('Facebook', 'Julia', 200);
INSERT INTO Aplicativo (nome,desenvolvedor,numero_downloads) VALUES ('Instagram','Daniel', 139);
INSERT INTO Aplicativo (nome,desenvolvedor,numero_downloads) VALUES ('Snapchat','Marina', 233);
INSERT INTO Aplicativo (nome,desenvolvedor,numero_downloads) VALUES ('Twitter', 'Gabrielle', 213);

//-----------------------------------------------
//Código criação CARRO

CREATE TABLE Carro(
id INT AUTO_INCREMENT,
modelo VARCHAR(50) NOT NULL,
marca VARCHAR(50) NOT NULL,
ano BIGINT NOT NULL,
categoria VARCHAR(50) NOT NULL,
PRIMARY KEY (id)
);

INSERT INTO Carro (modelo,marca,ano,categoria) VALUES('Meriva','Chevrolet',2011,'Minivan');
INSERT INTO Carro (modelo,marca,ano,categoria) VALUES('Gol','Volkswagen',2000,'Supermini');
INSERT INTO Carro (modelo,marca,ano,categoria) VALUES('Civic','Honda', 2010,'Sedan médio');
INSERT INTO Carro (modelo,marca,ano,categoria) VALUES('Corolla','Toyota', 2018,'Sedan médio');
