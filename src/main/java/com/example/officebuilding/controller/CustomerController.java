package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.CustomerDTO;
import com.example.officebuilding.service.customer.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api", produces = "application/json")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @PostMapping("/customer/create")
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){
        logger.info("Body- {}", customerDTO);
        return new ResponseEntity<>(customerService.save(customerDTO),HttpStatus.OK);
    }

    @GetMapping("/user/customer/getAll")
    public ResponseEntity<List<CustomerDTO>> getAllCustomer(){
        return new ResponseEntity<>(customerService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/user/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id){
        Optional<CustomerDTO> customer = customerService.findById(id);
        return customer.map(customerDTO -> {
            return new ResponseEntity<>(customerDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/customer/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO){
        logger.info("Body- {}", customerDTO);

        Optional<CustomerDTO> customerDTOOptional = customerService.findById(id);
        return customerDTOOptional.map(customerDTO1 -> {
            customerDTO.setId(customerDTO1.getId());
            CustomerDTO updateCustomer = customerService.update(customerDTO);
            return new ResponseEntity<>(updateCustomer,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/customer/delete/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable Integer id){
        Optional<CustomerDTO> customerDTOOptional = customerService.findById(id);
        return customerDTOOptional.map(customerDTO -> {
            customerService.remove(id);
            return new ResponseEntity<>(customerDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
