DELETE FROM RESTAURANTEPRODUCTO;
DELETE FROM PRODUCTOINGREDIENTE;
DELETE FROM PRODUCTOPEDIDO;
DELETE FROM PRODUCTOMENU;
DELETE FROM PEDIDO;
DELETE FROM PREFERENCIA;
DELETE FROM INGREDIENTE;
DELETE FROM PRODUCTO;
DELETE FROM RESTAURANTE;
DELETE FROM USUARIO;
DELETE FROM ZONA;

INSERT INTO USUARIO VALUES('USUARIO1','ID1','CORREO1','UsuarioCliente');
INSERT INTO USUARIO VALUES('USUARIO2','ID2','CORREO2','UsuarioAdmin');
INSERT INTO USUARIO VALUES('USUARIO3','ID3','CORREO3','UsuarioRestaurante');
INSERT INTO USUARIO VALUES('USUARIO4','ID4','CORREO4','UsuarioCliente');

INSERT INTO ZONA VALUES (1,25,0,0,1,1,1,1);
INSERT INTO ZONA VALUES (2,50,1,1,1,1,1,1);
INSERT INTO ZONA VALUES (3,10,0,0,0,0,0,0);

INSERT INTO PRODUCTO VALUES('Hamburguesa','Pan, carne de res, verduras','Bread, meat, veggies',20,'Comida rapida','Plato Fuerte');
INSERT INTO PRODUCTO VALUES('Jugo de mora','Jugo de moras frescas','Juice delicious juice',10,'Natural','Bebida');
INSERT INTO PRODUCTO VALUES('Nestea','Te de limon','Lemon tea',2,'Natural','Bebida');
INSERT INTO PRODUCTO VALUES('Empanadas','Empanaditas de carne y pollo','Contain chicken or beef',15,'Comida rapida','Entrada');
INSERT INTO PRODUCTO VALUES('Leche asada','Postre de leche','Delicious dessert',15,'Casero','Postre');
INSERT INTO PRODUCTO VALUES('Hogao','Salsa tradicional colombiana','Traditional colombian sauce',7,'Colombiano','Acompanamiento');
INSERT INTO PRODUCTO VALUES('MenuFantastico','Menu de hamburguesa y jugo de mora','Burger and berry juice',25,'Comida rapida','Menu');

INSERT INTO INGREDIENTE  values ('Pan','Flour - Corn, Fine', 'Frangelico');
INSERT INTO INGREDIENTE  values ('Leche','Chocolate - Semi Sweet, Calets', 'Champagne - Brights, Dry');
INSERT INTO INGREDIENTE  values ('Tomate','Wine - Cousino Macul Antiguas', 'Rice - Wild');
INSERT INTO INGREDIENTE  values ('Harina','Table Cloth 54x72 Colour', 'Mustard - Seed');
INSERT INTO INGREDIENTE  values ('Mora','Roe - Lump Fish, Red', 'Rum - Coconut, Malibu');
INSERT INTO INGREDIENTE  values ('Pollo','Veal - Striploin', 'Yeast Dry - Fermipan');

insert into RESTAURANTE (NOMBRE, REPRESENTANTE, TIPOCOMIDA, PAGINAWEB) values ('Subway', 'Abby Tetford', 'Comida rapida', 'me.flavors.Job');
insert into RESTAURANTE (NOMBRE, REPRESENTANTE, TIPOCOMIDA, PAGINAWEB) values ('Mafia', 'Abbe Morrott', 'Natural', 'com.feedburner.Ventosanzap');
insert into RESTAURANTE (NOMBRE, REPRESENTANTE, TIPOCOMIDA, PAGINAWEB) values ('Oishi', 'Kassi Beahan', 'Asiatico', 'com.stumbleupon.Prodder');
insert into RESTAURANTE (NOMBRE, REPRESENTANTE, TIPOCOMIDA, PAGINAWEB) values ('Papelon', 'Indira Volage', 'Colombiano', 'us.imageshack.Tres-Zap');

INSERT INTO RESTAURANTEPRODUCTO VALUES ('Hamburguesa','Mafia',10000,22000,5);
INSERT INTO RESTAURANTEPRODUCTO VALUES ('Jugo de mora','Oishi',5000,7000,20);
INSERT INTO RESTAURANTEPRODUCTO VALUES ('Leche asada','Papelon',2000,8000,10);
INSERT INTO RESTAURANTEPRODUCTO VALUES ('Leche asada','Mafia',1500,5000,15);
INSERT INTO RESTAURANTEPRODUCTO VALUES ('Empanadas','Subway',3000,10000,50);

INSERT INTO PRODUCTOINGREDIENTE VALUES ('Hamburguesa','Pan');
INSERT INTO PRODUCTOINGREDIENTE VALUES ('Empanadas','Harina');
INSERT INTO PRODUCTOINGREDIENTE VALUES ('Hamburguesa','Tomate');
INSERT INTO PRODUCTOINGREDIENTE VALUES ('Hogao','Tomate');
INSERT INTO PRODUCTOINGREDIENTE VALUES ('Leche asada','Leche');
INSERT INTO PRODUCTOINGREDIENTE VALUES ('Jugo de mora','Mora');

INSERT INTO PEDIDO (FECHA,IDPEDIDO,IDZONA,PRECIOTOTAL) VALUES (CURRENT_TIMESTAMP,1,2,0);
INSERT INTO PEDIDO (FECHA,IDPEDIDO,IDZONA,PRECIOTOTAL) VALUES (CURRENT_TIMESTAMP,2,3,0);
INSERT INTO PEDIDO (FECHA,IDPEDIDO,IDZONA,PRECIOTOTAL) VALUES (CURRENT_TIMESTAMP,3,1,0);

INSERT INTO PRODUCTOPEDIDO VALUES(1,'Hamburguesa','Mafia');
INSERT INTO PRODUCTOPEDIDO VALUES(2,'Leche asada','Papelon');
INSERT INTO PRODUCTOPEDIDO VALUES(3,'Empanadas','Subway');
INSERT INTO PRODUCTOPEDIDO VALUES(1,'Jugo de mora','Oishi');

INSERT INTO PREFERENCIA VALUES('ID1','Comida rapida');
INSERT INTO PREFERENCIA VALUES('ID1','Colombiano');
INSERT INTO PREFERENCIA VALUES('ID4','Natural');

INSERT INTO PRODUCTOMENU VALUES('MenuFantastico','Hamburguesa');
INSERT INTO PRODUCTOMENU VALUES('MenuFantastico','Jugo de mora');







