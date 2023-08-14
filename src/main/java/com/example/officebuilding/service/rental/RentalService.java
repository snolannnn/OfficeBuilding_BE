package com.example.officebuilding.service.rental;

import com.example.officebuilding.controller.RentalController;
import com.example.officebuilding.dtos.RentalDTO;
import com.example.officebuilding.entities.RentalEntity;
import com.example.officebuilding.repository.IRentalRepository;
import com.example.officebuilding.service.rental.IRentalService;
import com.example.officebuilding.service.room.RoomService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RentalService implements IRentalService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IRentalRepository rentalRepository;

    @Autowired
    private RoomService roomService;
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    @Override
    public List<RentalDTO> findAll(){
        List<RentalEntity> rentalEntities = rentalRepository.findAll();
        List<RentalDTO> rentalDTOS = rentalEntities.stream().map(rentalEntity -> modelMapper.map(rentalEntity, RentalDTO.class)).collect(Collectors.toList());
        return rentalDTOS;
    }

    @Override
    public Optional<RentalDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<RentalEntity> rentalEntity = rentalRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        return rentalEntity.map(rentalEntity1 -> modelMapper.map(rentalEntity1, RentalDTO.class));
    }
    @Override
    public RentalDTO save(RentalDTO rentalDTO){
        RentalEntity rentalEntity = modelMapper.map(rentalDTO, RentalEntity.class);
        RentalEntity updateRentalEntity = rentalRepository.save(rentalEntity);
        return modelMapper.map(updateRentalEntity,RentalDTO.class);
    }

    @Override
    public RentalDTO update(RentalDTO rentalDTO){
        RentalEntity rentalEntity = modelMapper.map(rentalDTO,RentalEntity.class);
        RentalEntity updatedRentalEntity = rentalRepository.save(rentalEntity);
        return modelMapper.map(updatedRentalEntity,RentalDTO.class);
    }
    @Override
    public void remove(Integer id){
        rentalRepository.deleteById(id);
    }

    @Override
    public RentalDTO createRentalWithRoomStatusChange(RentalDTO rentalDTO, Integer newRoomStatus) {
        // Create a new rental entity
        RentalEntity rentalEntity = modelMapper.map(rentalDTO, RentalEntity.class);
        RentalEntity savedRentalEntity = rentalRepository.save(rentalEntity);

        // Update room status using RoomService
        roomService.updateRoomStatus(rentalDTO.getRoomId(), newRoomStatus);

        return modelMapper.map(savedRentalEntity, RentalDTO.class);
    }
    @Override
    public void cancelRental(Integer rentalId) {
        Optional<RentalEntity> rentalEntityOptional = rentalRepository.findById(rentalId);
        rentalEntityOptional.ifPresent(rentalEntity -> {
            // Update rental status
            rentalEntity.setReStatus(0);
            rentalRepository.save(rentalEntity);

            // Update room status using RoomService
            roomService.updateRoomStatus(rentalEntity.getRoom().getId(), 0); // Assuming 0 represents available status
        });
    }

    @Override
    public List<RentalDTO> findAllByStatus(Integer reStatus) {
        List<RentalEntity> rentalEntities = rentalRepository.findByReStatus(reStatus);
        return rentalEntities.stream()
                .map(rentalEntity -> modelMapper.map(rentalEntity, RentalDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<RentalDTO> findRentalsWithinDateRange(Integer month,Integer year) {

        List<RentalEntity> rentalEntities = rentalRepository.findAll();

        List<RentalDTO> filteredRentals = new ArrayList<>();
        for (RentalEntity rentalEntity : rentalEntities){

            Integer yearBegin = Integer.parseInt(rentalEntity.getReDateBegin().substring(0, 4));
            Integer yearEnd = Integer.parseInt(rentalEntity.getReDateEnd().substring(0, 4));
            Integer monthBegin = Integer.parseInt(rentalEntity.getReDateBegin().substring(5, 7));
            Integer monthEnd = Integer.parseInt(rentalEntity.getReDateEnd().substring(5, 7));

            logger.info("Begin-------------------------- {}",yearBegin);
            logger.info("year {}",year);
            logger.info("yearBegin {}",yearBegin);
            logger.info("yearEnd {}",yearEnd);

            if (year - yearBegin ==0 && year - yearEnd == 0){
                logger.info("Body1- {}", monthBegin);
                if(month - monthBegin >=0 && monthEnd - month > 0){
                    RentalDTO rentalDTO = modelMapper.map(rentalEntity, RentalDTO.class);
                    filteredRentals.add(rentalDTO);
                }
            } else if (year - yearBegin == 0) {
                logger.info("Body2- {}", monthBegin);
                if(month - monthBegin >=0){
                    RentalDTO rentalDTO = modelMapper.map(rentalEntity, RentalDTO.class);
                    filteredRentals.add(rentalDTO);
                }
            } else if (year - yearEnd ==0) {
                logger.info("Body3- {}", monthEnd);
                if (monthEnd - month > 0){
                    RentalDTO rentalDTO = modelMapper.map(rentalEntity, RentalDTO.class);
                    filteredRentals.add(rentalDTO);
                }
            } else if(year - yearBegin >0 && yearEnd - year >0 ){
                logger.info("Body4- {}", monthBegin, monthEnd);
                RentalDTO rentalDTO = modelMapper.map(rentalEntity, RentalDTO.class);
                filteredRentals.add(rentalDTO);
            }
            logger.info("End----------------------------------------- {}",yearEnd);
        }
        return filteredRentals;
    }



}
