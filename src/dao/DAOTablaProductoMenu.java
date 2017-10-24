package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Producto;
import vos.ProductoMenu;

public class DAOTablaProductoMenu {
	
	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOTablaIngredientes
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaProductoMenu() {
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
	
	public ArrayList<ProductoMenu> darProductosPorMenu(String nomMenu) throws Exception, SQLException
	{
		ArrayList<ProductoMenu> proMens = new ArrayList<ProductoMenu>();

		String sql = "SELECT * FROM PRODUCTOMENU";
		sql += " WHERE NOMMENU = '" + nomMenu +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomM=rs.getString("NOMMENU");
			String nomP=rs.getString("NOMPRODUCTO");
			proMens.add(new ProductoMenu(nomM,nomP ));
		}
		return proMens;
	}
	
	public ArrayList<ProductoMenu> darMenusPorProducto(String nomPr) throws Exception, SQLException
	{
		ArrayList<ProductoMenu> proMens = new ArrayList<ProductoMenu>();

		String sql = "SELECT * FROM PRODUCTOMENU";
		sql += " WHERE NOMPRODUCTO = '" + nomPr +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomM=rs.getString("NOMMENU");
			String nomP=rs.getString("NOMPRODUCTO");
			proMens.add(new ProductoMenu(nomM,nomP ));
		}
		return proMens;
	}
	
	
	
	public void addProductoMenu(ProductoMenu rest) throws SQLException, Exception {

		String sql = "INSERT INTO PRODUCTOMENU VALUES ('";
		sql += rest.getNomMenu() +"','";
		sql += rest.getNomProducto() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	

	public void deleteProductoMenu(ProductoMenu rest) throws SQLException, Exception {

		String sql = "DELETE FROM PRODUCTOMENU";
		sql += " WHERE NOMPRODUCTO = '" + rest.getNomProducto()+"'";
		sql += " AND NOMMENU = " + rest.getNomMenu();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public ArrayList<String> darProductosMasOfrecidos() throws Exception, SQLException
	{
		ArrayList<String> productos = new ArrayList<String>();
		
		String sql = "SELECT NOMPRODUCTO, COUNT(*)AS CUENTA\r\n" + 
				"FROM PRODUCTOMENU\r\n" + 
				"GROUP BY NOMPRODUCTO\r\n" + 
				"ORDER BY CUENTA DESC";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		DAOTablaProductos daoProducto = new DAOTablaProductos();
				
		while (rs.next()) {
	        
			String nomP = rs.getString("NOMPRODUCTO");
			productos.add(nomP);
		}
		
		return productos;
		
	}


}
