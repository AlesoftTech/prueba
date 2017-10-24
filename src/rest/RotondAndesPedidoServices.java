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
import vos.Pedido;

@Path("{idUsuario}/pedidos")
public class RotondAndesPedidoServices {
	
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
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPedidos() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Pedido> Pedidos;
		try {
			Pedidos = tm.darPedidos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Pedidos).build();
	}

	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getPedido( @PathParam( "id" ) Long id)
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Pedido v = tm.buscarPedidoPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPedido(@PathParam("idUsuario") String idUsuario,Pedido Pedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addPedido(idUsuario,Pedido);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Pedido).build();
	}



	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePedido(@PathParam("idUsuario") String idUsuario,Pedido Pedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deletePedido(idUsuario,Pedido);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Pedido).build();
	}



}
