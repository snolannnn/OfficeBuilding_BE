package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.HotelDTO;
import com.example.officebuilding.dtos.InfoDTO;
import com.example.officebuilding.service.info.IInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api", produces = "application/json")
public class InfoController {
    @Autowired
    private IInfoService infoService;

    @PostMapping("/user/info/create")
    public ResponseEntity<InfoDTO> createNewInfo(@RequestBody InfoDTO infoDTO){
        return new ResponseEntity<>(infoService.save(infoDTO), HttpStatus.OK);
    }

    @GetMapping("/user/info/userId/{id}")
    public ResponseEntity<List<InfoDTO>> getInfoByUserId(@PathVariable Integer id){
        List<InfoDTO> infoDTOS = infoService.getInfosByUserId(id);
        return new ResponseEntity<>(infoDTOS,HttpStatus.OK);
    }
}
