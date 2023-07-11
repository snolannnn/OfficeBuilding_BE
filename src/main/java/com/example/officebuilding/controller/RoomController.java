package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.RoomDTO;
import com.example.officebuilding.dtos.RoomTypeDTO;
import com.example.officebuilding.service.room.IRoomService;
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
@RequestMapping(value = "/api",produces = "application/json")
public class RoomController {
    @Autowired
    private IRoomService roomService;

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @PostMapping("/room/create")
    public ResponseEntity<RoomDTO> createNewRoom(@RequestBody RoomDTO roomDTO){
        logger.error("Unauthorized error. Message - {}", roomDTO);

        return new ResponseEntity<>(roomService.save(roomDTO), HttpStatus.OK);
    }

    @GetMapping("/room/all")
    public ResponseEntity<List<RoomDTO>> getAllRoom(){
        return new ResponseEntity<>(roomService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/room/hotelId/{id}")
    public ResponseEntity<List<RoomDTO>> getRoomsByHotelId(@PathVariable Integer id) {
        List<RoomDTO> roomDTOs = roomService.getRoomsByHotelId(id);
        return new ResponseEntity<>(roomDTOs, HttpStatus.OK);
    }

    @GetMapping("user/room/hotel/{hotelId}/roomtype/{roomTypeId}")
    public ResponseEntity<List<RoomDTO>> getAllRoomsByHotelAndRoomType(
            @PathVariable Integer hotelId,
            @PathVariable Integer roomTypeId
    ){
        List<RoomDTO> rooms = roomService.getAllRoomsByHotelAndRoomType(hotelId, roomTypeId);
        return new ResponseEntity<>(rooms,HttpStatus.OK);
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Integer id) {
        Optional<RoomDTO> room = roomService.findById(id);
        return room.map(roomDTO -> {
            return new ResponseEntity<>(roomDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("user/room/update/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Integer id, @RequestBody RoomDTO roomDTO){
        Optional<RoomDTO> roomDTOOptional = roomService.findById(id);
        return roomDTOOptional.map(roomDTO1 -> {
            roomDTO.setId(roomDTO1.getId());
            RoomDTO updateRoom = roomService.update(roomDTO);
            return new ResponseEntity<>(updateRoom,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/room/delete/{id}")
    public ResponseEntity<RoomDTO> deleteRoom(@PathVariable Integer id){
        Optional<RoomDTO> roomDTOOptional=roomService.findById(id);
        return roomDTOOptional.map(roomDTO -> {
            roomService.remove(id);
            return new ResponseEntity<>(roomDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
