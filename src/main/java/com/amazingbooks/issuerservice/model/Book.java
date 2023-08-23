package com.amazingbooks.issuerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Book {

    private Long isbn;

    private String title;

    private String publishDate;

    private Integer totalCopies;

    private Integer issuedCopies;

    private String author;

    private String response;

}
