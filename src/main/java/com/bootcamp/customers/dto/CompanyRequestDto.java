package com.bootcamp.customers.dto;

import java.util.List;
import lombok.Data;

/**
 * Clase de transferencia de datos para la solicitud
 * de los clientes empresariales.
 * @author achumpiy
 */
@Data
public class CompanyRequestDto {
    private String id;
    private String businessName;
    private String ruc;
    private String email;
    private String telephone;
    private List<AuthorizedDto> headlines;
    private List<AuthorizedDto> signatories;
    private String typeCustomer;
}
