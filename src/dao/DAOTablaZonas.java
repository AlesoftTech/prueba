package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.InfoZonaInfoPedido;
import vos.Pedido;
import vos.Zona;

public class DAOTablaZonas
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
	 * Metodo constructor que crea DAOTablaZonas
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaZonas() {
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
	
	
	public ArrayList<Zona> darZonas() throws SQLException, Exception {
		ArrayList<Zona> zonaz = new ArrayList<Zona>();

		String sql = "SELECT * FROM ZONA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long pId=rs.getLong("ID");
			Integer pCap=rs.getInt("CAPACIDAD");
			boolean pAb=rs.getBoolean("ABIERTO");
			boolean pAcc=rs.getBoolean("ACCESONECESP");
			boolean pAsa=rs.getBoolean("ASADORES");
			boolean pExt=rs.getBoolean("EXTRACTORES");
			boolean pCal=rs.getBoolean("CALENTADORES");
			boolean pAco=rs.getBoolean("ACONDICIONARSE");
			zonaz.add(new Zona(pId, pCap,pAb,  pAcc, pAsa, pExt,  pCal, pAco));
		}
		return zonaz;
	}
	
	public Zona buscarZonaPorId(Long id) throws SQLException, Exception 
	{
		Zona uzer = null;

		String sql = "SELECT * FROM ZONA WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long pId=rs.getLong("ID");
			Integer pCap=rs.getInt("CAPACIDAD");
			boolean pAb=rs.getBoolean("ABIERTO");
			boolean pAcc=rs.getBoolean("ACCESONECESP");
			boolean pAsa=rs.getBoolean("ASADORES");
			boolean pExt=rs.getBoolean("EXTRACTORES");
			boolean pCal=rs.getBoolean("CALENTADORES");
			boolean pAco=rs.getBoolean("ACONDICIONARSE");
			uzer=new Zona(pId, pCap, pAb, pAcc, pAsa, pExt,  pCal, pAco);

		}

		return uzer;
	}	

	
	public void addZona(Zona zon) throws SQLException, Exception {
		
		Zona testeo=buscarZonaPorId(zon.getId());
		if(testeo!=null)
			throw new Exception ("Una zona con la id "+zon.getId()+" ya existe.");

		String sql = "INSERT INTO ZONA VALUES (";
		sql += zon.getId() + ",";
		sql += zon.getCapacidad() + ",";
		sql += zon.isAbiertoCHAR() + ",";
		sql += zon.isAccesoNecEspCHAR() + ",";
		sql += zon.isAsadoresCHAR() + ",";
		sql += zon.isExtractoresCHAR() + ",";
		sql += zon.isCalentadoresCHAR() + ",";
		sql += zon.isAcondicionarseCHAR() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void updateZona(Zona zon) throws SQLException, Exception {

		String sql = "UPDATE ZONA SET ";
		sql += zon.getCapacidad() + "',";
		sql += zon.isAbiertoCHAR() + ",";
		sql += zon.isAccesoNecEspCHAR() + "',";
		sql += zon.isAsadoresCHAR() + "',";
		sql += zon.isExtractoresCHAR() + "',";
		sql += zon.isCalentadoresCHAR() + "',";
		sql += zon.isAcondicionarseCHAR();
		sql += " WHERE ID = " + zon.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteZona(Zona zon) throws SQLException, Exception {

		String sql = "DELETE FROM ZONA";
		sql += " WHERE ID = " + zon.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public ArrayList<InfoZonaInfoPedido> consultarUnaZona(String criterioBusqueda) throws SQLException, Exception
	{
		ArrayList<Zona> zonitas = new ArrayList<Zona>();
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		ArrayList<Zona> zonaPedidos = daoPedidos.buscarZonaPedidos();
		DAOTablaProductoPedido daoproductoPedidos = new DAOTablaProductoPedido();
		ArrayList<InfoZonaInfoPedido> rta = new ArrayList<InfoZonaInfoPedido>();


		for (int i = 0; i < zonaPedidos.size(); i++) {

			Long idZona = zonaPedidos.get(i).getId();

			String sql = "SELECT * FROM ZONA";
			sql += "WHERE ID ="+ idZona;
			sql += "GROUP BY" + criterioBusqueda;
			sql += "ORDER BY" + criterioBusqueda;

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			if(rs.next()) {

				Long pId=rs.getLong("ID");
				Integer pCap=rs.getInt("CAPACIDAD");
				boolean pAb=rs.getBoolean("ABIERTO");
				boolean pAcc=rs.getBoolean("ACCESONECESP");
				boolean pAsa=rs.getBoolean("ASADORES");
				boolean pExt=rs.getBoolean("EXTRACTORES");
				boolean pCal=rs.getBoolean("CALENTADORES");
				boolean pAco=rs.getBoolean("ACONDICIONARSE");

				Zona zonita = new Zona(pId, pCap, pAb, pAcc, pAsa, pExt,  pCal, pAco);
				Pedido ppe= daoPedidos.buscarPedidoPorZona(idZona);
				rta.add(new InfoZonaInfoPedido(zonita,ppe, daoproductoPedidos.darRestauranteProductosPorIdPedido(ppe.getIdPedido())));


			}	

		}
		return rta;

	}


}
