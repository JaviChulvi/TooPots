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

CREATE TABLE Actividad (
    id SERIAL NOT NULL,
    idTipoActividad INTEGER NOT NULL,
    estado VARCHAR(15) NOT NULL,
    nombre VARCHAR(40) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    duracion TIME NOT NULL,
    fecha DATE NOT NULL,
    minAsistentes INTEGER NOT NULL,
    maxAsistentes INTEGER NOT NULL,
    lugar VARCHAR(40) NOT NULL,
    puntoDeEncuentro VARCHAR(30) NOT NULL,
    horaDeEncuentro TIME NOT NULL,
    monitor VARCHAR(10) NOT NULL,
    precio FLOAT NOT NULL,
    CONSTRAINT cp_actividad PRIMARY KEY (id),
    CONSTRAINT ca_actividad FOREIGN KEY (idTipoActividad) REFERENCES TipoActividad(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ca_monitor FOREIGN KEY (monitor) REFERENCES Monitor(dni) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT ri_actividad_duracion CHECK (duracion>'00:00'),
    CONSTRAINT ri_actividad_estado CHECK (estado='abierta' OR estado='cerrada' OR estado='completa' OR estado='cancelada')
);
    

    
CREATE TABLE Acreditacion (
	certificado VARCHAR(50) NOT NULL,
	dniMonitor VARCHAR(10) NOT NULL,
	estado VARCHAR(15),
	CONSTRAINT cp_certificado PRIMARY KEY (certificado),
	CONSTRAINT ca_monitor FOREIGN KEY (dniMonitor) REFERENCES Monitor(dni) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ri_acreditacion_estado CHECK (estado='aceptada' OR estado='rechazada')
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
    
CREATE TABLE Oferta (
	idActividad INTEGER NOT NULL,
	dniMonitor VARCHAR(10) NOT NULL,
	CONSTRAINT cp_oferta PRIMARY KEY (idActividad, dniMonitor),
	CONSTRAINT ca_actividad FOREIGN KEY (idActividad) REFERENCES Actividad(id) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ca_monitor FOREIGN KEY (dniMonitor) REFERENCES Monitor(dni) ON DELETE RESTRICT ON UPDATE CASCADE
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
	numAdultos INTEGER NOT NULL,
	numMenores INTEGER NOT NULL,
	precioPorPersona FLOAT NOT NULL,
	CONSTRAINT cp_reserva PRIMARY KEY (idActividad, dniCliente),
	CONSTRAINT ca_actividad FOREIGN KEY (idActividad) REFERENCES Actividad(id) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ca_cliente FOREIGN KEY (dniCliente) REFERENCES Cliente(dni) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ri_reserva_estadoPago CHECK (estadoPago='pendiente' OR estadoPago='pagado')
);

CREATE TABLE Entrada (
    idActividad INTEGER NOT NULL,
    tipo VARCHAR(15) NOT NULL,
    precio FLOAT NOT NULL,
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

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO al364498;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO al361899;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO al315614;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO al341816;
/*ALTER ROLE al364498 WITH SUPERUSER;
ALTER ROLE al361899 WITH SUPERUSER;
ALTER ROLE al315614 WITH SUPERUSER;
ALTER ROLE al341816 WITH SUPERUSER;
*/