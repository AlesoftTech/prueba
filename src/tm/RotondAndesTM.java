package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import dao.DAOTablaEquivalenciaProducto;
import dao.DAOTablaIngredientes;
import dao.DAOTablaPedidos;
import dao.DAOTablaPreferencias;
import dao.DAOTablaProductoIngrediente;
import dao.DAOTablaProductoMenu;
import dao.DAOTablaProductoPedido;
import dao.DAOTablaProductos;
import dao.DAOTablaRestauranteProducto;
import dao.DAOTablaRestaurantes;
import dao.DAOTablaUsuarios;
import dao.DAOTablaZonas;
import vos.EquivalenciaProducto;
import vos.FechaNombreCuenta;
import vos.InfoUsuario;
import vos.InfoZonaInfoPedido;
import vos.Ingrediente;
import vos.Pedido;
import vos.Preferencia;
import vos.Producto;
import vos.ProductoIngrediente;
import vos.ProductoMenu;
import vos.ProductoPedido;
import vos.RFC1;
import vos.RFC2;
import vos.Restaurante;
import vos.RestauranteProducto;
import vos.Usuario;
import vos.Zona;

public class RotondAndesTM {

	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;


	/**
	 * Metodo constructor de la clase RotondAndesTM, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	public List<Usuario> darUsuarios(String idUs) throws Exception {
		List<Usuario> Usuarios;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un administrador de RotondAndes puede ver todos los usuarios");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioAdmin")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un administrador");
			}
			}
			
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			Usuarios = daoUsuarios.darUsuarios();
			System.out.println("Hay "+Usuarios.size()+" usuarios");

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Usuarios;
	}

	public Usuario buscarUsuarioPorId(String idUs, String id) throws Exception {
		Usuario Usuario;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////transaccion
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Un usuario no registrado no puede ver la informacion de otros usuarios.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioAdmin"))
			{
			if(!idUs.equals(id)) {
				throw new Exception("Sólo un administrador de RotondAndes o el usuario con id "+id+" puede consultar informacion de este usuario");
			}
			}
			}
			
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			Usuario = daoUsuarios.buscarUsuarioPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Usuario;
	}

	public void addUsuario(String idUs, Usuario uzer) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////transaccion
			if(uzer.getRol().equals("UsuarioCliente"))
			{
				Usuario u;
				if(idUs.equals("0")) 
				{
					throw new Exception("Sólo un administrador de RotondAndes puede inscribir un cliente nuevo");
				}
				else {
					this.conn=darConexion();
					daoUsuarios.setConn(conn);
				u=daoUsuarios.buscarUsuarioPorId(idUs);			
				if(!u.getRol().equals("UsuarioAdmin")) {
					throw new Exception("El identificador "+idUs+" no corresponde a un administrador");
				}
				}
			}			
			
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.addUsuario(uzer);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateUsuario(String idUs, Usuario uzer) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////transaccion
			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Un usuario no registrado no puede editar la informacion de otros usuarios.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioAdmin"))
				{if(!idUs.equals(uzer.getIdentificacion())) {
				throw new Exception("Sólo un administrador de RotondAndes o el usuario con id "+uzer.getIdentificacion()+" puede editar informacion de este usuario");
			}
			}
			}
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.updateUsuario(uzer);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteUsuario(String idUs, Usuario uzer) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////transaccion
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Un usuario no registrado no puede eliminar la informacion de otros usuarios.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioAdmin"))
			{if(!idUs.equals(uzer.getIdentificacion())) {
				throw new Exception("Sólo un administrador de RotondAndes o el usuario con id "+uzer.getIdentificacion()+" puede eliminar informacion de este usuario");
			}
			}
			}
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.deleteUsuario(uzer);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<Restaurante> darRestaurantes() throws Exception {
		List<Restaurante> Restaurantes;
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try 
		{			
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			Restaurantes = daoRestaurantes.darRestaurantes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Restaurantes;
	}

	public Restaurante buscarRestaurantePorName(String name) throws Exception {
		Restaurante Restaurante;
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			Restaurante = daoRestaurantes.buscarRestaurantePorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Restaurante;
	}

	public void addRestaurante(String idUs, Restaurante Restaurante) throws Exception {
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////transaccion
			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un administrador de RotondAndes puede agregar un restaurante.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioAdmin")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un administrador");
			}
			}			
			
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.addRestaurante(Restaurante);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateRestaurante(Restaurante Restaurante) throws Exception {
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.updateRestaurante(Restaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteRestaurante(String idUs,String nomR) throws Exception {
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////transaccion
			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un administrador de RotondAndes puede eliminar un restaurante.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioAdmin")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un administrador");
			}
			}
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.deleteRestaurante(nomR);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public List<Zona> darZonas() throws Exception {
		List<Zona> Zonas;
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		try 
		{			
			this.conn = darConexion();
			daoZonas.setConn(conn);
			Zonas = daoZonas.darZonas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Zonas;
	}

	public Zona buscarZonaPorId(Long id) throws Exception {
		Zona Zona;
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoZonas.setConn(conn);
			Zona = daoZonas.buscarZonaPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Zona;
	}

	public void addZona(String idUs, Zona Zona) throws Exception {
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		DAOTablaUsuarios daoUs=new DAOTablaUsuarios();
		try 
		{
			Usuario u = null;
			//////transaccion
			this.conn=darConexion();
			daoUs.setConn(conn);
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un administrador de RotondANdes puede agregar otra zona");
			}
			else {
			u=daoUs.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioAdmin")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un administrador");
			}
			}
			
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.addZona(Zona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateZona(String idUs, Zona Zona) throws Exception {
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		DAOTablaUsuarios daoUs=new DAOTablaUsuarios();
		try 
		{
			Usuario u = null;
			//////transaccion
			this.conn=darConexion();
			daoUs.setConn(conn);
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un administrador de RotondANdes puede agregar otra zona");
			}
			else {
			u=daoUs.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioAdmin")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un administrador");
			}
			}
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.updateZona(Zona);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteZona(String idUs, Zona Zona) throws Exception {
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		DAOTablaUsuarios daoUs=new DAOTablaUsuarios();
		try 
		{
			Usuario u = null;
			//////transaccion
			this.conn=darConexion();
			daoUs.setConn(conn);
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un administrador de RotondANdes puede agregar otra zona");
			}
			else {
			u=daoUs.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioAdmin")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un administrador");
			}
			}
			
			this.conn = darConexion();
			daoZonas.setConn(conn);
			daoZonas.deleteZona(Zona);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public List<Producto> darProductos() throws Exception {
		List<Producto> Productos;
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try 
		{			
			this.conn = darConexion();
			daoProductos.setConn(conn);
			Productos = daoProductos.darProductos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Productos;
	}

	public Producto buscarProductoPorName(String namae) throws Exception {
		Producto Producto;
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			Producto = daoProductos.buscarProductoPorName(namae);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Producto;
	}

	public void addProducto(String idUs,Producto Producto) throws Exception {
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los productos.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.addProducto(Producto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateProducto(String idUs,Producto Producto) throws Exception {
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los productos.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.updateProducto(Producto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteProducto(String idUs,Producto Producto) throws Exception {
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los productos.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoProductos.deleteProducto(Producto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}	

	public List<Ingrediente> darIngredientes() throws Exception {
		List<Ingrediente> Ingredientes;
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try 
		{			
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			Ingredientes = daoIngredientes.darIngredientes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Ingredientes;
	}

	public Ingrediente buscarIngredientePorName(String namae) throws Exception {
		Ingrediente Ingrediente;
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			Ingrediente = daoIngredientes.buscarIngredientePorName(namae);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Ingrediente;
	}

	public void addIngrediente(String idUs,Ingrediente Ingrediente) throws Exception {
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los ingredientes.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.addIngrediente(Ingrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateIngrediente(String idUs,Ingrediente Ingrediente) throws Exception {
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los ingredientes.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.updateIngrediente(Ingrediente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteIngrediente(String idUs,Ingrediente Ingrediente) throws Exception {
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los ingredientes.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			//////transaccion
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			daoIngredientes.deleteIngrediente(Ingrediente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void addRestauranteProducto(String idUs, RestauranteProducto RestauranteProducto) throws Exception {
		DAOTablaRestauranteProducto daoRestauranteProductos = new DAOTablaRestauranteProducto();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los productos que sirve un restaurante");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			
			Producto p=buscarProductoPorName(RestauranteProducto.getNomProducto());
			if(p==null)
			{
				throw new Exception("El producto "+RestauranteProducto.getNomProducto()+" no está registrado en el sistema.");
			}
			
			if(p.getCategoria().equals("Menu"))
			{
				if(darRestaurantesPorProducto(p.getNombre())!=null)
				{
					if(!darRestaurantesPorProducto(p.getNombre()).isEmpty())
					{
						throw new Exception("Otro restaurante ya sirve un menu con el nombre "+p.getNombre()+". Cambie el nombre del menu.");
					}
				}
			}
			
			this.conn = darConexion();
			daoRestauranteProductos.setConn(conn);
			daoRestauranteProductos.addRestauranteProducto(RestauranteProducto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestauranteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<RestauranteProducto> darRestaurantesPorProducto(String nom) throws Exception {
		List<RestauranteProducto> resPros;
		DAOTablaRestauranteProducto daoResPros = new DAOTablaRestauranteProducto();
		try 
		{			
			this.conn = darConexion();
			daoResPros.setConn(conn);
			resPros = daoResPros.darRestaurantesPorProducto(nom);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoResPros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resPros;
	}

	public RestauranteProducto darRestauranteProducto(String nomR, String nomP) throws Exception {
		RestauranteProducto resPros=null;
		DAOTablaRestauranteProducto daoResPros = new DAOTablaRestauranteProducto();
		try 
		{			
			this.conn = darConexion();
			daoResPros.setConn(conn);
			resPros = daoResPros.darRestauranteProducto(nomR, nomP);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoResPros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resPros;
	}

	public List<RestauranteProducto> darProductosPorRestaurante(String nom) throws Exception {
		List<RestauranteProducto> resPros;
		DAOTablaRestauranteProducto daoResPros = new DAOTablaRestauranteProducto();
		try 
		{			
			this.conn = darConexion();
			daoResPros.setConn(conn);
			resPros = daoResPros.darProductosPorRestaurante(nom);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoResPros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resPros;
	}

	public void deleteRestauranteProducto(String idUs,RestauranteProducto RestauranteProducto) throws Exception {
		DAOTablaRestauranteProducto daoRestauranteProductos = new DAOTablaRestauranteProducto();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los productos que sirve un restaurante");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			
			this.conn = darConexion();
			daoRestauranteProductos.setConn(conn);
			daoRestauranteProductos.deleteRestauranteProducto(RestauranteProducto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestauranteProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}	

	public List<Pedido> darPedidos() throws Exception {
		List<Pedido> Pedidos;
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		try 
		{			
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			Pedidos = daoPedidos.darPedidos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Pedidos;
	}

	public Pedido buscarPedidoPorId(Long id) throws Exception {
		Pedido Pedido;
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			Pedido = daoPedidos.darPedidoPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return Pedido;
	}

	public void addPedido(String idUs, Pedido Pedido) throws Exception {
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario x;
			if(!idUs.equals("0")) 
			{
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
				x=daoUsuarios.buscarUsuarioPorId(idUs);			
				if(!x.getRol().equals("UsuarioCliente")) {
					throw new Exception("El identificador "+idUs+" no corresponde a un usuarioCliente");
				}
			}
			
			//////transaccion
			if(Pedido.getIdUsuario()!=null)
			{
			this.conn=darConexion();
			daoUsuarios.setConn(conn);
			if(!idUs.equals(Pedido.getIdUsuario()))
			{
				throw new Exception("El usuario con identificacion "+idUs+" no puede ordenar un pedido para "+Pedido.getIdUsuario());
			}
			
			Usuario u=daoUsuarios.buscarUsuarioPorId(Pedido.getIdUsuario());
			if(!u.getRol().equals("UsuarioCliente"))
				throw new Exception("El identificador "+Pedido.getIdUsuario()+" no corresponde a un cliente");
			}
			
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			daoPedidos.addPedido(Pedido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void updatePedido(String idUs,Pedido Pedido) throws Exception {
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario x;
			if(!idUs.equals("0")) 
			{
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
				x=daoUsuarios.buscarUsuarioPorId(idUs);			
				if(!x.getRol().equals("UsuarioCliente")) {
					throw new Exception("El identificador "+idUs+" no corresponde a un usuarioCliente");
				}
			}
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			daoPedidos.updatePedido(Pedido);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	public void deletePedido(String idUs,Pedido Pedido) throws Exception {
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario x;
			if(!idUs.equals("0")) 
			{
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
				x=daoUsuarios.buscarUsuarioPorId(idUs);			
				if(!x.getRol().equals("UsuarioCliente")) {
					throw new Exception("El identificador "+idUs+" no corresponde a un usuarioCliente");
				}
			}
			//////transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			daoPedidos.deletePedido(Pedido);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<ProductoPedido> darProductoPedidosPorPedido(Long idPedido) throws Exception {
		List<ProductoPedido> prodPedidos;
		DAOTablaProductoPedido daoProductoPedidos = new DAOTablaProductoPedido();
		try 
		{			
			this.conn = darConexion();
			daoProductoPedidos.setConn(conn);
			prodPedidos = daoProductoPedidos.darRestauranteProductosPorIdPedido(idPedido);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return prodPedidos;
	}



	public void addProductoPedido(String idUs, ProductoPedido prodPed) throws Exception {
		DAOTablaProductoPedido daoProductoPedidos = new DAOTablaProductoPedido();
		DAOTablaPedidos daoPeds= new DAOTablaPedidos();
		DAOTablaRestauranteProducto daoResPros= new DAOTablaRestauranteProducto();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		
		try 
		{			
			Usuario x;
			if(!idUs.equals("0")) 
			{
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
				x=daoUsuarios.buscarUsuarioPorId(idUs);			
				if(!x.getRol().equals("UsuarioCliente")) {
					throw new Exception("El identificador "+idUs+" no corresponde a un usuarioCliente");
				}
			}
			//////transaccion
			this.conn = darConexion();
			daoResPros.setConn(conn);
			RestauranteProducto testeo=darRestauranteProducto(prodPed.getNomRestaurante(), prodPed.getNomProducto());
			if(testeo==null)
				throw new Exception("El producto "+prodPed.getNomProducto()+" no se vende en "+prodPed.getNomRestaurante());
			else if(testeo.getUnidadesDisponibles()<=0)
				throw new Exception("El producto "+prodPed.getNomProducto()+" se acabó en "+prodPed.getNomRestaurante());
			else
			{
				Producto pact=buscarProductoPorName(prodPed.getNomProducto());
				if(pact.getCategoria().equals("Menu"))
				{
					for(ProductoMenu vaina:darProductosPorMenu(pact.getNombre()))
					{						
						String nomP=vaina.getNomProducto();
						String nomR=prodPed.getNomRestaurante();
						Long idz=prodPed.getIdPedido(); 
						addProductoPedido(idUs,new ProductoPedido(idz, nomP, nomR));
					}
				}				

				this.conn = darConexion();
				daoResPros.setConn(conn);

				int n=testeo.getUnidadesDisponibles();
				testeo.setUnidadesDisponibles(n-1);
				daoResPros.updateRestauranteProducto(testeo);
			}	
			
			this.conn=darConexion();
			daoPeds.setConn(conn);
			Pedido pi=daoPeds.darPedidoPorId(prodPed.getIdPedido());
			double c=testeo.getPrecio();
			double p=pi.getPrecioTotal();
			pi.setPrecioTotal(p+c);
			updatePedido(idUs,pi);

			this.conn = darConexion();
			daoProductoPedidos.setConn(conn);
			daoProductoPedidos.addProductoPedido(prodPed);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	public void deleteProductoPedido(String idUs,ProductoPedido ProductoPedido) throws Exception {
		DAOTablaProductoPedido daoProductoPedidos = new DAOTablaProductoPedido();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario x;
			if(!idUs.equals("0")) 
			{
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
				x=daoUsuarios.buscarUsuarioPorId(idUs);			
				if(!x.getRol().equals("UsuarioCliente")) {
					throw new Exception("El identificador "+idUs+" no corresponde a un usuarioCliente");
				}
			}
			//////transaccion
			this.conn = darConexion();
			daoProductoPedidos.setConn(conn);
			daoProductoPedidos.deleteProductoPedido(ProductoPedido);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void agregarPreferencia(String idUs,Preferencia preferencia) throws Exception {

		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario x;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioCliente puede ver editar las preferencias.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			x=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!x.getRol().equals("UsuarioCliente")||!x.getIdentificacion().equals(preferencia.getIdCliente())) {
				throw new Exception("Sólo el usuario con id "+preferencia.getIdCliente()+" puede editar preferencias de este usuario");
			}
			}
			//////transaccion
			this.conn=darConexion();
			daoUsuarios.setConn(conn);
			Usuario u=daoUsuarios.buscarUsuarioPorId(preferencia.getIdCliente());
			if(!u.getRol().equals("UsuarioCliente"))
				throw new Exception("El identificador "+preferencia.getIdCliente()+" no corresponde a un cliente");
			
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			daoPreferencias.addPreferenciaUsuario(preferencia);
			conn.commit();		
		}
		catch( SQLException e )
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			}
			catch(SQLException e) {
				System.err.println("SQLException closing resources:" + e.getMessage());
				e.printStackTrace();
				throw e;
			}

		}

	}

	public void updatePreferencia (String idUs, Preferencia pref) throws SQLException, Exception {

		DAOTablaPreferencias preferencias = new DAOTablaPreferencias();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario x;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioCliente puede ver editar las preferencias.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			x=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!x.getRol().equals("UsuarioCliente")||!x.getIdentificacion().equals(pref.getIdCliente())) {
				throw new Exception("Sólo el usuario con id "+pref.getIdCliente()+" puede editar preferencias de este usuario");
			}
			}
			//////transaccion
			this.conn = darConexion();
			preferencias.setConn(conn);
			preferencias.updatePreferencia(pref);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				preferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List <Preferencia> buscarPreferenciasPorIdCliente (String idUs,String idCliente) throws Exception, SQLException {

		List<Preferencia> preferencias;
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		DAOTablaUsuarios daoUsuarios=new DAOTablaUsuarios();
		try 
		{			
			Usuario x;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioCliente puede ver editar las preferencias.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			x=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!x.getRol().equals("UsuarioCliente")||!x.getIdentificacion().equals(idCliente)) {
				throw new Exception("Sólo el usuario con id "+idCliente+" puede editar preferencias de este usuario");
			}
			}		
			this.conn=darConexion();
			daoUsuarios.setConn(conn);
			Usuario u=daoUsuarios.buscarUsuarioPorId(idCliente);
			if(!u.getRol().equals("UsuarioCliente"))
				throw new Exception("El identificador "+idCliente+" no corresponde a un cliente");
			
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			preferencias = daoPreferencias.darPreferencias(idCliente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencias;

	}


	public void deletePreferencia(String idUs,Preferencia preferencia) throws Exception, SQLException {
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario x;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioCliente puede ver editar las preferencias.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			x=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!x.getRol().equals("UsuarioCliente")||!x.getIdentificacion().equals(preferencia.getIdCliente())) {
				throw new Exception("Sólo el usuario con id "+preferencia.getIdCliente()+" puede editar preferencias de este usuario");
			}
			}
			//////transaccion
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			daoPreferencias.deletePreferencia(preferencia);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<ProductoMenu> darProductosPorMenu(String nomM) throws Exception {
		List<ProductoMenu> prodPedidos;
		DAOTablaProductoMenu daoProductoMenus = new DAOTablaProductoMenu();
		try 
		{			
			this.conn = darConexion();
			daoProductoMenus.setConn(conn);
			prodPedidos = daoProductoMenus.darProductosPorMenu(nomM);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return prodPedidos;
	}



	public void addProductoMenu(String idUs,ProductoMenu prodPed) throws Exception 
	{
		DAOTablaProductoMenu daoProductoMenus = new DAOTablaProductoMenu();
		DAOTablaProductos daoProds= new DAOTablaProductos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los menus.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
				u=daoUsuarios.buscarUsuarioPorId(idUs);			
				if(!u.getRol().equals("UsuarioRestaurante")) {
					throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
				}
			}
			//////transaccion
			this.conn = darConexion();
			daoProds.setConn(conn);
			Producto menuActual=daoProds.buscarProductoPorName(prodPed.getNomMenu());
			if(menuActual==null)
			{
				throw new Exception("El menú "+prodPed.getNomMenu()+" no existe.");
			}
			String nomR="";
			for(RestauranteProducto vaina:darRestaurantesPorProducto(menuActual.getNombre()))
			{
				nomR=vaina.getNomRestaurante();
			}
			if(nomR.equals(""))
			{
				throw new Exception("El menú "+prodPed.getNomMenu()+" no se vende en ningún restaurante, para agregar productos a un menú"
						+ " este debe ser vendido en el mismo restaurante que sus productos.");
			}
			if(darRestauranteProducto(nomR, prodPed.getNomProducto())==null)
			{
				throw new Exception("El producto "+prodPed.getNomProducto()+" no se vende en "+nomR+". Para agregarlo al menu "+prodPed.getNomMenu()+
						", el producto debe venderse en el mismo restaurante.");
			}
			List<ProductoMenu> listica=darProductosPorMenu(prodPed.getNomMenu());
			Producto base=daoProds.buscarProductoPorName(prodPed.getNomProducto());
			for(ProductoMenu p:listica)
			{
				Producto actual=daoProds.buscarProductoPorName(p.getNomProducto());				
				if(base.getCategoria().equals(actual.getCategoria()))
					throw new Exception("El menu no puede tener mas de un producto de la categoria "+base.getCategoria());
			}
			
			this.conn = darConexion();
			daoProductoMenus.setConn(conn);
			daoProductoMenus.addProductoMenu(prodPed);
			conn.commit();
			
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	public void deleteProductoMenu(String idUs,ProductoMenu ProductoMenu) throws Exception {
		DAOTablaProductoMenu daoProductoMenus = new DAOTablaProductoMenu();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los menus.");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
				u=daoUsuarios.buscarUsuarioPorId(idUs);			
				if(!u.getRol().equals("UsuarioRestaurante")) {
					throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
				}
			}
			//////transaccion
			this.conn = darConexion();
			daoProductoMenus.setConn(conn);
			daoProductoMenus.deleteProductoMenu(ProductoMenu);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void addProductoIngrediente(String idUs,ProductoIngrediente ProductoIngrediente) throws Exception {
		DAOTablaProductoIngrediente daoProductoIngredientes = new DAOTablaProductoIngrediente();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los productos que sirve un restaurante");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			//////transaccion
			this.conn = darConexion();
			daoProductoIngredientes.setConn(conn);
			daoProductoIngredientes.addProductoIngrediente(ProductoIngrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<ProductoIngrediente> darIngredientesPorProducto(String nomP) throws Exception {
		List<ProductoIngrediente> resPros;
		DAOTablaProductoIngrediente daoResPros = new DAOTablaProductoIngrediente();
		try 
		{			
			this.conn = darConexion();
			daoResPros.setConn(conn);
			resPros = daoResPros.darIngredientesPorProducto(nomP);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoResPros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resPros;
	}



	public void deleteProductoIngrediente(String idUs,ProductoIngrediente ProductoIngrediente) throws Exception {
		DAOTablaProductoIngrediente daoProductoIngredientes = new DAOTablaProductoIngrediente();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede ver editar los ingredientes de un producto");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			//////transaccion
			this.conn = darConexion();
			daoProductoIngredientes.setConn(conn);
			daoProductoIngredientes.deleteProductoIngrediente(ProductoIngrediente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	
	public void addEquivalenciaProducto(String idUs, EquivalenciaProducto eqPro) throws Exception {
		DAOTablaEquivalenciaProducto daoEquivalenciaProductos = new DAOTablaEquivalenciaProducto();		
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario x;
			if(!idUs.equals("0")) 
			{
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
				x=daoUsuarios.buscarUsuarioPorId(idUs);			
				if(!x.getRol().equals("UsuarioRestaurante")) {
					throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
				}
			}
			//////transaccion
			this.conn = darConexion();
			daoEquivalenciaProductos.setConn(conn);
			daoEquivalenciaProductos.addEquivalenciaProducto(eqPro);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalenciaProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void deleteEquivalenciaProducto(String idUs,EquivalenciaProducto EquivalenciaProducto) throws Exception {
		DAOTablaEquivalenciaProducto daoEquivalenciaProductos = new DAOTablaEquivalenciaProducto();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{			
			Usuario u;
			if(idUs.equals("0")) 
			{
				throw new Exception("Sólo un usuarioRestaurante puede editar las equivalencias de un producto");
			}
			else {
				this.conn=darConexion();
				daoUsuarios.setConn(conn);
			u=daoUsuarios.buscarUsuarioPorId(idUs);			
			if(!u.getRol().equals("UsuarioRestaurante")) {
				throw new Exception("El identificador "+idUs+" no corresponde a un usuarioRestaurante");
			}
			}
			//////transaccion
			this.conn = darConexion();
			daoEquivalenciaProductos.setConn(conn);
			daoEquivalenciaProductos.deleteEquivalenciaProducto(EquivalenciaProducto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalenciaProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	
	
	
	
	
	
	
	
	
	
	
	public RFC2 consultarUnaZona(Long id) throws Exception {

		DAOTablaZonas daoZona = new DAOTablaZonas();
		DAOTablaPedidos daoPedido = new DAOTablaPedidos();

		try{

			this.conn = darConexion();
			daoZona.setConn(conn);
			Zona zona= daoZona.buscarZonaPorId(id);

			this.conn = darConexion();
			daoPedido.setConn(conn);
			List<Pedido> pedidos =daoPedido.darPedidosPorIdZona(id);			

			return new RFC2(zona, pedidos);

		}catch (SQLException e) {
			System.err.println("SQLException:"+ e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}
	
	public InfoUsuario RFC3(String idCliente) throws Exception
	{
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaPedidos daoPedidos=new DAOTablaPedidos();
		DAOTablaPreferencias daoPref=new DAOTablaPreferencias();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			Usuario u=daoUsuarios.buscarUsuarioPorId(idCliente);
			if(u.getRol()!="UsuarioCliente")
				throw new Exception("El identificador "+idCliente+" no corresponde a un cliente");
			
			this.conn=darConexion();
			daoPref.setConn(conn);
			ArrayList<Preferencia> l=daoPref.darPreferencias(idCliente);
			
			this.conn=darConexion();
			daoPedidos.setConn(conn);
			ArrayList<Pedido> p=daoPedidos.darPedidoPorIdCliente(idCliente);
			
			return new InfoUsuario(u,l,p);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
		
	}
	
	public List<FechaNombreCuenta> RFC6(String fechasita) throws SQLException
	{
		DAOTablaProductos daoProds=new DAOTablaProductos();
		try 
		{
			char[] n=fechasita.toCharArray();
			char[] de=new char[n.length+2];
			de[0]=n[0]; de[1]=n[1]; de[2]=n[2];de[3]=n[3];de[4]='/';de[5]=n[4];de[6]=n[5];de[7]='/';de[8]=n[6];de[9]=n[7];
			String mia="";
			for(int i=0;i<de.length;i++)
			{
				mia=mia+de[i];				
			}
			
			ArrayList<FechaNombreCuenta> def=new ArrayList();
			//////transaccion
			this.conn = darConexion();
			daoProds.setConn(conn);
			ArrayList<FechaNombreCuenta> u=daoProds.darProductosMasVendidos();	
			for(FechaNombreCuenta b:u)
			{
				if(b.getFecha().equals(mia))
					def.add(b);
			}
			return def;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProds.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
	}
	
	public List<InfoZonaInfoPedido> consultarUnaZona(String criterioBusqueda) throws Exception {

		DAOTablaZonas daoZona = new DAOTablaZonas();
		List<InfoZonaInfoPedido> zonass;

		try{
			this.conn = darConexion();
			daoZona.setConn(conn);
			zonass = daoZona.consultarUnaZona(criterioBusqueda);

		}catch (SQLException e) {
			System.err.println("SQLException:"+ e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonass;


	}
	
	public List<Producto> buscarProductosMasOfrecidosRFC4() throws Exception {

		List<String> productos;
		ArrayList<Producto> losProd=new ArrayList();

		DAOTablaProductoMenu daoPM = new DAOTablaProductoMenu();

		try{
			this.conn = darConexion();
			daoPM.setConn(conn);
			productos = daoPM.darProductosMasOfrecidos();
			for(String p:productos)
			{
				Producto z=buscarProductoPorName(p);
				if(z!=null)
					losProd.add(z);
			}
			return losProd;

		}catch (SQLException e) {
			System.err.println("SQLException:"+ e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPM.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}


	}
	
	public List<Producto> consultarProductosQueOfrecen(String criterio) throws Exception
	{
		List <Producto> rta;
		DAOTablaProductos daoP = new DAOTablaProductos();
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoP.setConn(conn);
			rta = daoP.consultarProductosQueOfrecen(criterio);
			
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoP.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rta;
	
	}
	
	public RFC1 consultarRestaurante(String categoria) throws Exception 

	{
		DAOTablaRestauranteProducto daoRP = new DAOTablaRestauranteProducto();
		DAOTablaRestaurantes daoR = new DAOTablaRestaurantes();
		DAOTablaProductos daoP = new DAOTablaProductos();
		List<RestauranteProducto> resta = null;
		try{


			this.conn = darConexion();
			daoP.setConn(conn);
			List<Producto> producto = daoP.productoPorCategoria(categoria);


			for (int i = 0; i < producto.size(); i++) {

				this.conn = darConexion();
				daoRP.setConn(conn);
				resta = daoRP.darRestaurantesPorProducto(producto.get(i).getNombre());

			}

			return new RFC1(resta, producto);}

		catch (SQLException e) {
			System.err.println("SQLException:"+ e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRP.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
			try {
				daoR.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}






}
