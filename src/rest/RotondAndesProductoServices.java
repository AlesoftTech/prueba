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

@Path("{idUsuario}/productos")
public class RotondAndesProductoServices 
{
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
	

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProducto(@PathParam("idUsuario") String idUsuario,Producto Producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addProducto(idUsuario,Producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Producto).build();
	}


	@GET
	@Path( "{name}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getProducto( @PathParam( "name" ) String name )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Producto v = tm.buscarProductoPorName(name);
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

   
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProductos() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> Productos;
		try {
			Productos = tm.darProductos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Productos).build();
	}


	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProducto(@PathParam("idUsuario") String idUsuario,Producto Producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateProducto(idUsuario,Producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Producto).build();
	}
	

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProducto(@PathParam("idUsuario") String idUsuario,Producto Producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteProducto(idUsuario,Producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Producto).build();
	}
	
	@GET
	@Path( "{criterio}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response consultarProductosQueOfrecen( @PathParam( "criterio" ) String criterio) 
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			List<Producto> rta = tm.consultarProductosQueOfrecen(criterio);
			return Response.status( 200 ).entity( rta ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	
	}


}
