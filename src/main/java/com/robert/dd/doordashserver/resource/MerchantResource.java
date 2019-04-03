package com.robert.dd.doordashserver.resource;

import com.robert.dd.doordashserver.model.Merchant;
import com.robert.dd.doordashserver.repository.MerchantRepository;

import com.robert.dd.doordashserver.utils.GeoUtils;
import com.robert.dd.doordashserver.validation.BindingErrorsResponse;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/merchants")
public class MerchantResource {

    @Autowired
    private MerchantRepository merchantRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Iterable<Merchant>> getMerchants(){
        Collection<Merchant> merchants = this.merchantRepository.findAll();
        if (merchants.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(merchants, HttpStatus.OK);
    }

    private final double NEARBY_DIAMETER_METERS = 3000d;
    @RequestMapping(value = "/nearby", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Merchant>> getClosestMerchantsToPoint(
            @RequestBody @Valid LocationRequestBody req,
            BindingResult bindingResult){
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors()) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }
        Polygon circle = GeoUtils.createRadius(req.getLatitude(),req.getLongitude(),req.getRadius());
        //Geometry geo = GeoUtils.bufferPoint(req.getLatitude(),req.getLongitude(),req.getRadius());
        Collection<Merchant> merchants = this.merchantRepository.findAllNearby(circle);
        for (Merchant merch: merchants){
            Long dist = GeoUtils.getDistance(merch.getAddress().getLocation().getY(),
                    merch.getAddress().getLocation().getX(),
                    req.getLongitude(),
                    req.getLatitude());
            System.out.println(dist);
        }
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
