package com.bootcamp.customers.repository;

import com.bootcamp.customers.entity.Company;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Clase Repositorio para los métodos de acceso a la base de datos de los clientes empresariales.
 * @author achumpiy
 */
public interface CompanyRepository extends ReactiveMongoRepository<Company, String> {

}
