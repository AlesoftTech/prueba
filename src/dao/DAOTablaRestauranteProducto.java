package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.RestauranteProducto;
import vos.Usuario;

public class DAOTablaRestauranteProducto {
	
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
	public DAOTablaRestauranteProducto() {
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
	
	public ArrayList<RestauranteProducto> darProductosPorRestaurante(String nom) throws Exception, SQLException
	{
		ArrayList<RestauranteProducto> Usuarioz = new ArrayList<RestauranteProducto>();

		String sql = "SELECT * FROM RESTAURANTEPRODUCTO";
		sql += " WHERE NOMRESTAURANTE = '" + nom +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomP=rs.getString("NOMPRODUCTO");
			String nomR=rs.getString("NOMRESTAURANTE");
			double costo=rs.getDouble("COSTOPROD");
			double pre=rs.getDouble("PRECIO");
			int un=rs.getInt("UNIDADESDISPONIBLES");
			Usuarioz.add(new RestauranteProducto(nomP, nomR, costo, pre,un));
		}
		return Usuarioz;
	}
	
	public RestauranteProducto darRestauranteProducto(String nomRe, String nomPr) throws Exception, SQLException
	{
		RestauranteProducto resPro = null;

		String sql = "SELECT * FROM RESTAURANTEPRODUCTO";
		sql += " WHERE NOMRESTAURANTE = '" + nomRe+"'";
		sql += " AND NOMPRODUCTO = '" + nomPr+"'";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomP=rs.getString("NOMPRODUCTO");
			String nomR=rs.getString("NOMRESTAURANTE");
			double costo=rs.getDouble("COSTOPROD");
			double pre=rs.getDouble("PRECIO");
			int un=rs.getInt("UNIDADESDISPONIBLES");
			resPro=new RestauranteProducto(nomP, nomR, costo, pre,un);
		}
		return resPro;
	}
	
	public ArrayList<RestauranteProducto> darRestaurantesPorProducto(String nom) throws Exception, SQLException
	{
		ArrayList<RestauranteProducto> Usuarioz = new ArrayList<RestauranteProducto>();

		String sql = "SELECT * FROM RESTAURANTEPRODUCTO";
		sql += " WHERE NOMPRODUCTO = '" + nom +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomP=rs.getString("NOMPRODUCTO");
			String nomR=rs.getString("NOMRESTAURANTE");
			double costo=rs.getDouble("COSTOPROD");
			double pre=rs.getDouble("PRECIO");
			int un=rs.getInt("UNIDADESDISPONIBLES");
			Usuarioz.add(new RestauranteProducto(nomP, nomR, costo, pre,un));
		}
		return Usuarioz;
	}
	
	
	
	public void addRestauranteProducto(RestauranteProducto rest) throws SQLException, Exception {

		String sql = "INSERT INTO RESTAURANTEPRODUCTO VALUES ('";
		sql += rest.getNomProducto() + "','";
		sql += rest.getNomRestaurante() + "',";
		sql += rest.getCostoProd() + ",";
		sql += rest.getPrecio() + ",";
		sql += rest.getUnidadesDisponibles() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void updateRestauranteProducto(RestauranteProducto rest) throws SQLException, Exception {
	
		String sql = "UPDATE RESTAURANTEPRODUCTO SET COSTOPROD=";
		sql += rest.getCostoProd()+", PRECIO=";
		sql += rest.getPrecio()+", UNIDADESDISPONIBLES=";
		sql += rest.getUnidadesDisponibles();
		sql += "WHERE NOMPRODUCTO = '"+rest.getNomProducto()+"' AND NOMRESTAURANTE = '"+rest.getNomRestaurante()+"'";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void deleteRestauranteProducto(RestauranteProducto rest) throws SQLException, Exception {

		String sql = "DELETE FROM RestauranteProducto";
		sql += " WHERE NOMRESTAURANTE = '" + rest.getNomRestaurante()+"'";
		sql += " AND NOMPRODUCTO = '" + rest.getNomProducto()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	


}
