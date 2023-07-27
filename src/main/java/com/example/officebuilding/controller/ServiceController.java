package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.ServiceDTO;
import com.example.officebuilding.service.service.IServiceService;
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
public class ServiceController {
    @Autowired
    private IServiceService serviceService;
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
    @PostMapping("/service/create")
    public ResponseEntity<ServiceDTO> createNewService(@RequestBody ServiceDTO serviceDTO){
        logger.info("Body- {}", serviceDTO);
        return new ResponseEntity<>(serviceService.save(serviceDTO),HttpStatus.OK);
    }

    @GetMapping("/user/service/getAll")
    public ResponseEntity<List<ServiceDTO>> getAllService(){
        return new ResponseEntity<>(serviceService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/user/service/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Integer id){
        Optional<ServiceDTO> service = serviceService.findById(id);
        return service.map(serviceDTO -> {
            return new ResponseEntity<>(serviceDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/service/update/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable Integer id, @RequestBody ServiceDTO serviceDTO){
        logger.info("Body- {}", serviceDTO);

        Optional<ServiceDTO> serviceDTOOptional = serviceService.findById(id);
        return serviceDTOOptional.map(serviceDTO1 -> {
            serviceDTO.setId(serviceDTO1.getId());
            ServiceDTO updateService = serviceService.update(serviceDTO);
            return new ResponseEntity<>(updateService,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/service/delete/{id}")
    public ResponseEntity<ServiceDTO> deleteService(@PathVariable Integer id){
        Optional<ServiceDTO> serviceDTOOptional = serviceService.findById(id);
        return serviceDTOOptional.map(serviceDTO -> {
            serviceService.remove(id);
            return new ResponseEntity<>(serviceDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
