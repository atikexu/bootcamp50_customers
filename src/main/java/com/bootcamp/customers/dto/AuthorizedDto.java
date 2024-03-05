package com.bootcamp.customers.dto;

import lombok.Data;

/**
 * Clase de transferencia de datos para la lista
 * de titulares de las cuentas empresariales.
 * @author achumpiy
 */
@Data
public class AuthorizedDto {
    private String name;
    private String lastName;
    private String dni;
    private String email;
    private String telephone;
}
