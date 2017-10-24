package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ProductoPedido;

public class DAOTablaProductoPedido
{
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
	public DAOTablaProductoPedido() {
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
	
	public ArrayList<ProductoPedido> darRestauranteProductosPorIdPedido(Long idP) throws Exception, SQLException
	{
		ArrayList<ProductoPedido> prodPed = new ArrayList<ProductoPedido>();

		String sql = "SELECT * FROM PRODUCTOPEDIDO";
		sql += " WHERE IDPEDIDO = " + idP;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomP=rs.getString("NOMPRODUCTO");
			String nomR=rs.getString("NOMRESTAURANTE");
			prodPed.add(new ProductoPedido(idP, nomP,nomR));
		}
		return prodPed;
	}	
	
	
	public void addProductoPedido(ProductoPedido rest) throws SQLException, Exception {		
		
		
		String sql = "INSERT INTO PRODUCTOPEDIDO VALUES (";
		sql += rest.getIdPedido() + ",'";
		sql += rest.getNomProducto() + "','";
		sql += rest.getNomRestaurante() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	
	public void deleteProductoPedido(ProductoPedido rest) throws SQLException, Exception {

		String sql = "DELETE FROM PRODUCTOPEDIDO";
		sql += " WHERE NOMRESTAURANTE = '" + rest.getNomRestaurante()+"'";
		sql += " AND NOMPRODUCTO = '" + rest.getNomProducto()+"'";
		sql += " AND IDPEDIDO = "+rest.getIdPedido();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}



}
