package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.RentalDTO;
import com.example.officebuilding.service.rental.IRentalService;
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
public class RentalController {
    @Autowired
    private IRentalService rentalService;
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    @PostMapping("/rental/create")
    public ResponseEntity<RentalDTO> createNewRental(@RequestBody RentalDTO rentalDTO){
        logger.info("Body- {}", rentalDTO);
        return new ResponseEntity<>(rentalService.createRentalWithRoomStatusChange(rentalDTO,1),HttpStatus.OK);
    }

    @GetMapping("/user/rental/getAll")
    public ResponseEntity<List<RentalDTO>> getAllRental(){
        return new ResponseEntity<>(rentalService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/user/rental/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id){
        Optional<RentalDTO> rental = rentalService.findById(id);
        return rental.map(rentalDTO -> {
            return new ResponseEntity<>(rentalDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/rental/update/{id}")
    public ResponseEntity<RentalDTO> rentalFloor(@PathVariable Integer id, @RequestBody RentalDTO rentalDTO){
        logger.info("Body- {}", rentalDTO);

        Optional<RentalDTO> rentalDTOOptional = rentalService.findById(id);
        return rentalDTOOptional.map(rentalDTO1 -> {
            rentalDTO.setId(rentalDTO1.getId());
            RentalDTO updateRental = rentalService.update(rentalDTO);
            return new ResponseEntity<>(updateRental,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/rental/delete/{id}")
    public ResponseEntity<RentalDTO> deleteRental(@PathVariable Integer id){
        Optional<RentalDTO> rentalDTOOptional = rentalService.findById(id);
        return rentalDTOOptional.map(rentalDTO -> {
            rentalService.remove(id);
            return new ResponseEntity<>(rentalDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/rental/cancel/{id}")
    public ResponseEntity<RentalDTO> cancelRental(@PathVariable Integer id) {
        logger.info("Body- {}", id);
        rentalService.cancelRental(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
