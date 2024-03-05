package com.bootcamp.customers.controller;

import com.bootcamp.customers.dto.Message;
import com.bootcamp.customers.dto.PersonRequestDto;
import com.bootcamp.customers.dto.PersonResponseDto;
import com.bootcamp.customers.entity.Person;
import com.bootcamp.customers.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Controller para clientes personales.
 * @author achumpiy
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Devuelve todos los clientes de tipo personal.
     * @return Flux&lt;Person&gt;
     */
    @GetMapping
    public Flux<Person> getAll() {
        return personService.getAll();
    }

    /**
     * Devuelve todos los clientes de tipo personal segun el id del cliente.
     * @param personId identificador del cliente
     * @return Mono&lt;Person&gt;
     */
    @GetMapping("/{personId}")
    public Mono<Person> getPersonById(@PathVariable String personId) {
        return personService.getPersonById(personId);
    }

    /**
     * Crea un nuevo cliente del tipo personal con los datos enviados en el body.
     * @param personRequestDto datos del cliente
     * @return Mono&lt;Person&gt;
     */
    @PostMapping(consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Person> createPerson(@RequestBody PersonRequestDto personRequestDto) {
        return personService.createPerson(personRequestDto);
    }

    /**
     * Actualiza el cliente de tipo personal según los datos enviados en el body.
     * @param personRequestDto datos del cliente
     * @return Mono&lt;Person&gt;
     */
    @PutMapping(consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Person> updatePerson(@RequestBody PersonRequestDto personRequestDto) {
        return personService.updatePerson(personRequestDto);
    }

    /**
     * Elimina el cliente de tipo personal segun el id del cliente.
     * @param personId identificador del cliente
     * @return Mono&lt;Message&gt;
     */
    @DeleteMapping("/{personId}")
    public Mono<Message> deletePerson(@PathVariable String personId) {
        return personService.deletePerson(personId);
    }
    
    /**
	 * Solicitud para cliente VIP
	 * @param personRequestDto
	 * @return Mono<Person>
	 */
	@PostMapping(value = "/vip", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PersonResponseDto> requestProfileVip(@RequestBody PersonRequestDto personRequestDto){
		return personService.requestProfileVip(personRequestDto);
    }

}
