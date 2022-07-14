package org.booking.repository;


import org.booking.model.Booking;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>, JpaSpecificationExecutor<Booking> {

    Optional<Booking> findById(int id);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.room.id = ?1 " +
            "AND b.dateStart BETWEEN ?2 AND ?3 OR b.dateEnd BETWEEN ?2 AND ?3")
    List<Booking> findByRoomIdAndDateStartAndDateEnd(Integer roomId, LocalDateTime dateStart, LocalDateTime dateEnd);


}
