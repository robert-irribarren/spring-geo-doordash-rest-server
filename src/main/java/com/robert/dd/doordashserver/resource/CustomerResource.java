package com.robert.dd.doordashserver.resource;

import com.robert.dd.doordashserver.model.Customer;
import com.robert.dd.doordashserver.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/customers")
public class CustomerResource {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Customer>> getOwners() {
        Collection<Customer> customers = this.customerRepository.findAll();
        if (customers.isEmpty()) {
            return new ResponseEntity<Collection<Customer>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Customer>>(customers, HttpStatus.OK);
    }
}
