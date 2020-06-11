package br.edu.ifsp.spo.bulls.usersApi.service;


public interface BaseService<T> {

	 T save(T entity) throws Exception;

	 T getById(String id);

	 void delete(String id);

	 T update( T entity) throws Exception;

	 Iterable<T> getAll();
}
