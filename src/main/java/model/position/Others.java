package model.position;

import model.Company.Department;
import view.Output;

public class Others extends Employee {

    private static final long serialVersionUID = 1L;
    private String description;

    public Others(Position pos, String desc) {
        super(pos);
        this.description = desc;
    }

    public Others(String name, String dateBirth, String dateCompany, Position type, Department department, String description) {
        super(name, dateBirth, dateCompany, type, department);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void del() {

    }

    @Override
    public String toString() {
        return getName() + Output.WORDS_SEPARATOR + getDescription()
                + Output.WORDS_SEPARATOR + getSalary();
    }
}
