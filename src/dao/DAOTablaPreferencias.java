package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Preferencia;

public class DAOTablaPreferencias {
	
	
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
	public DAOTablaPreferencias() {
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
	
	public void addPreferenciaUsuario( Preferencia preferencia ) throws SQLException, Exception {

		String sql = "INSERT INTO PREFERENCIA VALUES ('";
		
		sql += preferencia.getIdCliente() + "','";
		sql += preferencia.getTipo() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public ArrayList<Preferencia> darPreferencias(String idCliente) throws SQLException
	{
		ArrayList<Preferencia> preferenciasCliente = new ArrayList<Preferencia>();
		
		String sql = "SELECT * FROM PREFERENCIA";
		sql += " WHERE IDCLIENTE = '" + idCliente +"'";
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			String idCl = rs.getString("IDCLIENTE");
			String catg = rs.getString("TIPO");
			preferenciasCliente.add(new Preferencia(idCl, catg));
		}
		
		return preferenciasCliente;
	}
	
	public void updatePreferencia(Preferencia preferencia) throws SQLException, Exception {
		
		
		String sql = "UPDATE PREFERENCIA SET TIPO='";
		sql += preferencia.getTipo()+ "'";
		sql += "WHERE IDCLIENTE = '"+preferencia.getIdCliente()+"'";
		sql += "AND TIPO='"+preferencia.getTipo()+"'";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public ArrayList<Preferencia> darUsuariosPorTipo(String tipo) throws SQLException{
		
		
		ArrayList<Preferencia> rta = new ArrayList<Preferencia>();
		
		String sql = "SELECT * FROM PREFERENCIA";
		sql += "WHERE TIPO = '" + tipo +"'";
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			String idCl = rs.getString("IDCLIENTE");
			String catg = rs.getString("TIPO");
			rta.add(new Preferencia(idCl, catg));
		}
		
		return rta;
		
	}

	public void deletePreferencia(Preferencia p) throws SQLException, Exception {

		String sql = "DELETE FROM PREFERENCIA";
		sql += " WHERE IDCLIENTE = '" + p.getIdCliente()+"'";
		sql += " AND TIPO = '" + p.getTipo()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}