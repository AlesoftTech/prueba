package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Restaurante;	

public class DAOTablaRestaurantes {

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOTablaRestaurantes
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaRestaurantes() {
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


	public ArrayList<Restaurante> darRestaurantes() throws SQLException, Exception {
		ArrayList<Restaurante> restaurantez = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String rep=rs.getString("REPRESENTANTE");
			String tipo=rs.getString("TIPOCOMIDA");
			String pag=rs.getString("PAGINAWEB");
			restaurantez.add(new Restaurante(name, rep,tipo,pag));
		}
		return restaurantez;
	}

	public Restaurante buscarRestaurantePorName(String name) throws SQLException, Exception 
	{
		Restaurante resto = null;

		String sql = "SELECT * FROM RESTAURANTE WHERE NOMBRE = '" + name+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {

			String name1 = rs.getString("NOMBRE");
			String rep=rs.getString("REPRESENTANTE");
			String tipo=rs.getString("TIPOCOMIDA");
			String pag=rs.getString("PAGINAWEB");
			resto=new Restaurante(name1, rep,tipo,pag);
		}

		return resto;
	}


		public void addRestaurante(Restaurante rest) throws SQLException, Exception {

			String sql = "INSERT INTO RESTAURANTE VALUES ('";
			sql += rest.getNombre() + "','";
			sql += rest.getRepresentante() + "','";
			sql += rest.getTipoComida() + "','";
			sql += rest.getPaginaWeb() + "')";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();

		}

		public void updateRestaurante(Restaurante rest) throws SQLException, Exception {

			String sql = "UPDATE RESTAURANTE SET REPRESENTANTE='";
			sql += rest.getRepresentante() + "', TIPOCOMIDA='";
			sql += rest.getTipoComida() + "', PAGINAWEB='";
			sql += rest.getPaginaWeb() + "'";
			sql += " WHERE NOMBRE = '" + rest.getNombre()+"'";


			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}

		public void deleteRestaurante(String nom) throws SQLException, Exception {

			String sql = "DELETE FROM RESTAURANTE";
			sql += " WHERE NOMBRE = '" + nom +"'";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}



	}
