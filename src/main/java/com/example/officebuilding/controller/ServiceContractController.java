package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.service.serviceContract.IServiceContractService;
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
public class ServiceContractController {
    @Autowired
    private IServiceContractService serviceContractService;
    private static final Logger logger = LoggerFactory.getLogger(ServiceContractController.class);
    @PostMapping("/serviceContract/create")
    public ResponseEntity<ServiceContractDTO> createNewServiceContract(@RequestBody ServiceContractDTO serviceContractDTO){
        logger.info("Body- {}", serviceContractDTO);
        return new ResponseEntity<>(serviceContractService.save(serviceContractDTO),HttpStatus.OK);
    }

    @GetMapping("/user/serviceContract/getAll")
    public ResponseEntity<List<ServiceContractDTO>> getAllServiceContract(){
        return new ResponseEntity<>(serviceContractService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/user/serviceContract/{id}")
    public ResponseEntity<ServiceContractDTO> getServiceContractById(@PathVariable Integer id){
        Optional<ServiceContractDTO> serviceContract = serviceContractService.findById(id);
        return serviceContract.map(serviceContractDTO -> {
            return new ResponseEntity<>(serviceContractDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/serviceContract/update/{id}")
    public ResponseEntity<ServiceContractDTO> serviceContractFloor(@PathVariable Integer id, @RequestBody ServiceContractDTO serviceContractDTO){
        logger.info("Body- {}", serviceContractDTO);

        Optional<ServiceContractDTO> serviceContractDTOOptional = serviceContractService.findById(id);
        return serviceContractDTOOptional.map(serviceContractDTO1 -> {
            serviceContractDTO.setId(serviceContractDTO1.getId());
            ServiceContractDTO updateServiceContract = serviceContractService.update(serviceContractDTO);
            return new ResponseEntity<>(updateServiceContract,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/serviceContract/delete/{id}")
    public ResponseEntity<ServiceContractDTO> deleteServiceContract(@PathVariable Integer id){
        Optional<ServiceContractDTO> serviceContractDTOOptional = serviceContractService.findById(id);
        return serviceContractDTOOptional.map(serviceContractDTO -> {
            serviceContractService.remove(id);
            return new ResponseEntity<>(serviceContractDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
