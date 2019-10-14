package model.position;

import model.Company.Department;
import view.Output;

public class Specialist extends Employee {

    private static final long serialVersionUID = 1L;
    private Manager manager;

    public Specialist(Manager manager) {
        super(Position.SPECIALIST);
        this.manager = manager;
        if (this.manager != null) {
            this.manager.getSpecialists().add(this);
        }
    }

    public Specialist(String name, String dateBirth, String dateCompany, Department department, Manager manager) {
        super(name, dateBirth, dateCompany, Position.SPECIALIST, department);
        if (manager != null && manager.getDepartment() == department) {
            this.manager = manager;
            this.manager.getSpecialists().add(this);
        }
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public boolean changeManager(Manager manager) {
        if (manager == null || this.department != manager.getDepartment()) {
            return false;
        }
        if (this.manager != null) {
            this.manager.getSpecialists().remove(this);
        }
        this.manager = manager;
        this.manager.getSpecialists().add(this);
        return true;
    }

    @Override
    public void del() {
        if (this.manager != null) {
            this.manager.getSpecialists().remove(this);
        }
        this.manager = null;
    }

    @Override
    public String toString() {
        return getName() + Output.WORDS_SEPARATOR + getSalary() + Output.BR_L_ARRAY +
                (manager != null ? manager.getName() : "") + Output.BR_R_ARRAY;
    }

}
