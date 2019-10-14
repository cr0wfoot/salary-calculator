package controller;

import controller.persistence.LoaderFactory;
import model.Company;
import model.position.Manager;
import model.position.Specialist;
import view.Output;

public class Main {

    private final static String PATTERNS_PATH = "resources/properties/patterns.properties";
    private final static String CONFIG_PATH = "resources/properties/config.properties";
    private final static String DATA_PATH = "resources/input/data.txt";
    private final static String PAYROLL_PATH = "resources/output/payroll.txt";

    public static void main(String[] args) {
        try {
            new Config().load(PATTERNS_PATH, CONFIG_PATH);
            LoaderFactory.getInstance().download(DATA_PATH);
            Output output = new Output();
            output.printEmployees();
            Company.getInstance().calculateSalary(Config.DISTRIBUTION);
            output.printEmployees();
            output.uploadPayroll(PAYROLL_PATH);
            demo();
        } catch (Exception ex) {
            ExceptionHandler.getInstance().catchException(ex);
        }
    }

    private static void demo() {
        Company company = Company.getInstance();
        company.findEmployee("Bogdan Holl").changePosition(new Specialist((Manager) company.findEmployee("Alexander Holl")));
        company.compareByBirthDate();
        new Output().printEmployees();
    }
}
