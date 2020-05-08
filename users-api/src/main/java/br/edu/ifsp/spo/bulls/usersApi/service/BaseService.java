package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.List;

public interface BaseService<T> {

	 T save(T entity) throws Exception;

	 T getById(Long id);

	 void delete(Long id);

	 T update(T entity);

	 List<T> getAll();
}
