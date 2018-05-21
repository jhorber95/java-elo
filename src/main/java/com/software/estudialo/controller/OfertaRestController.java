/**
 * 
 */
package com.software.estudialo.controller;


import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.software.estudialo.entities.Oferta;
import com.software.estudialo.entities.DataTableJson;
import com.software.estudialo.entities.JSONRespuesta;
import com.software.estudialo.entities.Respuesta;
import com.software.estudialo.entities.RespuestaGeneral;
import com.software.estudialo.service.OfertaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class OfertaRestController.
 *
 * @author LUIS
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value="ofertas", description="Operaciones pertenecientes a las ofertas")
public class OfertaRestController {
	
	/** The Constant url. */
	private static final String url = "/oferta";
	
	/** The logger. */
	private Logger logger = Logger.getLogger(CategoriaRestController.class);

	
	/** The oferta service. */
	@Autowired
	OfertaService ofertaService;
		
	
	/**
	 * Obtener oferta.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping(url + "/{id}")
	@ApiOperation(value = "obtiene la oferta con el id" )
    public ResponseEntity<RespuestaGeneral> obtenerOferta(@PathVariable int id) {
		logger.debug("obtenerOferta -- Obtener Oferta");
		Oferta oferta = ofertaService.obtenerOferta(id); 
		List<Oferta> ofertas = new ArrayList<>();
		ofertas.add(oferta);
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(ofertas);
        return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
    }	
	
	
	
	/**
	 * Eliminar oferta de freelancer
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@ApiOperation(value = "un freelancer Elimina su oferta (Cambio de estado a eliminado) - proporciona el id")
	@PostMapping(url + "/freelancer/eliminar")
	@PreAuthorize("hasAuthority('ROLE_FREELANCER')")
	public ResponseEntity<Respuesta> eliminarOfertaFreelancer(@RequestParam(value = "idOferta") int idOferta,
			@RequestParam(value = "idUsuario") int idUsuario) {

		ofertaService.eliminarOfertaFreelancer(idOferta, idUsuario);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);		
	}
	
	
	
	/**
	 * Eliminar oferta de institucion
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@ApiOperation(value = "Una institucion Elimina su oferta (Cambio de estado a eliminado) - proporciona el id")
	@PostMapping(url + "/institucion/eliminar")
	@PreAuthorize("hasAuthority('ROLE_INSTITUCION')")
	public ResponseEntity<Respuesta> eliminarOfertaInstitucion(@RequestParam(value = "idOferta") int idOferta,
			@RequestParam(value = "idInstitucion") int idInstitucion) {

		ofertaService.eliminarOfertaInstitucion(idOferta, idInstitucion);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);		
	}
	
	
	
	
	
	
	
	
	/**
	 * Gets the ofertas buscador.
	 *
	 * @param search the search
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @return the ofertas buscador
	 */
	@ApiOperation(value = "Lista las ofertas a traves de la variable search (Campo de Busqueda)")
	@GetMapping(url + "/buscador")
	public ResponseEntity<JSONRespuesta> getOfertasBuscador(@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getOfertas --- listando la oferta");
		JSONRespuesta listaOferta = new JSONRespuesta();
		listaOferta = ofertaService.listarOfertaBuscador(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listaOferta, HttpStatus.OK);
	}

	/**
	 * Gets the ofertas filtros.
	 *
	 * @param start the start
	 * @param length the length
	 * @param draw the draw
	 * @param posicion the posicion
	 * @param direccion the direccion
	 * @param categoria the categoria
	 * @param municipio the municipio
	 * @param tipoOfrece the tipo ofrece
	 * @param tipoOferta the tipo oferta
	 * @param precioMinimo the precio minimo
	 * @param precioMaximo the precio maximo
	 * @return the ofertas filtros
	 */
	@ApiOperation(value = "Lista las ofertas a traves de los 5 filtros (categoria, municipio, tipoOfrece, tipoOferta, precioMinimo, precioMaximo y nombre carrera)")
	@GetMapping(url + "/filtros")
	public ResponseEntity<JSONRespuesta> getOfertasFiltros(
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion,
			@RequestParam(value = "categoria", defaultValue = "0") int categoria,
			@RequestParam(value = "municipio", defaultValue = "0") int municipio,
			@RequestParam(value = "tipoOfrece", defaultValue = "0") int tipoOfrece,
			@RequestParam(value = "tipoOferta", defaultValue = "0") int tipoOferta,
			@RequestParam(value = "precioMinimo", defaultValue = "0") int precioMinimo,
			@RequestParam(value = "precioMaximo", defaultValue = "10000000") int precioMaximo,
			@RequestParam(value = "stringCarrera", defaultValue = "") String nombreOferta) {
		
		logger.debug("getOfertas --- listando la oferta");
		System.out.println(" =====================================");
		System.out.println("Nombre oferta: " + nombreOferta);
		System.out.println(" =====================================");
		JSONRespuesta listaOferta = new JSONRespuesta();
		listaOferta = ofertaService.listarOfertaFiltros(start, length, draw, posicion, direccion, categoria, municipio, tipoOfrece, tipoOferta, precioMinimo, precioMaximo, nombreOferta);
		return new ResponseEntity<JSONRespuesta>(listaOferta, HttpStatus.OK);
	}
	

	
	
