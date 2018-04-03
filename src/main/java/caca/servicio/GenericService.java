package caca.servicio;

import java.io.Serializable;
import java.util.List;

public interface GenericService <T, ID extends Serializable>{
	T findOne(ID id);

	List<T> findAll();

	void save(T dto);
}
