package com.robert.dd.doordashserver.resource;

import com.robert.dd.doordashserver.model.Address;
import com.robert.dd.doordashserver.repository.AddressRepository;
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
@RequestMapping("/api/address")
public class AddressResource {

    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Address>> getOwners() {
        Collection<Address> addresses = this.addressRepository.findAll();
        if (addresses.isEmpty()) {
            return new ResponseEntity<Collection<Address>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Address>>(addresses, HttpStatus.OK);
    }
}