	/**
	 * Listar datos de ofertas seg�n la estructura que requiere DataTable de JQuery .
	 *
	 * @param search            parametro a buscar en la consulta
	 * @param start            limite de inicio de los registros
	 * @param length            limite de fin hasta donde se consultaran los registros
	 * @param draw            n�mero de veces que se realiza la consulta
	 * @param posicion            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de ofertaes segun los parametros enviados
	 */
	@GetMapping(url)
	@ApiOperation(value = "obtiene las lista de ofertas con paginacion para datatables" ) 
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<JSONRespuesta> getOfertas(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {
		
		logger.debug("getOfertas --- listando los ofertas");
		JSONRespuesta listOfertas = new JSONRespuesta();
		listOfertas = ofertaService.listarOfertas(search, start, length, draw, posicion, direccion);
		return new ResponseEntity<JSONRespuesta>(listOfertas, HttpStatus.OK);
	}
	
	
	@PostMapping(url + "/dataTables")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	@ApiOperation(value = "obtiene las lista de ofertas con paginacion para datatables" )
	public ResponseEntity<JSONRespuesta> getOfertasPost(@RequestBody DataTableJson dataTableJson) {
		
		logger.debug("Valores de prueba===> search=" + dataTableJson.getSearch().getValue());
		logger.debug("Valores de prueba===> start=" + dataTableJson.getStart());
		logger.debug("Valores de prueba===> length=" + dataTableJson.getLength());
		logger.debug("Valores de prueba===> draw=" + dataTableJson.getDraw());
		logger.debug("Valores de prueba===> posicion=" + dataTableJson.getOrder().get(0).getPosicion());
		logger.debug("Valores de prueba===> direccion=" + dataTableJson.getOrder().get(0).getDireccion());
		
		String search = dataTableJson.getSearch().getValue();
		int start = dataTableJson.getStart();
		int length = dataTableJson.getLength();
		int draw = dataTableJson.getDraw();
		int posicion = dataTableJson.getOrder().get(0).getPosicion();
		
		
		logger.debug("getUsuarios --- listando los usuarios");
		JSONRespuesta listUsuarios = new JSONRespuesta();
		listUsuarios = ofertaService.listarOfertasAdmin(search, start, length, draw, posicion, "asc");
		return new ResponseEntity<JSONRespuesta>(listUsuarios, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "Lista las ofertas destacadas")
	@GetMapping(url + "/destacadas")
	public ResponseEntity<RespuestaGeneral> getOfertasDestacadas() {		
		logger.debug("getOfertasDestacadas --- listando las ofertas destacadas");
		List<Oferta> destacadas = ofertaService.listarOfertasDestacadas();
		
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(destacadas);
		return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
	}	
	
		
	
	/**
	 * Adicionar oferta.
	 *
	 * @param oferta the oferta
	 * @return the response entity
	 */
	@ApiOperation(value = "Adiciona oferta - debe proporcionar el id del que ofrece dentro del objeto Oferta")
	@PostMapping(url)
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_FREELANCER') or hasAuthority('ROLE_INSTITUCION')")
	public ResponseEntity<Respuesta> adicionarOferta(@RequestBody Oferta oferta) {				
		int idOferta = ofertaService.agregarOferta(oferta);
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		Oferta ofertaCreada =  new Oferta();
		ofertaCreada.setId(idOferta);
		respuesta.setBody(ofertaCreada);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}

	/**
	 * Modificar oferta para el admin
	 *
	 * @param id the id
	 * @param oferta the oferta
	 * @return the response entity
	 */
	@ApiOperation(value = "Modifica una oferta desde el admin - proporciona el id y el objeto oferta")
	@PutMapping(url + "/admin/{id}")	
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> modificarOferta(@PathVariable int id, @RequestBody Oferta oferta) {		
		ofertaService.modificarOferta(id, oferta);		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	
	
	
	@ApiOperation(value = "Modifica una oferta para el usuario que creo la oferta - proporciona el id y el objeto oferta")
	@PutMapping(url + "/{id}")	
	@PreAuthorize("hasAuthority('ROLE_FREELANCER') or hasAuthority('ROLE_INSTITUCION')")
	public ResponseEntity<Respuesta> modificarOfertaGeneral(@PathVariable int id, @RequestBody Oferta oferta) {		
		ofertaService.modificarOfertaGeneral(id, oferta);		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	
	/**
	 * Eliminar oferta.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@ApiOperation(value = "Elimina una oferta (Cambio de estado a eliminado) - proporciona el id")
	@DeleteMapping(url + "/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
	public ResponseEntity<Respuesta> eliminarOferta(@PathVariable int id) {

		ofertaService.eliminarOferta(id);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);		
	}
	
	
	
	@GetMapping(url + "/verificarCalificacionInstitucion/{idOferta}/{idUsuario}")
	@ApiOperation(value = "verifica si ya fue calificada por un estudiante la oferta ofrecida por una institucion" ) 
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_ESTUDIANTE')")
	public ResponseEntity<RespuestaGeneral> verificarCalificacionOfertaInstitucion(@PathVariable int idOferta, @PathVariable int idUsuario) {
		
		logger.debug("verificarCalificacionOfertaInstitucion --- verificando calificacion oferta institucion");
		
		RespuestaGeneral rg = new RespuestaGeneral();		
		Boolean realizada = ofertaService.calificacionOfertaInstitucionRealizada(idOferta, idUsuario);
		rg.setExito(realizada);
		return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
	}
	
	
	
	@GetMapping(url + "/verificarCalificacion/{idOferta}/{idUsuario}")
	@ApiOperation(value = "verifica si ya fue calificada por un estudiante la oferta ofrecida por el freelancer" ) 
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_ESTUDIANTE')")
	public ResponseEntity<RespuestaGeneral> verificarCalificacionOfertaFreelancer(@PathVariable int idOferta, @PathVariable int idUsuario) {
		
		logger.debug("verificarCalificacionOfertaFreelancer --- verificando calificacion oferta freelancer");
		
		RespuestaGeneral rg = new RespuestaGeneral();
		Boolean realizada = ofertaService.calificacionOfertaFreelancerRealizada(idOferta, idUsuario);
		rg.setExito(realizada);				
		return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
	}
	
	
	@PostMapping(url + "/calificarOfertaInstitucion")
	@ApiOperation(value = "un estudiante califica  la oferta ofrecida por la institucion" ) 
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_ESTUDIANTE')")
	public ResponseEntity<Respuesta> calificarOfertaInstitucion(
			@RequestParam(value = "idOferta", defaultValue = "0") int idOferta,
			@RequestParam(value = "idUsuario", defaultValue = "0") int idUsuario,
			@RequestParam(value = "calificacion", defaultValue = "0") double calificacion,
			@RequestParam(value = "comentario", defaultValue = "0") String comentario
			) {
			
		logger.debug("calificarOfertaInstitucion --- calificando oferta institucion");	
		
		ofertaService.calificarOfertaInstitucion(idOferta, idUsuario, calificacion, comentario);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);	
	}
	
	
	@PostMapping(url + "/calificarOfertaFreelancer")
	@ApiOperation(value = "un estudiante califica  la oferta ofrecida por el freelancer" ) 
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_ESTUDIANTE')")
	public ResponseEntity<Respuesta> calificarOfertaFreelancer(
			@RequestParam(value = "idOferta", defaultValue = "0") int idOferta,
			@RequestParam(value = "idUsuario", defaultValue = "0") int idUsuario,
			@RequestParam(value = "calificacion", defaultValue = "0") double calificacion,
			@RequestParam(value = "comentario", defaultValue = "0") String comentario
			) {
			
		logger.debug("calificarOfertaFreelancer --- calificando oferta freelancer");	
		
		ofertaService.calificarOfertaFreelancer(idOferta, idUsuario, calificacion, comentario);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);	
	}
	

	

	
	@ApiOperation(value = "Lista las ofertas ofrecidas por una institucion")
	@GetMapping(url + "/ofrecidas/institucion/{idInstitucion}")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_INSTITUCION')")
	public ResponseEntity<RespuestaGeneral> getOfertasOfrecidasInstitucion(@PathVariable int idInstitucion) {		
		logger.debug("getOfertasOfrecidasInstitucion --- listando las ofertas ofrecidas por una institucion");
		List<Oferta> ofrecidas = ofertaService.getOfertasOfrecidasInstitucion(idInstitucion);
		
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(ofrecidas);
		return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
	}	
	
	@ApiOperation(value = "Lista las ofertas ofrecidas por una freelancer")
	@GetMapping(url + "/ofrecidas/freelancer/{idUsuarioFreelancer}")
	@PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_FREELANCER')")
	public ResponseEntity<RespuestaGeneral> getOfertasOfrecidasFreelancer(@PathVariable int idUsuarioFreelancer) {		
		logger.debug("getOfertasOfrecidasFreelancer --- listando las ofertas ofrecidas por una freelancer");
		List<Oferta> ofrecidas = ofertaService.getOfertasOfrecidasFreelancer(idUsuarioFreelancer);
		
		RespuestaGeneral rg = new RespuestaGeneral();
		rg.setExito(true);
		rg.setCodigo(200);
		rg.setData(ofrecidas);
		return new ResponseEntity<RespuestaGeneral>(rg, HttpStatus.OK);
	}
	

	
	
	
}
