package ar.edu.iua.ingweb3.business.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IArchivoBusiness;
import ar.edu.iua.ingweb3.model.Archivo;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;
import ar.edu.iua.ingweb3.model.persistence.ArchivoRepository;

@Service
public class ArchivoBusiness implements IArchivoBusiness {

	@Autowired
	private ArchivoRepository archivoDAO;
	
	@Override
	public Archivo getOne(int id) throws BusinessException, NotFoundException {
		Optional<Archivo> archivo = null;
		
		try {
			archivo = archivoDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		
		if (!archivo.isPresent()) {
			throw new NotFoundException();
		}
	
		return archivo.get();
	}

	@Override
	public Archivo add(Archivo archivo) throws BusinessException {
		try {
			return archivoDAO.save(archivo);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(Archivo archivo) throws BusinessException, NotFoundException {
		Optional<Archivo> arch = null;
		
		arch = archivoDAO.findById(archivo.getId());

		if (!arch.isPresent()) {
			throw new NotFoundException();
		}

		try {
			archivoDAO.delete(archivo);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}