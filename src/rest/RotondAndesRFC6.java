package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.FechaNombreCuenta;
import vos.InfoUsuario;
import vos.Usuario;

@Path("productosMasVendidos")
public class RotondAndesRFC6
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

	@GET
	@Path("{fecha}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response darInfoUsuario(@PathParam("fecha") String f)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<FechaNombreCuenta> iu;
		try {
			iu=tm.RFC6(f);		
			return Response.status( 200 ).entity(iu).build( );
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}


	}

}
