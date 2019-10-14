package model.distribution;

import controller.Config;
import model.position.Employee;
import model.position.Employee.Position;
import model.position.Manager;
import view.Output;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class Distribution {

    public static double PREMIUM_BIRTHDAY;
    public static double PREMIUM_SPECIALISTS;

    double budget;
    double totalSalary = 0;

    public Distribution(double budget) {
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }

    public String distributeSalary(List<Employee> employees)
            throws ParseException, ClassCastException, NumberFormatException {

        int birthdayCount = 0;
        for (Employee e : employees) {
            e.setSalary(e.getType().getSalary());
            if (e.getType() == Position.MANAGER) {
                e.setSalary(e.getSalary() + ((Manager) e).getSpecialists().size() * PREMIUM_SPECIALISTS);
            }
            totalSalary += e.getSalary();
            if (checkForBirthDay(e.getDateBirth()) == 0) {
                e.setSalary(e.getSalary() + PREMIUM_BIRTHDAY);
                birthdayCount++;
            }
        }
        if (this.budget < totalSalary) {
            throw new NumberFormatException();
        }
        this.budget -= totalSalary - birthdayCount * PREMIUM_BIRTHDAY;
        recalculate(birthdayCount, employees);
        return createPayroll(employees);
    }

    private String createPayroll(List<Employee> employees) {
        String payroll = System.lineSeparator();
        for (Employee e : employees) {
            payroll += e.getName() + Output.BR_L_POSITION +
                    e.getType().toString().toLowerCase() + Output.BR_R_POSITION +
                    Output.COUNTING + e.getSalary() + System.lineSeparator();
        }
        return payroll;
    }

    int checkForBirthDay(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Config.DATE_FORMAT_BIRTHDAY);
        return dateFormat.parse(dateFormat.format(new Date())).compareTo(dateFormat.parse(date));
    }

    public abstract void recalculate(int birthdayCount, List<Employee> employees) throws ParseException;
}
