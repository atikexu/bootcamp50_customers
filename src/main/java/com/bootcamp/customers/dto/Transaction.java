package com.bootcamp.customers.dto;

import java.util.Date;
import lombok.Data;

/**
 * Clase de transferencia de datos para el microservicio de transacciones.
 * @author achumpiy
 */
@Data
public class Transaction {
    private String id;
    //AHORRO, C_CORRIENTE, PLAZO_FIJO, CRE_PERSONAL, CRED_EMPRESARIAL, TAR_CRED_PERSONAL, TAR_CRED_EMPRESARIAL
    private String productType; 
    private String productId;
    private String customerId;
    //DEPOSITO, RETIRO, PAGO, CONSUMO
    private String transactionType; 
    private Double amount;
    private Date transactionDate;
    private String customerType;
}
