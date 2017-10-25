package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.EquivalenciaIngrediente;

public class DAOTablaEquivalenciaIngrediente
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
	public DAOTablaEquivalenciaIngrediente() {
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
	
	public ArrayList<EquivalenciaIngrediente> darEquivalenciasPorIngredienteEnRestaurante(String nomP, String nomR) throws Exception, SQLException
	{
		ArrayList<EquivalenciaIngrediente> prodPed = new ArrayList<EquivalenciaIngrediente>();

		String sql = "SELECT * FROM EquivalenciaIngrediente";
		sql += " WHERE NOMRESTAURANTE = '" + nomR+"' AND NOMIngrediente = '"+nomP+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomPr=rs.getString("NOMIngrediente");
			String nomRe=rs.getString("NOMRESTAURANTE");
			String nomE=rs.getString("NOMEQUIVALENCIA");
			prodPed.add(new EquivalenciaIngrediente(nomE, nomPr,nomRe));
		}
		return prodPed;
	}	
	
	
	public void addEquivalenciaIngrediente(EquivalenciaIngrediente rest) throws SQLException, Exception {		
		
		
		String sql = "INSERT INTO EquivalenciaIngrediente VALUES (";
		sql += rest.getnomEquivalencia() + ",'";
		sql += rest.getNomIngrediente() + "','";
		sql += rest.getNomRestaurante() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	
	public void deleteEquivalenciaIngrediente(EquivalenciaIngrediente rest) throws SQLException, Exception {

		String sql = "DELETE FROM EquivalenciaIngrediente";
		sql += " WHERE NOMRESTAURANTE = '" + rest.getNomRestaurante()+"'";
		sql += " AND NOMIngrediente = '" + rest.getNomIngrediente()+"'";
		sql += " AND nomEquivalencia = "+rest.getnomEquivalencia();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}



}
