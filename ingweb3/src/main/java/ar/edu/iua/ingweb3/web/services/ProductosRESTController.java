package ar.edu.iua.ingweb3.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IProductoBusiness;
import ar.edu.iua.ingweb3.model.Producto;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

@RestController
@RequestMapping("/productos")
public class ProductosRESTController {
	
	@Autowired
	private IProductoBusiness productoBusiness;

	@RequestMapping(value= {"","/"},method=RequestMethod.GET,produces="application/json")
	public ResponseEntity<List<Producto>> lista(
			@RequestParam(required=false, value="q", defaultValue="*") String q,
			@RequestParam(required=false, value="precio_desde", defaultValue="-1") double precioDesde,
			@RequestParam(required=false, value="precio_hasta", defaultValue="-1") double precioHasta){

		try {
			if (precioDesde != -1 && precioHasta != -1 && precioDesde <= precioHasta) {
				return new ResponseEntity<List<Producto>>(productoBusiness.searchByPrecios(precioDesde, precioHasta), HttpStatus.OK);
			} else if (q.equals("*") || q.trim().length() == 0) {
				return new ResponseEntity<List<Producto>>(productoBusiness.getAll(), HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Producto>>(productoBusiness.search(q), HttpStatus.OK);
			}
		} catch (BusinessException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value= {"/{id}"},method=RequestMethod.GET,produces="application/json")
	public ResponseEntity<Producto> uno(@PathVariable("id") int id){
		try {
			return new ResponseEntity<Producto>(productoBusiness.getOne(id), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(value= {"","/"},method=RequestMethod.POST,produces="application/json")
	public ResponseEntity<Producto> add(@RequestBody Producto producto, UriComponentsBuilder uriComponentsBuilder){
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(
				uriComponentsBuilder
					.path("/productos/{id}")
					.buildAndExpand(producto.getId())
					.toUri()
			);
			
			return new ResponseEntity<Producto>(productoBusiness.add(producto), headers, HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}