package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.FloorDTO;
import com.example.officebuilding.service.floor.IFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api", produces = "application/json")
public class FloorController {
    @Autowired
    private IFloorService floorService;
    private static final Logger logger = LoggerFactory.getLogger(FloorController.class);
    @PostMapping("/floor/create")
    public ResponseEntity<FloorDTO> createNewFloor(@RequestBody FloorDTO floorDTO){
        logger.info("Body- {}", floorDTO);
        return new ResponseEntity<>(floorService.save(floorDTO),HttpStatus.OK);
    }

    @GetMapping("/user/floor/getAll")
    public ResponseEntity<List<FloorDTO>> getAllFloor(){
        return new ResponseEntity<>(floorService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/user/floor/{id}")
    public ResponseEntity<FloorDTO> getFloorById(@PathVariable Integer id){
        Optional<FloorDTO> floor = floorService.findById(id);
        return floor.map(floorDTO -> {
            return new ResponseEntity<>(floorDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/floor/update/{id}")
    public ResponseEntity<FloorDTO> updateFloor(@PathVariable Integer id, @RequestBody FloorDTO floorDTO){
        logger.info("Body- {}", floorDTO);

        Optional<FloorDTO> floorDTOOptional = floorService.findById(id);
        return floorDTOOptional.map(floorDTO1 -> {
            floorDTO.setId(floorDTO1.getId());
            FloorDTO updateFloor = floorService.update(floorDTO);
            return new ResponseEntity<>(updateFloor,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/floor/delete/{id}")
    public ResponseEntity<FloorDTO> deleteFloor(@PathVariable Integer id){
        Optional<FloorDTO> floorDTOOptional = floorService.findById(id);
        return floorDTOOptional.map(floorDTO -> {
            floorService.remove(id);
            return new ResponseEntity<>(floorDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
