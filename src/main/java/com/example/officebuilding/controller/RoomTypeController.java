package com.example.officebuilding.controller;

import com.example.officebuilding.dao.RoomTypeDAO;
import com.example.officebuilding.dtos.CompanyEmployeeDTO;
import com.example.officebuilding.dtos.RoomTypeDTO;
import com.example.officebuilding.entities.HotelEntity;
import com.example.officebuilding.entities.RoomTypeEntity;
import com.example.officebuilding.service.room_type.IRoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api",produces = "application/json")
public class RoomTypeController {
    @Autowired
    private IRoomTypeService roomTypeService;

    @Autowired
    private RoomTypeDAO roomTypeDAO;
    private static final Logger logger = LoggerFactory.getLogger(RoomTypeController.class);

    @PostMapping("/roomType/create")
    public ResponseEntity<RoomTypeDTO> createNewRoomType(@RequestBody RoomTypeDTO roomTypeDTO){
        return new ResponseEntity<>(roomTypeService.save(roomTypeDTO), HttpStatus.OK);
    }

    @PostMapping("/roomType/hotelID={id}")
    public void insertRoomTypeOfHotel(@PathVariable Integer id, @RequestBody RoomTypeDTO roomTypeDTO){
        roomTypeDAO.insertRoomTypeByHotelId(id,roomTypeDTO);
    }

    @GetMapping("/user/roomType/hotelID={id}")
    public ResponseEntity<List<RoomTypeDTO>> findAllRoomTypeOfHotel(@PathVariable Integer id){
        return new ResponseEntity<>(roomTypeService.findAllRoomTypeOfHotel(id),HttpStatus.OK);
    }

    @GetMapping("/user/roomType/{id}")
    public ResponseEntity<RoomTypeDTO> getRoomTypeById(@PathVariable Integer id){
        Optional<RoomTypeDTO> roomtype = roomTypeService.findById(id);
        return roomtype.map(roomTypeDTO -> {
            return new ResponseEntity<>(roomTypeDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/roomType/update/{id}")
    public ResponseEntity<RoomTypeDTO> updateRoomType(@PathVariable Integer id, @RequestBody RoomTypeDTO roomTypeDTO){
        Optional<RoomTypeDTO> roomTypeDTOOptional = roomTypeService.findById(id);
        logger.error("Unauthorized error. Message - {}", roomTypeDTOOptional);
        logger.error("Unauthorized error. Message - {}", roomTypeDTO);

        return roomTypeDTOOptional.map(roomTypeDTO1 -> {
            roomTypeDTO.setId(roomTypeDTO1.getId());
            RoomTypeDTO updateRoomType = roomTypeService.update(roomTypeDTO);
            return new ResponseEntity<>(updateRoomType,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/roomType/delete/{id}")
    public ResponseEntity<RoomTypeDTO> deleteRoomType(@PathVariable Integer id){
        Optional<RoomTypeDTO> roomTypeDTOOptional = roomTypeService.findById(id);
        return roomTypeDTOOptional.map(roomTypeDTO -> {
            roomTypeService.remove(id);
            return new ResponseEntity<>(roomTypeDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
