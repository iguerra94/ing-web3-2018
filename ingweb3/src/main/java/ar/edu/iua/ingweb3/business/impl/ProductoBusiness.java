package ar.edu.iua.ingweb3.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IProductoBusiness;
import ar.edu.iua.ingweb3.model.Producto;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;
import ar.edu.iua.ingweb3.model.persistence.ProductoRepository;

@Service
public class ProductoBusiness implements IProductoBusiness {

	@Autowired
	private ProductoRepository productoDAO;
	
	@Override
	public List<Producto> getAll() throws BusinessException {
		try {
			return productoDAO.findAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Producto getOne(int id) throws BusinessException, NotFoundException {
		Optional<Producto> producto = null;
		try {
			producto = productoDAO.findById(id);

			if (producto == null) {
				throw new NotFoundException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	
		return producto.get();
	}

	@Override
	public Producto add(Producto producto) throws BusinessException {
		try {
			return productoDAO.save(producto);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Producto update(Producto producto) throws BusinessException, NotFoundException {
		Optional<Producto> pr = null;
		try {
			pr = productoDAO.findById(producto.getId());

			if (pr == null) {
				throw new NotFoundException();
			}

			return productoDAO.save(producto);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(Producto producto) throws BusinessException, NotFoundException {
		Optional<Producto> pr = null;
		try {
			pr = productoDAO.findById(producto.getId());

			if (pr == null) {
				throw new NotFoundException();
			}			

			productoDAO.delete(producto);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Producto> search(String searchInput) throws BusinessException {
		List<Producto> resultado = null;
		List<Producto> r = null;
		try {
			resultado = new ArrayList<>();
			r = productoDAO.findAll();
			
			for (Producto p : r) {
				if (p.getDescripcion().toLowerCase().indexOf(searchInput.toLowerCase()) != -1) {
					resultado.add(p);
				}
			}
			
			return resultado;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}