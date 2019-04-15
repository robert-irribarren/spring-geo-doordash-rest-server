package com.robert.dd.doordashserver.resource;

import com.robert.dd.doordashserver.model.BaseModel;
import com.robert.dd.doordashserver.model.MerchantProduct;
import com.robert.dd.doordashserver.repository.MerchantProductItemRepository;
import com.robert.dd.doordashserver.validation.BindingErrorsResponse;
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
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/merchantproducts")
public class MerchantProductResource extends BaseModel {

    @Autowired
    private MerchantProductItemRepository merchantProductItemRepository;

    /** CREATE && UPDATE UPSERT **/
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MerchantProduct> saveMerchantProductItem(@RequestBody @Valid MerchantProduct merchantProductItem, BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();

        if (bindingResult.hasErrors() || merchantProductItem == null){
            errors.addAllErrors(bindingResult);
            headers.add("error",errors.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        this.merchantProductItemRepository.save(merchantProductItem);
        return new ResponseEntity<>(merchantProductItem, HttpStatus.OK);
    }

    /** Get products from a particular merchant **/
    @RequestMapping(value = "/merchants/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<MerchantProduct>> getProductsForMerchant(Pageable page,
                                                                        @PathVariable("id") String id){
        List<MerchantProduct> merch = this.merchantProductItemRepository.getProductsForMerchant(page,id);
        return new ResponseEntity<>(merch, HttpStatus.OK);
    }

    /** Get products from a particular merchant **/
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<MerchantProduct>> getAllProducts(Pageable page){
        Page<MerchantProduct> merch = this.merchantProductItemRepository.findAll(page);
        return new ResponseEntity<>(merch.getContent(), HttpStatus.OK);
    }
}
