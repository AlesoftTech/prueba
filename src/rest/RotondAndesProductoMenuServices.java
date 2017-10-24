package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import tm.RotondAndesTM;
import vos.Producto;
import vos.ProductoMenu;

@Path("{idUsuario}/productosMenu")
public class RotondAndesProductoMenuServices {
	
	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}	
	
	@GET
	@Path( "{nomM}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurantesPorProducto(@PathParam( "nomM" ) String nomM ) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<ProductoMenu> ProductoMenus;
		try {
			ProductoMenus = tm.darProductosPorMenu(nomM);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ProductoMenus).build();
	}


   
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProductoMenu(@PathParam("idUsuario") String idUsuario,ProductoMenu ProductoMenu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addProductoMenu(idUsuario,ProductoMenu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ProductoMenu).build();
	}	
   
	
	

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProductoMenu(@PathParam("idUsuario") String idUsuario,ProductoMenu ProductoMenu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteProductoMenu(idUsuario,ProductoMenu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ProductoMenu).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurantesPorProducto() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> productos;
		try {
			productos = tm.buscarProductosMasOfrecidosRFC4();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
	}

}





