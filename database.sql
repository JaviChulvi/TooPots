CREATE TABLE TipoActividad (
    id SERIAL NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	nivel VARCHAR(15) NOT NULL,
	CONSTRAINT cp_tipoactividad PRIMARY KEY (id),
	CONSTRAINT ri_tipoactividad_nivel CHECK (nivel='bajo' OR nivel='medio' OR nivel='alto' OR nivel='extremo')
);

CREATE TABLE Monitor (
    dni VARCHAR(10) NOT NULL,
    estado VARCHAR(15) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    domicilio VARCHAR(70) NOT NULL,
    email VARCHAR(30) NOT NULL,
    iban VARCHAR(20) NOT NULL,
    foto VARCHAR(50) NOT NULL,
    contraseña VARCHAR(50) NOT NULL,
    CONSTRAINT cp_monitor PRIMARY KEY (dni),
    CONSTRAINT ri_monitor_estado CHECK (estado='aceptada' OR estado='rechazada' OR estado='pendiente')
);

CREATE TABLE Oferta (
    nombre VARCHAR(25) NOT NULL,
    descripcion VARCHAR(100),
    descuento FLOAT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    CONSTRAINT cp_oferta PRIMARY KEY (nombre),
    CONSTRAINT ri_tipo CHECK (tipo='menor18' OR tipo='entre18-50' OR tipo='mayor50' OR tipo='grupo' OR tipo='temporadabaja' OR tipo='total')
);

