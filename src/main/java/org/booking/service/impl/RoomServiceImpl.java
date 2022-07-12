package org.booking.service.impl;

import org.booking.model.Room;
import org.booking.model.dto.RoomDTO;
import org.booking.repository.RoomRepository;
import org.booking.service.RoomService;
import org.springframework.stereotype.Service;

import org.booking.exception.EntityNotFoundException;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;

    @Override
    public RoomDTO findById(Integer id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        return roomOptional
                .map(room -> toRoomDto(room))
                .orElseThrow(EntityNotFoundException::new);
    }

    private RoomDTO toRoomDto(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        return roomDTO;
    }
}
