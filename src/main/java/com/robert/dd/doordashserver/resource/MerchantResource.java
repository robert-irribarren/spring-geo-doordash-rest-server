package com.robert.dd.doordashserver.resource;

import com.robert.dd.doordashserver.model.Merchant;
import com.robert.dd.doordashserver.repository.MerchantRepository;
import com.robert.dd.doordashserver.utils.GeoUtils;
import com.robert.dd.doordashserver.validation.BindingErrorsResponse;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/merchants")
public class MerchantResource {

    @Autowired
    private MerchantRepository merchantRepository;

    /** CREATE && UPDATE UPSERT **/
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Merchant> saveAddress(@RequestBody @Valid Merchant merchant, BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() ||  (merchant==null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        this.merchantRepository.save(merchant);
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }

    /** READS **/
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Merchant>> getMerchants(Pageable page){
        Page<Merchant> merchants = this.merchantRepository.findAll(page);
        if (merchants.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(merchants.getContent(), HttpStatus.OK);
    }

    @RequestMapping(value = "/nearby", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Merchant>> getClosestMerchantsToPoint(Pageable page,
            @RequestBody @Valid LocationRequestBody req,
            BindingResult bindingResult){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors()) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }
        Geometry circle = GeoUtils.create3DCircle(req.getLatitude(),req.getLongitude(),req.getRadius());
        List<Merchant> merchants = this.merchantRepository.findAllNearby(page,circle);
        for (Merchant merch: merchants){
            Long dist = GeoUtils.getDistance(merch.getAddress().getLocation().getX(),
                    merch.getAddress().getLocation().getY(),
                    req.getLongitude(),
                    req.getLatitude());
            merch.setDistance(dist);
        }
        merchants.sort(Comparator.comparingLong(Merchant::getDistance));
        if (merchants.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(merchants, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Merchant> getMerchantById(@PathVariable("id") String id){
        Merchant merchant = this.merchantRepository.findById(id).orElse(null);
        if (merchant==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }
}
