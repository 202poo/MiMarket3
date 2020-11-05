SET FOREIGN_KEY_CHECKS=0;



CREATE TABLE Caja
(
	fecha Date,
	es_ingreso int,
	glosa String,
	monto double,
	cajaID Integer NOT NULL,
	clienteID Integer,
	proveedorID Integer,
	PRIMARY KEY (cajaID),
	KEY (clienteID),
	KEY (proveedorID)
) 
;


CREATE TABLE Cliente
(
	nombres String,
	infoadic String,
	clienteID Integer NOT NULL,
	PRIMARY KEY (clienteID)
) 
;


CREATE TABLE LineaVenta
(
	descripcion String,
	cant double,
	precio_un double,
	total_dscto double,
	total double,
	lineaVentaID Integer NOT NULL,
	productoID Integer,
	ventaID Integer NOT NULL,
	PRIMARY KEY (lineaVentaID),
	KEY (productoID),
	KEY (ventaID)
) 
;


CREATE TABLE Producto
(
	nombre String,
	detalle String,
	precio double,
	fecha_venc Date,
	productoID Integer NOT NULL,
	PRIMARY KEY (productoID)
) 
;


CREATE TABLE Proveedor
(
	proveedorID Integer NOT NULL,
	PRIMARY KEY (proveedorID)
) 
;


CREATE TABLE TIPO_COMPROBANTE
(
	BOLETA int DEFAULT 1,
	FACTURA int DEFAULT 2,
	TICKET int DEFAULT 0,
	tIPO_COMPROBANTEID Integer NOT NULL,
	PRIMARY KEY (tIPO_COMPROBANTEID)
) 
;


CREATE TABLE Venta
(
	clie_nom String,
	activo int,
	fecha Date,
	ventaID Integer NOT NULL,
	clie_id Integer,
	Xtipo_comp Integer NOT NULL,
	PRIMARY KEY (ventaID),
	KEY (clie_id),
	KEY (Xtipo_comp)
) 
;



SET FOREIGN_KEY_CHECKS=1;


ALTER TABLE Caja ADD CONSTRAINT FK_Caja_Cliente 
	FOREIGN KEY (clienteID) REFERENCES Cliente (clienteID)
;

ALTER TABLE Caja ADD CONSTRAINT FK_Caja_Proveedor 
	FOREIGN KEY (proveedorID) REFERENCES Proveedor (proveedorID)
;

ALTER TABLE LineaVenta ADD CONSTRAINT FK_LineaVenta_Producto 
	FOREIGN KEY (productoID) REFERENCES Producto (productoID)
;

ALTER TABLE LineaVenta ADD CONSTRAINT FK_LineaVenta_Venta 
	FOREIGN KEY (ventaID) REFERENCES Venta (ventaID)
;

ALTER TABLE Venta ADD CONSTRAINT FK_clie_id 
	FOREIGN KEY (clie_id) REFERENCES Cliente (clienteID)
;

ALTER TABLE Venta ADD CONSTRAINT FK_Xtipo_comp 
	FOREIGN KEY (Xtipo_comp) REFERENCES TIPO_COMPROBANTE (tIPO_COMPROBANTEID)
;
