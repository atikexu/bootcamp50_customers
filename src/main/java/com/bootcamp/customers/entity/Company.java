package com.bootcamp.customers.entity;

import com.bootcamp.customers.dto.AuthorizedDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase de entidad para clientes empresariales.
 * @author achumpiy
 */
@Data
@AllArgsConstructor
@Document(collection = "company")
public class Company {
    @Id
    private String id;
    private String businessName;
    private String ruc;
    private String email;
    private String telephone;
    private List<AuthorizedDto> headlines;
    private List<AuthorizedDto> signatories;
    private String typeCustomer;
    private String typeProfile;
}
