package com.robert.dd.doordashserver.resource;

import com.robert.dd.doordashserver.model.CustomerOrder;
import com.robert.dd.doordashserver.repository.CustomerOrderRepository;
import com.robert.dd.doordashserver.validation.BindingErrorsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/orders")
public class CustomerOrderResource {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;


    /**
     * Upsert for saving an order. Will create new if ID is not set.
     * @param customerOrder Entity
     * @return the new customerorder
     */
    @PostMapping("/")
    public ResponseEntity<CustomerOrder> saveOrder(@RequestBody @Valid CustomerOrder customerOrder
            , BindingResult bindingResult){
        HttpHeaders headers = new HttpHeaders();
        BindingErrorsResponse errors = new BindingErrorsResponse();
        if (bindingResult.hasErrors() || customerOrder == null){
            errors.addAllErrors(bindingResult);
            headers.set("errors",errors.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        CustomerOrder savedOrder = this.customerOrderRepository.save(customerOrder);
        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> getOrderById(@PathVariable("id") String id){
        CustomerOrder order = this.customerOrderRepository.findById(id).orElse(null);
        if (order==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    /**
     *
     * @param userId
     * @return Returns the latest unfinished order from the user id
     */
    @GetMapping("/users/{id}/latest")
    public ResponseEntity<CustomerOrder> getMyLatestOrder(@PathVariable("id") String userId){
        CustomerOrder order = this.customerOrderRepository.findLatestByUserId(userId);
        if (order==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    /**
     *
     * @param userId
     * @return Returns all orders from this user
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<List<CustomerOrder>> getAllOrdersByUserId(@PathVariable("id") String userId){
        List<CustomerOrder> order = this.customerOrderRepository.findAllByCustomerId(userId);
        if (order==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
}
