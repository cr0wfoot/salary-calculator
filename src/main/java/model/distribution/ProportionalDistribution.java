package model.distribution;

import model.position.Employee;

import java.text.ParseException;
import java.util.List;

public class ProportionalDistribution extends Distribution {

    public ProportionalDistribution(double budget) {
        super(budget);
    }

    @Override
    public void recalculate(int birthdayCount, List<Employee> employees) throws ParseException {
        for (Employee e : employees) {
            e.setSalary(e.getSalary() +
                    (checkForBirthDay(e.getDateBirth()) == 0 ?
                            e.getSalary() - PREMIUM_BIRTHDAY / totalSalary :
                            e.getSalary() / totalSalary) * super.budget);
        }
    }
}
