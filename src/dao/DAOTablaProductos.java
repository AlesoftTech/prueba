package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.FechaNombreCuenta;
import vos.Producto;

public class DAOTablaProductos {

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOTablaProductos
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaProductos() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexion que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	public ArrayList<Producto> darProductos() throws SQLException, Exception {
		ArrayList<Producto> productoz = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String desc=rs.getString("DESCRIPCION");
			String descT=rs.getString("DESCTRADUCIDA");
			Integer tie=rs.getInt("TIEMPOPREP");
			String tip=rs.getString("TIPO");
			String cat=rs.getString("CATEGORIA");
			productoz.add(new Producto(name, desc, descT, tie, tip, cat));
		}
		return productoz;
	}


	public Producto buscarProductoPorName(String name) throws SQLException, Exception 
	{
		Producto productoz=null;

		String sql = "SELECT * FROM PRODUCTO WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String name1 = rs.getString("NOMBRE");
			String desc=rs.getString("DESCRIPCION");
			String descT=rs.getString("DESCTRADUCIDA");
			Integer tie=rs.getInt("TIEMPOPREP");
			String tip=rs.getString("TIPO");
			String cat=rs.getString("CATEGORIA");
			productoz=new Producto(name1, desc, descT, tie, tip, cat);
		}

		return productoz;
	}

	public void addProducto(Producto prod) throws SQLException, Exception {

		String sql = "INSERT INTO PRODUCTO VALUES ('";
		sql += prod.getNombre() + "','";
		sql += prod.getDescripcion()+ "','";
		sql += prod.getDescTraducida()+ "',";
		sql += prod.getTiempoPrep()+ ",'";
		sql += prod.getTipo()+ "','";
		sql += prod.getCategoria() +"')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateProducto(Producto prod) throws SQLException, Exception {

		String sql = "UPDATE PRODUCTO SET DESCRIPCION='";
		sql += prod.getDescripcion()+ "', DESCTRADUCIDA='";
		sql += prod.getDescTraducida()+ "',TIEMPOPREP=";
		sql += prod.getTiempoPrep()+ ", TIPO='";
		sql += prod.getTipo()+ "', CATEGORIA='";
		sql += prod.getCategoria()+"'";
		sql += " WHERE NOMBRE = " + prod.getNombre()+"'";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void deleteProducto(Producto prod) throws SQLException, Exception {

		String sql = "DELETE FROM Producto";
		sql += " WHERE NOMBRE = " + prod.getNombre()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public ArrayList<Producto> consultarProductosQueOfrecen(String criterio) throws SQLException, Exception {

		DAOTablaRestauranteProducto daoRest = new DAOTablaRestauranteProducto();
		ArrayList<Producto> rta = new ArrayList<Producto>();

		String sql = "SELECT NOMPRODUCTO FROM PRODUCTO, RESTAURANTEPRODUCTO";
		sql += "ORDER BY" + criterio;
		sql += "GROUP BY" + criterio;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String desc=rs.getString("DESCRIPCION");
			String descT=rs.getString("DESCTRADUCIDA");
			Integer tie=rs.getInt("TIEMPOPREP");
			String tip=rs.getString("TIPO");
			String cat=rs.getString("CATEGORIA");
			rta.add(new Producto(name, desc, descT, tie, tip, cat));
		}	
		return rta;
	}
	
	public ArrayList<FechaNombreCuenta> darProductosMasVendidos() throws SQLException
	{
		ArrayList<FechaNombreCuenta> fnc=new ArrayList();
		
		String sql="SELECT FECHA,NOMBRE,COUNT(*) AS CUENTA\n" + 
				"FROM PRODUCTO JOIN PRODUCTOPEDIDO ON PRODUCTO.NOMBRE=PRODUCTOPEDIDO.NOMPRODUCTO JOIN PEDIDO ON PEDIDO.IDPEDIDO=PRODUCTOPEDIDO.IDPEDIDO\n" + 
				"GROUP BY FECHA,NOMBRE\n" + 
				"ORDER BY CUENTA";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next())
		{
			Date f=rs.getDate("FECHA");
			String n=rs.getString("NOMBRE");
			int c=rs.getInt("CUENTA");
			fnc.add(new FechaNombreCuenta(f, n, c));
		}
		
		return fnc;
	}
	
public ArrayList<Producto> productoPorCategoria(String categoria) throws SQLException, Exception {
		
		ArrayList<Producto> productos = new ArrayList<Producto>();
		
		String sql = "SELECT * FROM PRODUCTO";
		sql += " WHERE CATEGORIA = '"+ categoria+"'";
		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String desc=rs.getString("DESCRIPCION");
			String descT=rs.getString("DESCTRADUCIDA");
			Integer tie=rs.getInt("TIEMPOPREP");
			String tip=rs.getString("TIPO");
			String cat=rs.getString("CATEGORIA");
			productos.add(new Producto(name, desc, descT, tie, tip, cat));
		}
		return productos;
		
	}



}

