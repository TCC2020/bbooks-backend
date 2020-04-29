package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.List;

public interface BaseService<T> {

	 T save(T entity) throws Exception;

	 T getById(Long id);

	 void delete(T entity);

	 T update(T entity);

	 List<T> getAll();
}
