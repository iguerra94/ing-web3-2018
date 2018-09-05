package ar.edu.iua.ingweb3.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IProductoBusiness;
import ar.edu.iua.ingweb3.model.Producto;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

@Service
public class ProductoBusiness implements IProductoBusiness {

	private List<Producto> r = new ArrayList<Producto>();
	
	public ProductoBusiness() {
		r.add(new Producto(1, "Arroz", 41.50, true, new Date()));
		r.add(new Producto(2, "Leche", 35, true, new Date()));
		r.add(new Producto(3, "Cerveza", 39.50, true, null));
	}

	@Override
	public List<Producto> getAll() throws BusinessException {
		return r;
	}

	@Override
	public Producto getOne(int id) throws BusinessException, NotFoundException {
		Producto producto = null;
		
		for (Producto p : r) {
			if (p.getId() == id) {
				producto = p;
				break;
			}
		}
		
		if (producto == null) {
			throw new NotFoundException();
		}
		return producto;
	}

	@Override
	public Producto add(Producto producto) throws BusinessException {
		r.add(producto);
		return producto;
	}

	@Override
	public Producto update(Producto producto) throws BusinessException, NotFoundException {
		boolean found = false;
		
		for (int i = 0; i < r.size(); i++) {
			Producto p = r.get(i);
			if (p.getId() == producto.getId()) {
				r.set(i, producto);
				found = true;
				break;
			}
		}
		
		if (!found) {
			throw new NotFoundException();
		}
		return producto;
	}

	@Override
	public void delete(Producto producto) throws BusinessException, NotFoundException {
		boolean found = false;
		
		for (int i = 0; i < r.size(); i++) {
			Producto p = r.get(i);
			if (p.getId() == producto.getId()) {
				r.remove(i);
				found = true;
				break;
			}
		}
		
		if (!found) {
			throw new NotFoundException();
		}
	}

	@Override
	public List<Producto> search(String searchInput) throws BusinessException {
		List<Producto> resultado = new ArrayList<>();
		
		for (Producto p : r) {
			if (p.getDescripcion().toLowerCase().indexOf(searchInput.toLowerCase()) != -1) {
				resultado.add(p);
			}
		}
		
		return resultado;
	}

}