package br.com.fabio.call_center.model;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultCallAttendStrategy implements CallAttendStrategy  {
	private static final Logger logger = LoggerFactory.getLogger(DefaultCallAttendStrategy.class);
	 @Override
    public Employee findEmployee(Collection<Employee> employeeList) {
        Validate.notNull(employeeList);
        List<Employee> availableEmployees = employeeList.stream().filter(e -> e.getEmployeeStatus() == EmployeeStatus.AVAILABLE).collect(Collectors.toList());
        logger.info("Available operators: " + availableEmployees.size());
        Optional<Employee> employee = availableEmployees.stream().filter(e -> e.getEmployeeType() == EmployeeType.ATTENDANT).findAny();
        if (!employee.isPresent()) {
            logger.info("Nenhum atendente  disponível encontrado");
            employee = availableEmployees.stream().filter(e -> e.getEmployeeType() == EmployeeType.MANAGER).findAny();
            if (!employee.isPresent()) {
                logger.info("Nenhum gerente  disponível encontrado");
                employee = availableEmployees.stream().filter(e -> e.getEmployeeType() == EmployeeType.DIRECTOR).findAny();
                if (!employee.isPresent()) {
                    logger.info("Nenhum diretor  disponível encontrado");
                    return null;
                }
            }
        }
        logger.info(employee.get().getEmployeeType() + " encontrado");
        return employee.get();
    }

}
