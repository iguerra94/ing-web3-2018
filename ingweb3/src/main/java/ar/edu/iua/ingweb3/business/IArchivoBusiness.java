package ar.edu.iua.ingweb3.business;

import ar.edu.iua.ingweb3.model.Archivo;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

public interface IArchivoBusiness {
	public Archivo getOne(int id) throws BusinessException, NotFoundException;
	public Archivo add(Archivo archivo) throws BusinessException;
	public void delete(Archivo archivo) throws BusinessException, NotFoundException;
}