CREATE TABLE Actividad (
    id SERIAL NOT NULL,
    idTipoActividad INTEGER NOT NULL,
    estado VARCHAR(15) NOT NULL,
    nombre VARCHAR(40) NOT NULL,
    descripcion VARCHAR(300) NOT NULL,
    duracion TIME NOT NULL,
    fecha DATE NOT NULL,
    minAsistentes INTEGER NOT NULL,
    maxAsistentes INTEGER NOT NULL,
    lugar VARCHAR(100) NOT NULL,
    puntoDeEncuentro VARCHAR(150) NOT NULL,
    horaDeEncuentro TIME NOT NULL,
    monitor VARCHAR(10) NOT NULL,
    precioBruto FLOAT NOT NULL,
    ofertaAplicada VARCHAR(25),
    CONSTRAINT cp_actividad PRIMARY KEY (id),
    CONSTRAINT ca_actividad FOREIGN KEY (idTipoActividad) REFERENCES TipoActividad(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_monitor FOREIGN KEY (monitor) REFERENCES Monitor(dni) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT ca_oferta FOREIGN KEY (ofertaAplicada) REFERENCES Oferta(nombre) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ri_actividad_duracion CHECK (duracion>'00:00'),
    CONSTRAINT ri_actividad_estado CHECK (estado='abierta' OR estado='cerrada' OR estado='completa' OR estado='cancelada')
);
    
CREATE TABLE Acreditacion (
	certificado VARCHAR(50) NOT NULL,
	dniMonitor VARCHAR(10) NOT NULL,
	estado VARCHAR(15),
	CONSTRAINT cp_certificado PRIMARY KEY (certificado),
	CONSTRAINT ca_monitor FOREIGN KEY (dniMonitor) REFERENCES Monitor(dni) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ri_acreditacion_estado CHECK (estado='aceptada' OR estado='rechazada'OR estado='pendiente')
);

CREATE TABLE Acredita(
    idTipoActividad INTEGER NOT NULL,
    certificado VARCHAR(50) NOT NULL,
    CONSTRAINT cp_acredita PRIMARY KEY (idTipoActividad, certificado),
    CONSTRAINT ca_tipoactividad FOREIGN KEY (idTipoActividad) REFERENCES TipoActividad(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_acreditacion FOREIGN KEY (certificado) REFERENCES Acreditacion(certificado) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Cliente (
    dni VARCHAR(10) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(30) NOT NULL,
    sexo VARCHAR(10)NOT NULL,
    fechaNacimiento DATE NOT NULL,
    contraseña VARCHAR(50) NOT NULL,
    CONSTRAINT cp_client PRIMARY KEY (dni)
);


CREATE TABLE Prefiere (
	dniCliente VARCHAR(10) NOT NULL,
	idTipoActividad INTEGER NOT NULL,
	CONSTRAINT cp_prefiere PRIMARY KEY (dniCliente, idTipoActividad),
	CONSTRAINT ca_tipoactividad FOREIGN KEY (idTipoActividad) REFERENCES TipoActividad(id) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ca_cliente FOREIGN KEY (dniCliente) REFERENCES Cliente(dni) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE ImagenPromocional (
	idActividad INTEGER NOT NULL,
	imagen VARCHAR(50) NOT NULL,
	CONSTRAINT cp_imagenpromocional PRIMARY KEY (idActividad, imagen),
	CONSTRAINT ca_actividad FOREIGN KEY (idActividad) REFERENCES Actividad(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Reserva (
	idActividad INTEGER NOT NULL,
	dniCliente VARCHAR(10) NOT NULL,
	estadoPago VARCHAR(15) NOT NULL,
	fecha DATE NOT NULL,
	numJubilados INTEGER NOT NULL,
	numAdultos INTEGER NOT NULL,
	numMenores INTEGER NOT NULL,
	precioTotal FLOAT NOT NULL,
	CONSTRAINT cp_reserva PRIMARY KEY (idActividad, dniCliente),
	CONSTRAINT ca_actividad FOREIGN KEY (idActividad) REFERENCES Actividad(id) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ca_cliente FOREIGN KEY (dniCliente) REFERENCES Cliente(dni) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ri_reserva_estadoPago CHECK (estadoPago='pendiente' OR estadoPago='pagado')
);

CREATE TABLE Entrada (
    idActividad INTEGER NOT NULL,
    tipo VARCHAR(15) NOT NULL,
    precioBruto FLOAT NOT NULL,
    CONSTRAINT cp_entrada PRIMARY KEY (idActividad, tipo),
    CONSTRAINT ca_actividad FOREIGN KEY (idActividad) REFERENCES Actividad(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ri_entrada_tipo CHECK (tipo='menor18' OR tipo='entre18-50' OR tipo='mayor50' OR tipo='grupo' OR tipo='temporadabaja' OR tipo='temporadaalta')
);

CREATE TABLE TextoCliente (
    id SERIAL NOT NULL,
	idActividad INTEGER NOT NULL,
	fecha DATE NOT NULL,
	mensaje VARCHAR(150) NOT NULL,
	CONSTRAINT cp_textocliente PRIMARY KEY (id),
	CONSTRAINT ca_actividad FOREIGN KEY (idActividad) REFERENCES Actividad(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO monitor VALUES('admin', 'aceptada', 'admin', '/', 'admin@toopots.com', '666', 'root.png', 'swryw0RUCuHWDOFndaeTp5DDRGt/13QY');
/* admin contraseña: admin*/

INSERT INTO tipoactividad (nombre, nivel) VALUES ('ciclismo','bajo');
INSERT INTO tipoactividad (nombre, nivel) VALUES ('ciclismo','medio');
INSERT INTO tipoactividad (nombre, nivel) VALUES ('ciclismo','alto');
INSERT INTO tipoactividad (nombre, nivel) VALUES ('ciclismo','extremo');

INSERT INTO tipoactividad (nombre, nivel) VALUES ('escalada','bajo');
INSERT INTO tipoactividad (nombre, nivel) VALUES ('escalada','medio');
INSERT INTO tipoactividad (nombre, nivel) VALUES ('escalada','alto');
INSERT INTO tipoactividad (nombre, nivel) VALUES ('escalada','extremo');

INSERT INTO tipoactividad (nombre, nivel) VALUES ('Paddle Surf','bajo');
INSERT INTO tipoactividad (nombre, nivel) VALUES ('Paddle Surf','medio');
INSERT INTO tipoactividad (nombre, nivel) VALUES ('Paddle Surf','alto');
INSERT INTO tipoactividad (nombre, nivel) VALUES ('Paddle Surf','extremo');

/*
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (1,'abierta','Ruta antenes Vilafamés','Castelló,font de Codina, antenes Vilafamés, baixada a la Pobla per Itaca, esmorzar,pujada al Bartolo','02:00:00','2019-05-12',5,20,'Puebla Tornesa','Castellón de la Plana','09:00:00','admin',20.00);
INSERT INTO ImagenPromocional VALUES(1, 'antenesVilafames.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (2,'abierta','Ruta antenes Vilafamés','Castelló,font de Codina, antenes Vilafamés, baixada a la Pobla per Itaca, esmorzar,pujada al Bartolo','02:00:00','2019-05-12',5,20,'Puebla Tornesa','Castellón de la Plana','09:00:00','admin',20.00);
INSERT INTO ImagenPromocional VALUES(2, 'antenesVilafames.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (3,'abierta','Ruta antenes Vilafamés','Castelló,font de Codina, antenes Vilafamés, baixada a la Pobla per Itaca, esmorzar,pujada al Bartolo','02:00:00','2019-05-12',5,20,'Puebla Tornesa','Castellón de la Plana','09:00:00','admin',20.00);
INSERT INTO ImagenPromocional VALUES(3, 'antenesVilafames.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (4,'abierta','Ruta antenes Vilafamés','Castelló,font de Codina, antenes Vilafamés, baixada a la Pobla per Itaca, esmorzar,pujada al Bartolo','02:00:00','2019-05-12',5,20,'Puebla Tornesa','Castellón de la Plana','09:00:00','admin',20.00);
INSERT INTO ImagenPromocional VALUES(4, 'antenesVilafames.jpg');


INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (5,'abierta','Rocódromo y búlder','En el rocódromo tienes la facilidad de estar instaladas las vías en diferentes niveles con sus propias cintas express y cuerdas dinámicas.','01:30:00','2019-05-12',5,20,'EsportVerd Castellón','C/ Francia, 12006 Castellón (España) Polígono Industrial Acceso Sur, nave 8C','17:00:00','admin',15.00);
INSERT INTO ImagenPromocional VALUES(5, 'escalada.jpeg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (6,'abierta','Rocódromo y búlder','En el rocódromo tienes la facilidad de estar instaladas las vías en diferentes niveles con sus propias cintas express y cuerdas dinámicas.','01:30:00','2019-05-12',5,20,'EsportVerd Castellón','C/ Francia, 12006 Castellón (España) Polígono Industrial Acceso Sur, nave 8C','17:00:00','admin',15.00);
INSERT INTO ImagenPromocional VALUES(6, 'escalada.jpeg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (7,'abierta','Rocódromo y búlder','En el rocódromo tienes la facilidad de estar instaladas las vías en diferentes niveles con sus propias cintas express y cuerdas dinámicas.','01:30:00','2019-05-12',5,20,'EsportVerd Castellón','C/ Francia, 12006 Castellón (España) Polígono Industrial Acceso Sur, nave 8C','17:00:00','admin',15.00);
INSERT INTO ImagenPromocional VALUES(7, 'escalada.jpeg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (8,'abierta','Rocódromo y búlder','En el rocódromo tienes la facilidad de estar instaladas las vías en diferentes niveles con sus propias cintas express y cuerdas dinámicas.','01:30:00','2019-05-12',5,20,'EsportVerd Castellón','C/ Francia, 12006 Castellón (España) Polígono Industrial Acceso Sur, nave 8C','17:00:00','admin',15.00);
INSERT INTO ImagenPromocional VALUES(8, 'escalada.jpeg');


INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (9,'abierta','Paddle Surf','¡Aprovecha tu visita a Oropesa del Mar y prueba un deporte acuático que cada vez tiene más adeptos! Está claro que hablamos del paddle surf.','01:00:00','2019-05-12',5,20,'Oropesa del Mar','Puerto Deportivo Oropesa del Mar, S/N, 12594 Oropesa del Mar, Castellón','12:00:00','admin',30.00);
INSERT INTO ImagenPromocional VALUES(9, 'paddleSurf.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (10,'abierta','Paddle Surf','¡Aprovecha tu visita a Oropesa del Mar y prueba un deporte acuático que cada vez tiene más adeptos! Está claro que hablamos del paddle surf.','01:00:00','2019-05-12',5,20,'Oropesa del Mar','Puerto Deportivo Oropesa del Mar, S/N, 12594 Oropesa del Mar, Castellón','12:00:00','admin',30.00);
INSERT INTO ImagenPromocional VALUES(10, 'paddleSurf.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (11,'abierta','Paddle Surf','¡Aprovecha tu visita a Oropesa del Mar y prueba un deporte acuático que cada vez tiene más adeptos! Está claro que hablamos del paddle surf.','01:00:00','2019-05-12',5,20,'Oropesa del Mar','Puerto Deportivo Oropesa del Mar, S/N, 12594 Oropesa del Mar, Castellón','12:00:00','admin',30.00);
INSERT INTO ImagenPromocional VALUES(11, 'paddleSurf.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (12,'abierta','Paddle Surf','¡Aprovecha tu visita a Oropesa del Mar y prueba un deporte acuático que cada vez tiene más adeptos! Está claro que hablamos del paddle surf.','01:00:00','2019-05-12',5,20,'Oropesa del Mar','Puerto Deportivo Oropesa del Mar, S/N, 12594 Oropesa del Mar, Castellón','12:00:00','admin',30.00);
INSERT INTO ImagenPromocional VALUES(12, 'paddleSurf.jpg');
 */

INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (1,'abierta','Ruta antenes Vilafamés Novato','Castelló,font de Codina, antenes Vilafamés, baixada a la Pobla per Itaca, esmorzar,pujada al Bartolo','02:00:00','2019-05-12',5,20,'Puebla Tornesa','Castellón de la Plana','09:00:00','admin',20.00);
INSERT INTO ImagenPromocional VALUES(1, 'antenesVilafames.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (5,'abierta','Rocódromo y búlder Novato','En el rocódromo tienes la facilidad de estar instaladas las vías en diferentes niveles con sus propias cintas express y cuerdas dinámicas.','01:30:00','2019-05-12',5,20,'EsportVerd Castellón','C/ Francia, 12006 Castellón (España) Polígono Industrial Acceso Sur, nave 8C','17:00:00','admin',15.00);
INSERT INTO ImagenPromocional VALUES(2, 'escalada.jpeg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (9,'abierta','Paddle Surf Novato','¡Aprovecha tu visita a Oropesa del Mar y prueba un deporte acuático que cada vez tiene más adeptos! Está claro que hablamos del paddle surf.','01:00:00','2019-05-12',5,20,'Oropesa del Mar','Puerto Deportivo Oropesa del Mar, S/N, 12594 Oropesa del Mar, Castellón','12:00:00','admin',30.00);
INSERT INTO ImagenPromocional VALUES(3, 'paddleSurf.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (2,'abierta','Ruta antenes Vilafamés Dominguero','Castelló,font de Codina, antenes Vilafamés, baixada a la Pobla per Itaca, esmorzar,pujada al Bartolo','02:00:00','2019-05-12',5,20,'Puebla Tornesa','Castellón de la Plana','09:00:00','admin',20.00);
INSERT INTO ImagenPromocional VALUES(4, 'antenesVilafames.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (6,'abierta','Rocódromo y búlder Dominguero','En el rocódromo tienes la facilidad de estar instaladas las vías en diferentes niveles con sus propias cintas express y cuerdas dinámicas.','01:30:00','2019-05-12',5,20,'EsportVerd Castellón','C/ Francia, 12006 Castellón (España) Polígono Industrial Acceso Sur, nave 8C','17:00:00','admin',15.00);
INSERT INTO ImagenPromocional VALUES(5, 'escalada.jpeg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (10,'abierta','Paddle Surf Dominguero','¡Aprovecha tu visita a Oropesa del Mar y prueba un deporte acuático que cada vez tiene más adeptos! Está claro que hablamos del paddle surf.','01:00:00','2019-05-12',5,20,'Oropesa del Mar','Puerto Deportivo Oropesa del Mar, S/N, 12594 Oropesa del Mar, Castellón','12:00:00','admin',30.00);
INSERT INTO ImagenPromocional VALUES(6, 'paddleSurf.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (3,'abierta','Ruta antenes Vilafamés Respetable','Castelló,font de Codina, antenes Vilafamés, baixada a la Pobla per Itaca, esmorzar,pujada al Bartolo','02:00:00','2019-05-12',5,20,'Puebla Tornesa','Castellón de la Plana','09:00:00','admin',20.00);
INSERT INTO ImagenPromocional VALUES(7, 'antenesVilafames.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (7,'abierta','Rocódromo y búlder Respetable','En el rocódromo tienes la facilidad de estar instaladas las vías en diferentes niveles con sus propias cintas express y cuerdas dinámicas.','01:30:00','2019-05-12',5,20,'EsportVerd Castellón','C/ Francia, 12006 Castellón (España) Polígono Industrial Acceso Sur, nave 8C','17:00:00','admin',15.00);
INSERT INTO ImagenPromocional VALUES(8, 'escalada.jpeg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (11,'abierta','Paddle Surf Respetable','¡Aprovecha tu visita a Oropesa del Mar y prueba un deporte acuático que cada vez tiene más adeptos! Está claro que hablamos del paddle surf.','01:00:00','2019-05-12',5,20,'Oropesa del Mar','Puerto Deportivo Oropesa del Mar, S/N, 12594 Oropesa del Mar, Castellón','12:00:00','admin',30.00);
INSERT INTO ImagenPromocional VALUES(9, 'paddleSurf.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (4,'abierta','Ruta antenes Vilafamés Challenger','Castelló,font de Codina, antenes Vilafamés, baixada a la Pobla per Itaca, esmorzar,pujada al Bartolo','02:00:00','2019-05-12',5,20,'Puebla Tornesa','Castellón de la Plana','09:00:00','admin',20.00);
INSERT INTO ImagenPromocional VALUES(10, 'antenesVilafames.jpg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (8,'abierta','Rocódromo y búlder Challenger','En el rocódromo tienes la facilidad de estar instaladas las vías en diferentes niveles con sus propias cintas express y cuerdas dinámicas.','01:30:00','2019-05-12',5,20,'EsportVerd Castellón','C/ Francia, 12006 Castellón (España) Polígono Industrial Acceso Sur, nave 8C','17:00:00','admin',15.00);
INSERT INTO ImagenPromocional VALUES(11, 'escalada.jpeg');
INSERT INTO actividad (idTipoActividad, estado ,nombre ,descripcion ,duracion ,fecha ,minAsistentes ,maxAsistentes, lugar, puntoDeEncuentro ,horaDeEncuentro ,monitor, precioBruto) VALUES (12,'abierta','Paddle Surf Challenger','¡Aprovecha tu visita a Oropesa del Mar y prueba un deporte acuático que cada vez tiene más adeptos! Está claro que hablamos del paddle surf.','01:00:00','2019-05-12',5,20,'Oropesa del Mar','Puerto Deportivo Oropesa del Mar, S/N, 12594 Oropesa del Mar, Castellón','12:00:00','admin',30.00);
INSERT INTO ImagenPromocional VALUES(12, 'paddleSurf.jpg');

INSERT INTO oferta VALUES('Niños -75%', 'Niños -75%', 0.75, 'menor18');
INSERT INTO oferta VALUES('Adultos -50%', 'Adultos -50%', 0.50, 'entre18-50');
INSERT INTO oferta VALUES('Jubilados -50%', 'Jubilados -50%', 0.50, 'mayor50');
INSERT INTO oferta VALUES('Grupo -25%', 'Grupo -25%', 0.25, 'grupo');
INSERT INTO oferta VALUES('Temporada Baja -35%', 'Temporada Baja -35%', 0.35, 'temporadabaja');
INSERT INTO oferta VALUES('A mitad de precio TODOS!', 'A mitad de precio TODOS!', 0.5, 'total');