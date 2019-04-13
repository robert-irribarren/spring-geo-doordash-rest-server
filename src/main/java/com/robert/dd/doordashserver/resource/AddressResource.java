package com.robert.dd.doordashserver.resource;

import com.robert.dd.doordashserver.model.Address;
import com.robert.dd.doordashserver.repository.AddressRepository;
import com.robert.dd.doordashserver.validation.BindingErrorsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/api/addresses")
public class AddressResource {

    @Autowired
    private AddressRepository addressRepository;

    /** CREATE && UPDATE UPSERT **/
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Address> saveAddress(@RequestBody @Valid Address address, BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() ||  (address==null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        this.addressRepository.save(address);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    /** READS **/
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Address>> getAddresses(Pageable page) {
        Page<Address> addresses = this.addressRepository.findAll(page);
        if (addresses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(addresses.getContent(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Address>> getAddresses(Pageable page, @PathVariable("id") String customerId) {
        List<Address> addresses = this.addressRepository.getAddressesByCustomerId(page,customerId);
        if (addresses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }


}
