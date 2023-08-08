package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.EquipmentDTO;
import com.example.officebuilding.dtos.RoomDTO;
import com.example.officebuilding.service.equipment.IEquipmentService;
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
public class EquipmentController {
    @Autowired
    private IEquipmentService equipmentService;
    private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);
    @PostMapping("/equipment/create")
    public ResponseEntity<EquipmentDTO> createNewEquipment(@RequestBody EquipmentDTO equipmentDTO){
        logger.info("Body- {}", equipmentDTO);
        return new ResponseEntity<>(equipmentService.save(equipmentDTO),HttpStatus.OK);
    }

    @GetMapping("/user/equipment/getAll")
    public ResponseEntity<List<EquipmentDTO>> getAllEquipment(){
        return new ResponseEntity<>(equipmentService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/user/equipment/{id}")
    public ResponseEntity<EquipmentDTO> getEquipmentById(@PathVariable Integer id){
        Optional<EquipmentDTO> equipment = equipmentService.findById(id);
        return equipment.map(equipmentDTO -> {
            return new ResponseEntity<>(equipmentDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/equipment/update/{id}")
    public ResponseEntity<EquipmentDTO> equipmentFloor(@PathVariable Integer id, @RequestBody EquipmentDTO equipmentDTO){
        logger.info("Body- {}", equipmentDTO);

        Optional<EquipmentDTO> equipmentDTOOptional = equipmentService.findById(id);
        return equipmentDTOOptional.map(equipmentDTO1 -> {
            equipmentDTO.setId(equipmentDTO1.getId());
            EquipmentDTO updateEquipment = equipmentService.update(equipmentDTO);
            return new ResponseEntity<>(updateEquipment,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/equipment/delete/{id}")
    public ResponseEntity<EquipmentDTO> deleteEquipment(@PathVariable Integer id){
        Optional<EquipmentDTO> equipmentDTOOptional = equipmentService.findById(id);
        return equipmentDTOOptional.map(equipmentDTO -> {
            equipmentService.remove(id);
            return new ResponseEntity<>(equipmentDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/equipment/getAllByFloorId/{id}")
    public ResponseEntity<List<EquipmentDTO>> findAllEquipmentsByFloorId(@PathVariable Integer id){
        List<EquipmentDTO> equipDTOs = equipmentService.findAllEquipmentsByFloorId(id);
        return new ResponseEntity<>(equipDTOs,HttpStatus.OK);


    }
}
