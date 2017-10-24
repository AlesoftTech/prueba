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
	import vos.Preferencia;

	@Path("{idUsuario}/preferencias")
	public class RotondAndesPreferenciasServices {

		/**
		 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual
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
		@Path( "{idCliente}" )
		@Produces( { MediaType.APPLICATION_JSON } )
		public Response getPreferencias( @PathParam("idUsuario") String idUsuario,@PathParam( "idCliente" ) String idCliente)
		{
			RotondAndesTM tm = new RotondAndesTM( getPath( ) );
			try
			{
				List<Preferencia> p = tm.buscarPreferenciasPorIdCliente( idUsuario,idCliente );
				return Response.status( 200 ).entity(p).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}

		@POST
		@Path( "{idCliente}" )
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addPreferencia(@PathParam("idUsuario") String idUsuario,Preferencia preferencia) {
			RotondAndesTM tm = new RotondAndesTM(getPath());
			try {
				tm.agregarPreferencia(idUsuario,preferencia);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(preferencia).build();
		}


		@PUT
		@Path( "{idCliente}" )
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response updatePreferencia(@PathParam("idUsuario") String idUsuario,Preferencia preferencia) {
			RotondAndesTM tm = new RotondAndesTM(getPath());
			try {
				tm.updatePreferencia(idUsuario,preferencia);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(preferencia).build();
		}


		@DELETE
		@Path( "{idCliente}" )
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deletePreferencia(@PathParam("idUsuario") String idUsuario,Preferencia preferencia) {
			RotondAndesTM tm = new RotondAndesTM(getPath());
			try {
				tm.deletePreferencia(idUsuario,preferencia);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(preferencia).build();
		}



	}
