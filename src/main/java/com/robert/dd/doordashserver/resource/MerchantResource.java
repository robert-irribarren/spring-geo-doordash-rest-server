package com.robert.dd.doordashserver.resource;

import com.robert.dd.doordashserver.model.Merchant;
import com.robert.dd.doordashserver.repository.MerchantRepository;
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
@RequestMapping("/api/merchants")
public class MerchantResource {

    @Autowired
    private MerchantRepository merchantRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Merchant>> getMerchants(){
        Collection<Merchant> merchants = this.merchantRepository.findAll();
        if (merchants.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(merchants, HttpStatus.OK);
    }
}
