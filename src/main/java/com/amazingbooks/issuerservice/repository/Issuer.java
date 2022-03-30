package com.amazingbooks.issuerservice.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "issuer")
public class Issuer {

    @EmbeddedId
    private IssuerId issuerId;

    @Column(name="no_of_copies")
    private Integer noOfCopies;

    @Transient
    private String response;




}
