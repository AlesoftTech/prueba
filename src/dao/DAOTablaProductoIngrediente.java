package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ProductoIngrediente;
import vos.RestauranteProducto;

public class DAOTablaProductoIngrediente {

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
	public DAOTablaProductoIngrediente
	() {
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
	
	public ArrayList<ProductoIngrediente> darIngredientesPorProducto(String nom) throws Exception, SQLException
	{
		ArrayList<ProductoIngrediente> prodIng = new ArrayList<ProductoIngrediente>();

		String sql = "SELECT * FROM PRODUCTOINGREDIENTE";
		sql += " WHERE NOMPRODUCTO = '" + nom +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomP=rs.getString("NOMPRODUCTO");
			String nomI=rs.getString("NOMINGREDIENTE");
			prodIng.add(new ProductoIngrediente(nomP, nomI));
		}
		return prodIng;
	}
	
	public void addProductoIngrediente(ProductoIngrediente rest) throws SQLException, Exception {

		String sql = "INSERT INTO ProductoIngrediente VALUES ('";
		sql += rest.getNomProducto() + "','";
		sql += rest.getNomIngrediente() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void deleteProductoIngrediente(ProductoIngrediente rest) throws SQLException, Exception {

		String sql = "DELETE FROM ProductoIngrediente";
		sql += " WHERE NOMINGREDIENTE = '" + rest.getNomIngrediente()+"'";
		sql += " AND NOMPRODUCTO = '" + rest.getNomProducto()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


}

