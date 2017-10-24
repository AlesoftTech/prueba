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
import vos.InfoZonaInfoPedido;
import vos.Zona;

@Path("{idUsuario}/zonas")
public class RotondAndesZonaServices {
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
	public Response getZonas() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Zona> Zonas;
		try {
			Zonas = tm.darZonas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Zonas).build();
	}

	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getZona( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Zona v = tm.buscarZonaPorId( id );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	@GET
	@Path( "{criterio}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response consultarUnaZona(@PathParam( "criterio" ) String criterio) {

		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{ 
			List<InfoZonaInfoPedido> rta = tm.consultarUnaZona(criterio);
			return Response.status( 200 ).entity( rta ).build( );			
		}
		catch( Exception e)
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}

	}

   
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addZona(@PathParam("idUsuario") String idUsuario,Zona Zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addZona(idUsuario,Zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Zona).build();
	}
	
   
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateZona(@PathParam("idUsuario") String idUsuario,Zona Zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateZona(idUsuario,Zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Zona).build();
	}
	

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteZona(@PathParam("idUsuario") String idUsuario,Zona Zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteZona(idUsuario,Zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Zona).build();
	}



}
