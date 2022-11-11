/*
INTEGRANTES/AUTORES:
Jessica Bilbao Restrepo
Santiago Gómez Montoya
*/

CREATE TABLE Clientes(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	NIT TEXT NOT NULL,
	nombre TEXT NOT NULL,
	direccion TEXT NOT NULL,
	telefono TEXT NOT NULL,
	ciudad TEXT 
);

CREATE TABLE Conductores(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	DNI TEXT NOT NULL,
	nombre TEXT NOT NULL,
	apellido TEXT NOT NULL,
	empresa TEXT NOT NULL
);

CREATE TABLE Productos(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	tipo TEXT NOT NULL,
	precio INT NOT NULL
);

CREATE TABLE Despachos(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	fecha TEXT NOT NULL,
	peso FLOAT NOT NULL,
	ciudad_destino TEXT NOT NULL,
	id_cliente TEXT NOT NULL,
	id_conductor INT NOT NULL,
	id_producto INT NOT NULL,
	FOREIGN KEY(id_cliente) REFERENCES Clientes(id),
	FOREIGN KEY(id_conductor) REFERENCES Conductores(id),
	FOREIGN KEY(id_producto) REFERENCES Productos(id)
);

/* Añadiendo datos */

INSERT INTO Clientes VALUES(null, "658466545", "Pepito", "calle 45 #96-03", "3165784256", "Cali");
INSERT INTO Clientes VALUES(null, "213216556", "Pepita", "calle 56 #63-25", "3158624723", "Bogota");
INSERT INTO Clientes VALUES(null, "987513222", "Gabriel", "carrera 45 #16-78", "3205698546", "Cali");
INSERT INTO Clientes VALUES(null, "987466665", "Carolina", "carrera 65 #86-03", "3125654860", "Medellin");

SELECT * FROM Clientes

DROP TABLE Despachos

INSERT INTO Conductores VALUES(null, "11365479", "Conductor1", "Ap1", "Conductores S.A.S");
INSERT INTO Conductores VALUES(null, "8465845", "Conductor2", "Ap2", "Conductorcitos");
INSERT INTO Conductores VALUES(null, "8947654", "Conductor3", "Ap3", "Consuctores S.A.S");
INSERT INTO Conductores VALUES(null, "8464546", "Conductor4", "Ap4", "Consuctorcitos");

INSERT INTO Productos VALUES(null, "Algodon reciclaje", 5000);
INSERT INTO Productos VALUES(null, "Algodon indio", 10000);
INSERT INTO Productos VALUES(null, "Algodon americano", 20000);
INSERT INTO Productos VALUES(null, "Algodon egipcio", 30000);

INSERT INTO Despachos VALUES(null, "26/01/2021", 15.5, "Medellin", 1, 1, 2);
INSERT INTO Despachos VALUES(null, "15/03/2022", 10.5, "Bogota", 2, 2, 3);
INSERT INTO Despachos VALUES(null, "23/02/2022", 18.5, "Barranquilla", 3, 4, 1);
INSERT INTO Despachos VALUES(null, "05/04/2022", 6.5, "Cali", 1, 4, 1);
INSERT INTO Despachos VALUES(null, "26/01/2021", 15, "Medellin", 3, 3, 2);
INSERT INTO Despachos VALUES(null, "26/01/2021", 20, "Medellin", 3, 2, 1);

/* 1.	Cantidad de algodón despachado en el año */
SELECT sum(peso) as "Peso total despachado" FROM Despachos;

/* 2.	Tipo de algodón más vendido */
/*---------------> Segun despachos*/
SELECT tipo, count(tipo) as "Cantidad de despachos" FROM Despachos as d
JOIN Productos as p ON p.id = d.id_producto
GROUP BY tipo
ORDER BY count(tipo) DESC
LIMIT 1;  

/*---------------> Segun peso*/
SELECT tipo, sum(peso) as "Cantidad de algodon" FROM Despachos as d
JOIN Productos as p ON p.id = d.id_producto
GROUP BY tipo
ORDER BY sum(peso) DESC
LIMIT 1;

/* 3.	Ciudad a la que se realizaron más despachos */
SELECT ciudad_destino, count(*) as "Cantidad de despachos" FROM Despachos
GROUP BY ciudad_destino
ORDER BY count(*) DESC
LIMIT 1;  

/* 4.	Conductor (DNI, nombre, apellido, empresa) más frecuente */
SELECT  DNI, nombre, apellido, empresa, COUNT(*) as "frecuencia"
FROM Conductores 
GROUP BY DNI
ORDER BY COUNT(*) DESC
LIMIT 1;

/* 5.	Información de los Clientes en la ciudad de Cali (NIT, Nombre, dirección, teléfono) */
SELECT * FROM Clientes
WHERE ciudad = 'Cali';

/* 6.	Ciudad a la que se hacen más despachos de algodón reciclado. */
SELECT ciudad_destino, COUNT(*) AS "ciudad_con_mas_despachos_de_algodon_reciclado"
FROM Despachos
GROUP BY ciudad_destino
ORDER BY COUNT(*) DESC
LIMIT 1;

/* 7.	Adicione el campo Alias (texto) a la tabla conductores. */
ALTER TABLE Conductores ADD COLUMN alias TEXT

/* 8.	Cree una sentencia SQL para subir el precio de los productos en un 7% */
UPDATE Productos SET precio = precio*(1+0.07);

