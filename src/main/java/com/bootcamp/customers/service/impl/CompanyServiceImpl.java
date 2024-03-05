package com.bootcamp.customers.service.impl;

import com.bootcamp.customers.clients.AccountsRestClient;
import com.bootcamp.customers.clients.CreditsRestClient;
import com.bootcamp.customers.dto.CompanyRequestDto;
import com.bootcamp.customers.dto.CompanyResponseDto;
import com.bootcamp.customers.dto.Message;
import com.bootcamp.customers.entity.Company;
import com.bootcamp.customers.repository.CompanyRepository;
import com.bootcamp.customers.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase de implementación para la interfaz CompanyService.
 * @author achumpiy
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    AccountsRestClient accountsRestClient;

    @Autowired
    CreditsRestClient creditsRestClient;

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Método que devuelve todos los clientes de tipo empresarial dentro el repositorio.
     * @return Flux&lt;Company&gt;
     */
    @Override
    public Flux<Company> getAll() {
        return companyRepository.findAll();
    }

    /**
     * Devuelve todos los clientes de tipo empresarial dentro el repositorio segun el id del cliente.
     * @return Mono&lt;Company&gt;
     */
    @Override
    public Mono<Company> getCompanyById(String companyId) {
        return companyRepository.findById(companyId);
    }

    /**
     * Crea un cliente de tipo empresarial dentro del repositorio con los datos enviados en el body.
     * Valida los campos necesarios para la creación en validateCompanyRequest()
     * @param companyRequestDto datos del cliente
     * @return Mono&lt;Company&gt;
     */
    @Override
    public Mono<CompanyResponseDto> createCompany(CompanyRequestDto companyRequestDto) {
        Company company = new Company(null, companyRequestDto.getBusinessName(), companyRequestDto.getRuc(),
                companyRequestDto.getEmail(), companyRequestDto.getTelephone(), companyRequestDto.getHeadlines(),
                companyRequestDto.getSignatories(),"COMPANY", "BUSINESS");
        CompanyResponseDto companyResponseDto = new CompanyResponseDto();
        companyResponseDto.setCompany(company);
        companyResponseDto.setMessage("Business client created successfully");
        return validateCompanyRequest(companyResponseDto)
                .flatMap(validate -> {
                    if (validate.getCompany() == null) {
                        return Mono.just(validate);
                    } else {
                        return companyRepository.save(validate.getCompany())
                                .map(saveCompany -> new CompanyResponseDto(validate.getMessage(), saveCompany));
                    }
                });
    }

    /**
     * Actualiza un cliente de tipo empresarial dentro del repositorio segun los datos enviados en el body.
     * Valida los campos necesarios para la creación en validateCompanyRequest()
     * @param companyRequestDto datos del cliente
     * @return Mono&lt;Company&gt;
     */
    @Override
    public Mono<CompanyResponseDto> updateCompany(CompanyRequestDto companyRequestDto) {
        return companyRepository.findById(companyRequestDto.getId())
                .flatMap(uCompany -> {
                    uCompany.setBusinessName(companyRequestDto.getBusinessName());
                    uCompany.setRuc(companyRequestDto.getRuc());
                    uCompany.setEmail(companyRequestDto.getEmail());
                    uCompany.setTelephone(companyRequestDto.getTelephone());
                    uCompany.setHeadlines(companyRequestDto.getHeadlines());
                    uCompany.setSignatories(companyRequestDto.getSignatories());
                    uCompany.setTypeCustomer("COMPANY");
                    CompanyResponseDto companyResponseDto = new CompanyResponseDto();
                    companyResponseDto.setCompany(uCompany);
                    companyResponseDto.setMessage("business type client upgraded successfully");
                    return validateCompanyRequest(companyResponseDto)
                            .flatMap(validate -> {
                                if (validate.getCompany() == null) {
                                    return Mono.just(validate);
                                } else {
                                    return companyRepository.save(validate.getCompany())
                                            .map(savedCompany -> new CompanyResponseDto(validate.getMessage(),
                                                    savedCompany));
                                }
                            });
                })
                .switchIfEmpty(Mono.just(new CompanyResponseDto("Business client does not exist", null)));
    }

    /**
     * Elimina al cliente de tipo empresarial dentro del repositorio segun el id del cliente.
     * @param companyId identificaor del cliente
     * @return Mono&lt;Message&gt;
     */
    @Override
    public Mono<Message> deleteCompany(String companyId) {
        Message message = new Message("Company does not exist");
        return companyRepository.findById(companyId)
                .flatMap(dCompany -> {
                    message.setMessage("Company deleted successfully");
                    return companyRepository.deleteById(dCompany.getId()).thenReturn(message);
                }).defaultIfEmpty(message);
    }

    /**
     * Valida los titulares o firmantes autorizados.
     * @param companyRequestDto datos del cliente
     * @return Mono&lt;CompanyResponseDto&gt;
     */
    private Mono<CompanyResponseDto> validateCompanyRequest(CompanyResponseDto companyRequestDto) {
        return Mono.just(companyRequestDto)
            .filter(dto -> dto.getCompany().getHeadlines() != null && !dto.getCompany().getHeadlines().isEmpty())
            .filter(dto -> dto.getCompany().getSignatories() == null || !dto.getCompany().getSignatories().isEmpty())
            .switchIfEmpty(Mono.just(new CompanyResponseDto("Must have 1 or more holders",null)));
    }

    /**
     * Verifica el pérfil del cliente.
     * @param companyRequestDto datos del cliente
     * @return Mono&lt;CompanyResponseDto&gt;
     */
    @Override
    public Mono<CompanyResponseDto> requestProfilePyme(CompanyRequestDto companyRequestDto) {
        return companyRepository.findById(companyRequestDto.getId()).flatMap(uCompany -> {
            return accountsRestClient.getAllAccountXCustomerId(uCompany.getId())
                    .filter(c -> c.getDescripTypeAccount().equals("C_CORRIENTE")).next()
                    .flatMap(x -> {
                        return creditsRestClient.getAllCreditCardXCustomerId(uCompany.getId()).next().flatMap(b -> {
                            uCompany.setTypeProfile("PYME");
                            return companyRepository.save(uCompany).flatMap(z -> {
                                return Mono.just(new CompanyResponseDto("Successful profile request (PYME)", uCompany));
                            });
                        }).defaultIfEmpty(new CompanyResponseDto("Customer does not have a credit card", uCompany));
                    }).defaultIfEmpty(new CompanyResponseDto("Client does not have a checking account", uCompany));
        }).defaultIfEmpty(new CompanyResponseDto("Client does not exist",null));
    }

}
