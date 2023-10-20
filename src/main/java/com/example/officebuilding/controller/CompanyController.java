package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.CompanyDTO;
import com.example.officebuilding.service.company.ICompanyService;
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
public class CompanyController {
    @Autowired
    private ICompanyService companyService;
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
    @PostMapping("/user/company/create")
    public ResponseEntity<CompanyDTO> createNewCompany(@RequestBody CompanyDTO companyDTO){
        logger.info("Body- {}", companyDTO);
        return new ResponseEntity<>(companyService.save(companyDTO),HttpStatus.OK);
    }

    @GetMapping("/user/company/getAll")
    public ResponseEntity<List<CompanyDTO>> getAllCompany(){
        return new ResponseEntity<>(companyService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/user/company/getAllByCusStatus/{status}")
    public ResponseEntity<List<CompanyDTO>> getAllCompanyByCusStatus(@PathVariable Integer status){
        return new ResponseEntity<>(companyService.findAllByCStatus(status),HttpStatus.OK);
    }

    @GetMapping("/user/company/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Integer id){
        Optional<CompanyDTO> company = companyService.findById(id);
        return company.map(companyDTO -> {
            return new ResponseEntity<>(companyDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/user/company/update/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Integer id, @RequestBody CompanyDTO companyDTO){
        logger.info("Body- {}", companyDTO);

        Optional<CompanyDTO> companyDTOOptional = companyService.findById(id);
        return companyDTOOptional.map(companyDTO1 -> {
            companyDTO.setId(companyDTO1.getId());
            CompanyDTO updateCompany = companyService.update(companyDTO);
            return new ResponseEntity<>(updateCompany,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/user/company/delete/{id}")
    public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable Integer id){
        Optional<CompanyDTO> companyDTOOptional = companyService.findById(id);
        return companyDTOOptional.map(companyDTO -> {
            companyService.remove(id);
            return new ResponseEntity<>(companyDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
