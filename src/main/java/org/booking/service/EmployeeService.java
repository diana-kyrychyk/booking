package org.booking.service;

import org.booking.model.dto.EmployeeDTO;

public interface EmployeeService {

    EmployeeDTO findById(Integer id);
}
