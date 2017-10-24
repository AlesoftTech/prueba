
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
import vos.Usuario;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/UsuarioAndes/rest/Usuarios/...
 * @author Monitores 2017-20
 */
@Path("{idUsuario}/usuarios")
public class RotondAndesUsuarioServices {

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
	public Response getUsuarios(@PathParam("idUsuario") String idUsuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> Usuarios;
		try {
			Usuarios = tm.darUsuarios(idUsuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Usuarios).build();
	}

	@GET
	@Path( "{id}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getUsuario(@PathParam("idUsuario") String idUsuario, @PathParam( "id" ) String id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Usuario v = tm.buscarUsuarioPorId( idUsuario, id );
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
	public Response addUsuario(@PathParam("idUsuario") String idUsuario, Usuario Usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addUsuario(idUsuario,Usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Usuario).build();
	}
	
   
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(@PathParam("idUsuario") String idUsuario, Usuario Usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateUsuario(idUsuario,Usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Usuario).build();
	}
	

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(@PathParam("idUsuario") String idUsuario, Usuario Usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteUsuario(idUsuario,Usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Usuario).build();
	}


}
