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
import vos.ProductoPedido;

@Path("{idUsuario}/productosPedidos")
public class RotondAndesProductoPedidoServices {
	
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
	@Path( "{id: \\d+}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProductosPorPedido(@PathParam( "id" ) Long idPedido ) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<ProductoPedido> ProductoPedidos;
		try {
			ProductoPedidos = tm.darProductoPedidosPorPedido(idPedido);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ProductoPedidos).build();
	}


   
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProductoPedido(@PathParam("idUsuario") String idUsuario,ProductoPedido ProductoPedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addProductoPedido(idUsuario,ProductoPedido);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ProductoPedido).build();
	}	
   
	
	

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProductoPedido(@PathParam("idUsuario") String idUsuario,ProductoPedido ProductoPedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteProductoPedido(idUsuario,ProductoPedido);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ProductoPedido).build();
	}


}
