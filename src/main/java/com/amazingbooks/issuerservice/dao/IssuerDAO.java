package com.amazingbooks.issuerservice.dao;

import com.amazingbooks.issuerservice.repository.Issuer;
import com.amazingbooks.issuerservice.repository.IssuerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuerDAO extends JpaRepository<Issuer, IssuerId> {
    public Issuer findByIssuerId(IssuerId issuerId);

}
