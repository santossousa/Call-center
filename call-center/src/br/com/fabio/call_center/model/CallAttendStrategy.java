package br.com.fabio.call_center.model;

import java.util.Collection;

public interface CallAttendStrategy {
    Employee findEmployee(Collection<Employee> employeeList);

}
