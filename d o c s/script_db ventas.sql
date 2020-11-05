CREATE TABLE IF NOT EXISTS clientes (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	nombres		TEXT	NOT NULL,
    info_adic	TEXT	NULL
);

CREATE TABLE IF NOT EXISTS productos (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	nombre		TEXT	NOT NULL,
    detalle		TEXT	NULL,
    precio		REAL	NULL,
    fecha_ven   datetime NULL,
    date_created datetime default current_timestamp
);

CREATE TABLE IF NOT EXISTS ventas (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	fecha		datetime	NOT NULL,
    clie_id		INTEGER		NOT	NULL,
	clie_nom	TEXT			NULL,
	
	activo		INTEGER		default 1,
	FOREIGN KEY (clie_id) REFERENCES clientes (id) 
	ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS venta_lineas (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	venta_id	INTEGER		NOT	NULL,
	prod_id		INTEGER		NOT	NULL,
	descripcion	TEXT			NULL,
	cant		REAL		NOT	NULL,
	precio		REAL	NOT NULL,
	descto		REAL	 NULL,
	subtotal	REAL	 NULL, 
   
	activo		INTEGER		default 1
	,FOREIGN KEY (venta_id) REFERENCES ventas (id) 
	ON UPDATE RESTRICT ON DELETE RESTRICT
	,FOREIGN KEY (prod_id) REFERENCES productos (id) 
	ON UPDATE RESTRICT ON DELETE RESTRICT
);
