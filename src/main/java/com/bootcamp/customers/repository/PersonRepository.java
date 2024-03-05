package com.bootcamp.customers.repository;

import com.bootcamp.customers.entity.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Clase Repositorio para los métodos de acceso a la base de datos de los clientes personales.
 * @author achumpiy
 */
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

}
