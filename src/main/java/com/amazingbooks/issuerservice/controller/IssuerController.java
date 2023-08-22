package com.amazingbooks.issuerservice.controller;

import com.amazingbooks.issuerservice.model.Book;
import com.amazingbooks.issuerservice.repository.Issuer;
import com.amazingbooks.issuerservice.service.IssuerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/amazingbooks")
@EnableCaching
public class IssuerController {

    @Autowired
    public IssuerService issuerService;


    @GetMapping
    public String welcome(){
        return "Welcome to Amazing Books";
    }

   /* @GetMapping("/user")
    public Map<String, Object> userDetails(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }
*/
    @PostMapping("/save/book")
    public Book saveBook(@RequestBody Book book){
        return issuerService.saveBook(book);
    }


    @GetMapping("/book/search/{searchCritera}/{value}")
    public List<Book> getBooksBySearchCriteria(@PathVariable String searchCritera, @PathVariable String value){
        List<Book> book = issuerService.getBooksBySearchCriteria(searchCritera, value);
            return book;
    }

    @GetMapping("/books")
    public List<Book> fetchAllBooks(){
        List<Book> book = issuerService.fetchAllBooks();
        return book;
    }


    @PostMapping("/issue/book/{isbn}/{noOfCopies}")
    public Issuer issueBooks(@PathVariable Long isbn, @PathVariable Integer noOfCopies){
        String customerId = "mailtopawangupta@gmail.com";
        Issuer issuer = issuerService.issueBooks(isbn,noOfCopies,customerId);
        return issuer;
    }
}
