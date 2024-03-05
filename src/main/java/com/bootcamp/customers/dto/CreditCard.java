package com.bootcamp.customers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import lombok.Data;

/**
 * Clase de transferencia de datos para el microservicio de tarjetas de credito.
 * @author achumpiy
 */
@Data
@JsonInclude(Include.NON_NULL)
public class CreditCard {
    private String id;
    private String customerId;
    private Integer typeAccount;
    private String descripTypeAccount;
    private Double creditAmount;
    private Double existingAmount;
    private Date creditDate;
    private String numberCard;
    private String typeCustomer;
    private Double amount;
}
