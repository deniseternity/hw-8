package pro.Sky.Skypro.service;

import org.springframework.stereotype.Service;
import pro.Sky.Skypro.Exceptions.EmployeeAlreadyAddedException;
import pro.Sky.Skypro.Exceptions.EmployeeNotFoundException;
import pro.Sky.Skypro.Exceptions.EmployeeStoragelsFullException;
import pro.Sky.Skypro.model.Employee;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {

    private static final int MAX_COUNT = 5;
    private final Map<String, Employee> employees = new HashMap<>(MAX_COUNT);

    public void add(String firstName, String lastName) throws EmployeeAlreadyAddedException {
        if (employees.size() >= MAX_COUNT) {
            throw new EmployeeStoragelsFullException();
        }
        Employee employee = new Employee(firstName, lastName);
        var key = makeKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.put(key, employee);
    }

    public void delete(String firstName, String lastName) {
        var key = makeKey(firstName, lastName);
        var removed = employees.remove(key);
        if (removed == null) {
            throw new EmployeeNotFoundException();
        }
    }

    public Employee find(String firstName, String lastName) {
        var key = makeKey(firstName, lastName);
        if (employees.containsKey(key)) {
            return employees.get(key);
        }
        throw new EmployeeNotFoundException();
    }

    public Collection<Employee> getAll() {
        return Collections.unmodifiableCollection(employees.values());
    }

    private static String makeKey(String firstName, String lastName) {
        return (firstName + "_" + lastName).toLowerCase();
    }
}