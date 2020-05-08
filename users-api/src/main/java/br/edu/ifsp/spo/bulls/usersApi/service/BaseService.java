package br.edu.ifsp.spo.bulls.usersApi.service;

import java.util.List;

public interface BaseService<T> {

	 T save(T entity) throws Exception;

	 T getById(String id);

	 void delete(String id);

	 T update(T entity);

	 List<T> getAll();
}
