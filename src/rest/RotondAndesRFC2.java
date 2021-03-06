package rest;

	import javax.servlet.ServletContext;
	import javax.ws.rs.GET;
	import javax.ws.rs.Path;
	import javax.ws.rs.PathParam;
	import javax.ws.rs.Produces;
	import javax.ws.rs.core.Context;
	import javax.ws.rs.core.MediaType;
	import javax.ws.rs.core.Response;
	
	import tm.RotondAndesTM;
import vos.RFC2;
import vos.Usuario;
	
	@Path("consultaZona")
	public class RotondAndesRFC2
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
		@Path( "{id: \\d+}" )
		@Produces({ MediaType.APPLICATION_JSON })
		public Response consultarZona(@PathParam( "id" ) Long id)
		{
			RotondAndesTM tm = new RotondAndesTM(getPath());
			RFC2 rta;
			try {
			 rta = tm.consultarUnaZona(id);
				return Response.status( 200 ).entity(rta).build( );
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
	
		}

	}