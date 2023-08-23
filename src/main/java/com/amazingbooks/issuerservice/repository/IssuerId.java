package com.amazingbooks.issuerservice.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssuerId implements Serializable {

    private String customerId;
    private Long isbn;


}
