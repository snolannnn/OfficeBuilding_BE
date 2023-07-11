package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.HotelDTO;
import com.example.officebuilding.service.hotel.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api", produces = "application/json")
public class HotelController {
    @Autowired
    private IHotelService hotelService;

    @PostMapping("/hotel/create")
    public ResponseEntity<HotelDTO> createNewHotel(@RequestBody HotelDTO hotelDTO){
        return new ResponseEntity<>(hotelService.save(hotelDTO), HttpStatus.OK);
    }

    @GetMapping("/user/hotel/getAll")
    public ResponseEntity<List<HotelDTO>> getAllHotel(){
        return new ResponseEntity<>(hotelService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/user/hotel/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Integer id){
        Optional<HotelDTO> hotel = hotelService.findById(id);
        return hotel.map(hotelDTO -> {
            return new ResponseEntity<>(hotelDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/hotel/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Integer id, @RequestBody HotelDTO hotelDTO){
        Optional<HotelDTO> hotelDTOOptional = hotelService.findById(id);
        return hotelDTOOptional.map(hotelDTO1 -> {
            hotelDTO.setId(hotelDTO1.getId());
            HotelDTO updateHotel = hotelService.update(hotelDTO);
            return new ResponseEntity<>(updateHotel,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/hotel/{id}")
    public ResponseEntity<HotelDTO> deleteHotel(@PathVariable Integer id){
        Optional<HotelDTO> hotelDTOOptional = hotelService.findById(id);
        return hotelDTOOptional.map(hotelDTO -> {
            hotelService.remove(id);
            return new ResponseEntity<>(hotelDTO,HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
