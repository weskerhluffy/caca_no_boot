package caca.servicio;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID> {

	@Autowired
	private CrudRepository<T, ID> repository;

	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GenericServiceImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	public T findOne(ID id) {
		return repository.findById(id).get();
	}

	public List<T> findAll() {
		List<T> result = new ArrayList<T>();
		for (T t : repository.findAll()) {
			result.add(t);
		}
		// XXX:
		// https://stackoverflow.com/questions/6416706/easy-way-to-change-iterable-into-collection
		// result = StreamSupport.stream(repository.findAll().spliterator(),
		// false).collect(Collectors.toList());
		return result;
	}

	public void save(T dto) {
		repository.save(dto);
	}

}
