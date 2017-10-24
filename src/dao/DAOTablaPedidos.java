	package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import vos.Pedido;
import vos.Zona;

public class DAOTablaPedidos 
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
	 * Metodo constructor que crea DAOTablaProductos
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPedidos() {
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
	
	public ArrayList<Pedido> darPedidos() throws SQLException, Exception {
		ArrayList<Pedido> Pedidoz = new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idp=rs.getLong("IDPEDIDO");
			Date f=rs.getDate("FECHA");
			Long idz=rs.getLong("IDZONA");
			double p=rs.getDouble("PRECIOTOTAL");
			String i=rs.getString("IDUSUARIO");
			Pedidoz.add(new Pedido(idp,f,idz,p,i));
		}
		return Pedidoz;
	}
	
	public Pedido darPedidoPorId(Long id) throws SQLException, Exception {
		Pedido Pedidoz=null;

		String sql = "SELECT * FROM PEDIDO WHERE IDPEDIDO= "+id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			Long idp=rs.getLong("IDPEDIDO");
			Date f=rs.getDate("FECHA");
			Long idz=rs.getLong("IDZONA");
			double p=rs.getDouble("PRECIOTOTAL");
			String i=rs.getString("IDUSUARIO");
			Pedidoz=new Pedido(idp,f,idz,p,i);
		}
		return Pedidoz;
	}
	
	public ArrayList<Pedido> darPedidoPorIdCliente(String idC) throws SQLException, Exception {
		ArrayList<Pedido> Pedidoz=new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO WHERE IDUSUARIO= '"+idC+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			Long idp=rs.getLong("IDPEDIDO");
			Date f=rs.getDate("FECHA");
			Long idz=rs.getLong("IDZONA");
			double p=rs.getDouble("PRECIOTOTAL");
			String i=rs.getString("IDUSUARIO");
			Pedidoz.add(new Pedido(idp,f,idz,p,i));
		}
		return Pedidoz;
	}


	

	public void addPedido(Pedido prod) throws SQLException, Exception {
		
		Pedido testeo=darPedidoPorId(prod.getIdPedido());
		if(testeo!=null)
			throw new Exception ("Un pedido con la id "+prod.getIdPedido()+" ya existe.");

		String sql = "INSERT INTO PEDIDO VALUES(SYSDATE,";
		sql += prod.getIdPedido()+",";
		sql += prod.getIdZona()+",";
		sql += 0+", '";
		sql += prod.getIdUsuario()+"')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updatePedido(Pedido prod) throws SQLException, Exception {

		String sql = "UPDATE PEDIDO SET PRECIOTOTAL="+prod.getPrecioTotal();
		sql += " WHERE IDPEDIDO = " + prod.getIdPedido()+"";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void deletePedido(Pedido prod) throws SQLException, Exception {

		String sql = "DELETE FROM PEDIDO";
		sql += " WHERE IDPEDIDO = " + prod.getIdPedido();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public ArrayList<Zona> buscarZonaPedidos() throws SQLException, Exception {

		ArrayList<Pedido> pedidos = darPedidos();
		DAOTablaZonas tz = new DAOTablaZonas();
		ArrayList<Zona> zonas = new ArrayList<Zona>();

		for (int i = 0; i < pedidos.size(); i++) {

			Zona zonitaPedido = tz.buscarZonaPorId(pedidos.get(i).getIdZona());
			zonas.add(zonitaPedido);
		}
		return zonas;
	}

	public Pedido buscarPedidoPorZona(Long idZona) throws SQLException, Exception 

	{
		Pedido pedidoBuscado = null;

		String sql = "SELECT * FROM PEDIDO";
		sql += "WHERE IDZONA = " + idZona;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long idp=rs.getLong("IDPEDIDO");
			Date f=rs.getDate("FECHA");
			Long idz=rs.getLong("IDZONA");
			double p=rs.getDouble("PRECIOTOTAL");
			String i=rs.getString("IDUSUARIO");
			pedidoBuscado = new Pedido(idp,f,idz,p, i);
		}

		return pedidoBuscado;
	}
	
public ArrayList<Pedido> darPedidosPorIdZona(Long id) throws SQLException, Exception {
		
		ArrayList<Pedido> Pedidoz = new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO WHERE IDZONA= "+id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			Long idp=rs.getLong("IDPEDIDO");
			Date f=rs.getDate("FECHA");
			Long idz=rs.getLong("IDZONA");
			double p=rs.getDouble("PRECIOTOTAL");
			String i=rs.getString("IDUSUARIO");

			Pedidoz.add(new Pedido(idp,f,idz,p,i));
		}
		return Pedidoz;
	}




}
