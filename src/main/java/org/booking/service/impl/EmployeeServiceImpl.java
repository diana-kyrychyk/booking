package org.booking.service.impl;

import org.booking.model.Employee;
import org.booking.model.dto.EmployeeDTO;
import org.booking.repository.EmployeeRepository;
import org.booking.service.EmployeeService;
import org.springframework.stereotype.Service;

import org.booking.exception.EntityNotFoundException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO findById(Integer id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.map(employee -> toEmployeeDTO(employee)).orElseThrow(EntityNotFoundException::new);
    }

    private EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirst_name(employee.getFirst_name());
        employeeDTO.setLast_name(employeeDTO.getLast_name());
        return employeeDTO;
    }
}
