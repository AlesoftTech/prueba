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
import vos.Ingrediente;

@Path("{idUsuario}/ingredientes")
public class RotondAndesIngredienteServices {

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
	public Response getIngredientes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Ingrediente> Ingredientes;
		try {
			Ingredientes = tm.darIngredientes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Ingredientes).build();
	}

	@GET
	@Path( "{name}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getIngrediente( @PathParam( "name" ) String name )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Ingrediente v = tm.buscarIngredientePorName( name );
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
	public Response addIngrediente(@PathParam("idUsuario") String idUsuario,Ingrediente Ingrediente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addIngrediente(idUsuario,Ingrediente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Ingrediente).build();
	}


	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateIngrediente(@PathParam("idUsuario") String idUsuario,Ingrediente Ingrediente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateIngrediente(idUsuario,Ingrediente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Ingrediente).build();
	}


	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteIngrediente(@PathParam("idUsuario") String idUsuario,Ingrediente Ingrediente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteIngrediente(idUsuario,Ingrediente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Ingrediente).build();
	}



}
