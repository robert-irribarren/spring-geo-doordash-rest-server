package com.robert.dd.doordashserver.resource;

import com.robert.dd.doordashserver.model.Customer;
import com.robert.dd.doordashserver.repository.CustomerRepository;
import com.robert.dd.doordashserver.validation.BindingErrorsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/customers")
public class CustomerResource {

    @Autowired
    private CustomerRepository customerRepository;

    /** CREATE && UPDATE UPSERT **/
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Customer> saveCustomer(@RequestBody @Valid Customer customer,
                                                 BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();

        if (bindingResult.hasErrors() ||  (customer==null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        Customer savedCustomer = this.customerRepository.save(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

    /** READS **/
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Customer>> getCustomers(Pageable page) {
        Page<Customer> customers = this.customerRepository.findAll(page);
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers.getContent(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String id){
        Customer customer = this.customerRepository.findById(id).orElse(null);
        if (customer==null){
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
