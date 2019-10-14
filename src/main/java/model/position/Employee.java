package model.position;

import model.Company;
import model.Company.Department;

import java.io.Serializable;

public abstract class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String dateBirth;
    private String dateCompany;
    private double salary;
    protected Position position;
    protected Department department;

    protected Employee(Position pos) {
        this.position = pos;
    }

    protected Employee(String name, String dateBirth, String dateCompany, Position type, Department department) {
        this.name = name;
        this.dateBirth = dateBirth;
        this.dateCompany = dateCompany;
        this.position = type;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getDateCompany() {
        return dateCompany;
    }

    public void setDateCompany(String dateCompany) {
        this.dateCompany = dateCompany;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Position getType() {
        return position;
    }

    public void setType(Position type) {
        this.position = type;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void changePosition(Employee e) {
        e.copy(this);
        Company.getInstance().delEmployee(this);
        Company.getInstance().addEmployee(e);
    }

    private void copy(Employee e) {
        this.name = e.name;
        this.dateBirth = e.dateBirth;
        this.dateCompany = e.dateCompany;
        this.department = e.department;
    }

    public abstract void del() throws ClassCastException;

    public enum Position {

        MANAGER(8000),
        SPECIALIST(5000),
        DIRECTOR(15000),
        SECRETARY(3000);

        private double salary;

        Position(double salary) {
            this.salary = salary;
        }

        public double getSalary() {
            return salary;
        }

    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        Employee e = (Employee) o;
        return name.equals(e.getName()) && dateBirth.equals(e.getDateBirth()) &&
                dateCompany.equals(e.getDateCompany()) && position == e.getType() &&
                department == e.getDepartment();
    }
}
