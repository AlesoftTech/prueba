package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Usuario;

public class DAOTablaUsuarios {
	
	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOTablaUsuarios
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaUsuarios() {
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
	
	
	public ArrayList<Usuario> darUsuarios() throws SQLException, Exception {
		ArrayList<Usuario> Usuarioz = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		

		while (rs.next()) {
			String pName=rs.getString("NOMBRE");

			System.out.println("Sigue el usuario "+pName);
			String pId=rs.getString("IDENTIFICACION");
			String pCorreo=rs.getString("CORREO");
			String pRol=rs.getString("ROL");
			Usuarioz.add(new Usuario(pName, pId, pCorreo, pRol));
		}
		return Usuarioz;
	}
	
	public Usuario buscarUsuarioPorId(String id) throws SQLException, Exception 
	{
		Usuario uzer = null;

		String sql = "SELECT * FROM USUARIO WHERE IDENTIFICACION = '" + id+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String pName=rs.getString("NOMBRE");
			String pId=rs.getString("IDENTIFICACION");
			String pCorreo=rs.getString("CORREO");
			String pRol=rs.getString("ROL");
			uzer = new Usuario(pName, pId, pCorreo, pRol);

		}

		return uzer;
	}	
	
	
	
	

	public void addUsuario(Usuario us) throws SQLException, Exception {
		
		Usuario testeo=buscarUsuarioPorId(us.getIdentificacion());
		if(testeo!=null)
			throw new Exception("Un usuario con la identificacion "+us.getIdentificacion()+" ya existe.");

		String sql = "INSERT INTO USUARIO VALUES ('";
		sql += us.getNombre() + "','";
		sql += us.getIdentificacion() + "','";
		sql += us.getCorreo() + "','";
		sql += us.getRol() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		

	}
	
	public void updateUsuario(Usuario us) throws SQLException, Exception {

		String sql = "UPDATE USUARIO SET NOMBRE='";
		sql += us.getNombre() + "', CORREO='";
		sql += us.getCorreo() + "',ROL='";
		sql += us.getRol() + "'";
		sql += " WHERE IDENTIFICACION = '" + us.getIdentificacion()+"'";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteUsuario(Usuario us) throws SQLException, Exception {

		String sql = "DELETE FROM USUARIO";
		sql += " WHERE IDENTIFICACION = '" + us.getIdentificacion()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


}
