package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingrediente;


public class DAOTablaIngredientes {
	
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
	public DAOTablaIngredientes() {
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


	public ArrayList<Ingrediente> darIngredientes() throws SQLException, Exception {
		ArrayList<Ingrediente> Ingredientez = new ArrayList<Ingrediente>();

		String sql = "SELECT * FROM INGREDIENTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String desc=rs.getString("DESCRIPCION");
			String descT=rs.getString("DESCTRADUCIDA");
			Ingredientez.add(new Ingrediente(name, desc, descT));
		}
		return Ingredientez;
	}

	

	public Ingrediente buscarIngredientePorName(String name) throws SQLException, Exception 
	{
		Ingrediente ingredientez=null;

		String sql = "SELECT * FROM INGREDIENTE WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String name1 = rs.getString("NOMBRE");
			String desc=rs.getString("DESCRIPCION");
			String descT=rs.getString("DESCTRADUCIDA");
			ingredientez=new Ingrediente(name1, desc, descT);
		}

		return ingredientez;
	}

	public void addIngrediente(Ingrediente ing) throws SQLException, Exception {

		String sql = "INSERT INTO INGREDIENTE VALUES ('";
		sql += ing.getNombre() + "','";
		sql += ing.getDescripcion()+ "','";
		sql += ing.getDescTraducida() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateIngrediente(Ingrediente ing) throws SQLException, Exception {

		String sql = "UPDATE INGREDIENTE SET DESCRIPCION='";
		sql += ing.getDescripcion()+ "', DESCTRADUCIDA= '";
		sql += ing.getDescTraducida()+ "'";
		sql += " WHERE NOMBRE = '" + ing.getNombre()+"'";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void deleteIngrediente(Ingrediente prod) throws SQLException, Exception {

		String sql = "DELETE FROM INGREDIENTE";
		sql += " WHERE NOMBRE = '" + prod.getNombre()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


}
