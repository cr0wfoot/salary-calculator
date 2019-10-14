package model;

import controller.Config;
import controller.ExceptionHandler;
import model.distribution.Distribution;
import model.position.Employee;
import model.position.Employee.Position;
import model.position.Manager;
import view.Output;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Company {

    private static volatile Company instance;
    private Map<String, Employee> employees = new HashMap<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat(Config.DATE_FORMAT);
    private String payroll = null;

    private static final String COMPANY_BUDGET = "Company budget: ";

    public enum Department {

        HR,
        IT,
        SALES;

        private List<Employee> employees = new LinkedList<>();

        public List<Employee> getEmployees() {
            return employees;
        }

    }

    public static Company getInstance() {
        if (instance == null) {
            synchronized (Company.class) {
                if (instance == null) instance = new Company();
            }
        }
        return instance;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String format) {
        this.dateFormat = new SimpleDateFormat(format);
    }

    public String getPayroll() {
        return payroll;
    }

    public List<Employee> getEmployees() {
        List<Employee> emp = new LinkedList<>();
        emp.addAll(this.employees.values());
        return emp;
    }

    public Department[] getDepartments() {
        return Department.values();
    }

    public void addEmployee(Employee e) {
        if (e != null) {
            e.getDepartment().employees.add(e);
            employees.put(e.getName(), e);
        }
    }

    public Employee findEmployee(String name) {
        return this.employees.get(name);
    }

    public Manager findManager(String name, Department dept) {
        Employee e;
        if ((e = employees.get(name)).getType() == Position.MANAGER) {
            return (Manager) e;
        }
        for (Employee emp : dept.employees) {
            if (emp.getName().equals(name) && emp.getType() == Position.MANAGER) {
                return (Manager) emp;
            }
        }
        return null;
    }

    public void clear() {
        employees = null;
        for (Department d : Department.values()) {
            d.employees = null;
        }
    }

    public void delEmployee(Employee e) {
        employees.remove(e.getName());
        e.getDepartment().employees.remove(e);
        e.del();
    }

    public List<Employee> getFilteredEmployees(Department d, Filter<Employee> f) {
        List<Employee> filtered = new LinkedList<>();
        for (Employee e : d.employees) {
            if (f.apply(e)) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    public Comparator<Employee> compareByBirthDate() {
        return (e1, e2) -> {
            try {
                return dateFormat.parse(e1.getDateBirth()).compareTo(dateFormat.parse(e2.getDateBirth()));
            } catch (ParseException ex) {
                ExceptionHandler.getInstance().catchException(ex);
                return -1;
            }
        };
    }

    public Comparator<Employee> compareByCompanyDate() {
        return (e1, e2) -> {
            try {
                return dateFormat.parse(e1.getDateCompany()).compareTo(dateFormat.parse(e2.getDateCompany()));
            } catch (ParseException ex) {
                ExceptionHandler.getInstance().catchException(ex);
                return 0;
            }
        };
    }

    public void calculateSalary(Distribution... distribution)
            throws NumberFormatException, ClassCastException, ParseException {

        if (distribution.length == 1) {
            payroll = COMPANY_BUDGET + distribution[0].getBudget() +
                    System.lineSeparator() + distribution[0].distributeSalary(getEmployees());
        } else if (distribution.length == Department.values().length) {
            Department[] depts = Department.values();
            int i = 0;
            for (Distribution d : distribution) {
                payroll += depts[i].name() + Output.COUNTING + d.getBudget() +
                        System.lineSeparator() + d.distributeSalary(depts[i++].employees) +
                        System.lineSeparator() +
                        System.lineSeparator();
            }
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public String toString() {
        String result = "";
        for (Department d : Department.values()) {
            List<Employee> l = d.getEmployees();
            for (Employee e : l) {
                result += e + System.lineSeparator();
            }
            result += System.lineSeparator();
        }
        return result;
    }
}
