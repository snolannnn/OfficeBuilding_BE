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
    @PostMapping("/user/rental/create")
    public ResponseEntity<RentalDTO> createNewRental(@RequestBody RentalDTO rentalDTO){
        logger.info("Body- {} la id cua room", rentalDTO.getRoomId());
        List<RentalDTO> rentals = rentalService.findAllByRoomIdAndStatus(rentalDTO.getRoomId(),1);
        logger.info("length theo id Body- {} ", rentals.size());
        if (rentals.size() > 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(rentalService.createRentalWithRoomStatusChange(rentalDTO,1),HttpStatus.OK);
        }
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

    @PutMapping("/user/rental/update/{id}")
    public ResponseEntity<RentalDTO> rentalFloor(@PathVariable Integer id, @RequestBody RentalDTO rentalDTO){
        logger.info("Body- {}", rentalDTO);

        Optional<RentalDTO> rentalDTOOptional = rentalService.findById(id);
        return rentalDTOOptional.map(rentalDTO1 -> {
            rentalDTO.setId(rentalDTO1.getId());
            RentalDTO updateRental = rentalService.update(rentalDTO);
            return new ResponseEntity<>(updateRental,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/user/rental/delete/{id}")
    public ResponseEntity<RentalDTO> deleteRental(@PathVariable Integer id){
        Optional<RentalDTO> rentalDTOOptional = rentalService.findById(id);
        return rentalDTOOptional.map(rentalDTO -> {
            rentalService.remove(id);
            return new ResponseEntity<>(rentalDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/user/rental/cancel/{id}")
    public ResponseEntity<RentalDTO> cancelRental(@PathVariable Integer id,@RequestParam String dateEnd) {
        logger.info("Body- {}", id);
        rentalService.cancelRental(id,dateEnd);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("user/rental/findByStatus/{status}")
    public ResponseEntity<List<RentalDTO>> findAllRentalByStatus(@PathVariable Integer status){
        List<RentalDTO> rentals = rentalService.findAllByStatus(status);
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("user/rental/hide")
    public ResponseEntity<List<RentalDTO>> findRentalsWithinDateRange(
            @RequestParam Integer month,
            @RequestParam Integer year
           ) {
        logger.info("Body- {}", month);
        List<RentalDTO> rentals = rentalService.findRentalsWithinDateRange(month,year);
        return ResponseEntity.ok(rentals);
    }


}
