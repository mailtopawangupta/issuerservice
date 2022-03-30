package com.amazingbooks.issuerservice.service;

import com.amazingbooks.issuerservice.dao.IssuerDAO;
import com.amazingbooks.issuerservice.model.Book;
import com.amazingbooks.issuerservice.repository.Issuer;
import com.amazingbooks.issuerservice.repository.IssuerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class IssuerService {

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public IssuerDAO issuerDAO;

    public List<Book> getBooksBySearchCriteria(String searchCritera, String value){
        List<Book> books = new ArrayList<>();
        Book book = null;
        switch (searchCritera){
            case "isbn":
                book =  restTemplate.getForObject("http://book-service/amazingbooks/book/isbn/{criteria}",Book.class, Long.parseLong(value));
                books.add(book);
                break;
            case "title":
                books = (List<Book>) restTemplate.getForObject("http://book-service/amazingbooks/books/title/{criteria}",List.class, value);
                break;
            case "author":
                books = (List<Book>) restTemplate.getForObject("http://book-service/amazingbooks/books/author/{criteria}",List.class, value);
                break;
        }

        return books;
    }


    public List<Book> fetchAllBooks(){
        List<Book> books = new ArrayList<>();
        books = restTemplate.getForObject("http://book-service/amazingbooks/books",List.class);

        return books;
    }

    @Transactional
    public Issuer issueBooks(Long isbn, Integer noOfCopies, String customerId){
        Issuer issuer = null;
        List<Book> books = this.getBooksBySearchCriteria("isbn",String.valueOf(isbn));
        IssuerId issuerId = IssuerId.builder()
                .customerId(customerId)
                .isbn(isbn)
                .build();
        issuer = issuerDAO.findByIssuerId(issuerId);
        if(null!=issuer){
            issuer.setResponse("Already Issued");
            return issuer;
        }
        else {
            issuer = Issuer.builder().issuerId(issuerId).build();
            for (Book book : books) {
                if(null!=book) {
                    Integer availableCopies = book.getTotalCopies() - book.getIssuedCopies();
                    if (availableCopies > 0) {

                        issuer.setIssuerId(issuerId);
                        issuer.setNoOfCopies(noOfCopies);
                        issuerDAO.save(issuer);

                        book.setIssuedCopies(book.getIssuedCopies() + noOfCopies);
                        book = restTemplate.postForObject("http://book-service/amazingbooks/book", book, Book.class);
                        issuer.setResponse(book.getIssuedCopies() + " Copies Issued");

                    } else {
                        issuer.setResponse("Zero Available Copies");
                    }
                }else
                {
                    issuer.setResponse("Book Not Available");
                }
            }
        }
            return issuer;
    }

    @Transactional
    public Book saveBook(Book book){
           book = restTemplate.postForObject("http://book-service/amazingbooks/book", book, Book.class);
           return  book;
    }


}
