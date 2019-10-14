package model.position;

import model.Company;
import model.Company.Department;
import view.Output;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Manager extends Employee {

    private static final long serialVersionUID = 1L;
    private List<Employee> specialists;

    public Manager() {
        super(Position.MANAGER);
        this.specialists = new LinkedList<>();
    }

    public Manager(String name, String dateBirth, String dateCompany, Department department) {
        super(name, dateBirth, dateCompany, Position.MANAGER, department);
        this.specialists = new LinkedList<>();
    }

    public List<Employee> getSpecialists() {
        return this.specialists;
    }

    @Override
    public void del() throws ClassCastException {
        List<Employee> managers = Company.getInstance().getFilteredEmployees(this.department, e -> Position.MANAGER == e.getType());
        if (managers.isEmpty()) {
            for (Employee e : this.specialists) {
                ((Specialist) e).setManager(null);
            }
        } else {
            Iterator<Employee> i = managers.iterator();
            for (Employee e : this.specialists) {
                if (!i.hasNext()) {
                    i = managers.iterator();
                }
                ((Specialist) e).setManager((Manager) i.next());
                ((Specialist) e).getManager().getSpecialists().add(e);
            }
        }
        this.specialists = null;
    }

    @Override
    public String toString() {
        String tmp = getName() + Output.WORDS_SEPARATOR + getSalary() + Output.BR_L_OBJECT;
        if (specialists != null) {
            for (Employee e : specialists) {
                tmp += e.getName() + Output.WORDS_SEPARATOR;
            }
        }
        return tmp + Output.BR_R_OBJECT;
    }
}
