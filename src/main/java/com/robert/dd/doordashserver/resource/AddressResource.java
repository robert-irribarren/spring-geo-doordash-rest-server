package com.robert.dd.doordashserver.resource;

import com.robert.dd.doordashserver.model.Address;
import com.robert.dd.doordashserver.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/addresses")
public class AddressResource {

    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Address>> getAddresses(Pageable pageable) {
        Page<Address> addresses = this.addressRepository.findAll(pageable);
        if (addresses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(addresses.getContent(), HttpStatus.OK);
    }
}
