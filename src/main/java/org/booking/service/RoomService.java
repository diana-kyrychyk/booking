package org.booking.service;

import org.booking.model.dto.RoomDTO;

public interface RoomService {

    RoomDTO findById(Integer id);

}
