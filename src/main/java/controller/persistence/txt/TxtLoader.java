package controller.persistence.txt;

import controller.Validator;
import controller.persistence.LoaderFactory;
import model.Company;
import model.position.Employee;
import model.position.Employee.Position;
import model.position.Manager;
import model.position.Others;
import model.position.Specialist;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class TxtLoader extends LoaderFactory {

    private final static String ARRAY_SEPARATOR = ";";
    private final static String FIELD_NAME = "name";
    private final static String FIELD_DEPT = "department";
    private final static String FIELD_POS = "position";
    private final static String FIELD_ADDITIONAL = "additional";
    private final static String FIELD_DT_BIRTH = "birth date";
    private final static String FIELD_DT_COMP = "company date";

    private List<String> params;
    private Map<String, Specialist> specialists = new HashMap<>();
    private String[] args;

    @Override
    public void upload(String path) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        out.write(Company.getInstance().getPayroll());
        out.flush();
        out.close();
    }

    @Override
    public void download(String path)
            throws IOException, NullPointerException,
            IllegalArgumentException,
            SecurityException, ArrayIndexOutOfBoundsException,
            InputMismatchException, ClassCastException {

        BufferedReader in = new BufferedReader(new FileReader(path));
        Company comp = Company.getInstance();
        args = in.readLine().split(ARRAY_SEPARATOR);
        params = new LinkedList<>();
        for (String p : args) {
            params.add(p.toLowerCase().trim());
        }
        while (in.ready()) {
            args = in.readLine().split(ARRAY_SEPARATOR);
            Employee e = getConcreteEmployee();
            e.setDateBirth(Validator.getInstance().validDate(args[params.indexOf(FIELD_DT_BIRTH)]));
            e.setDateCompany(Validator.getInstance().validDate(args[params.indexOf(FIELD_DT_COMP)]));
            e.setName(Validator.getInstance().validName(args[params.indexOf(FIELD_NAME)]));
            e.setDepartment(Validator.getInstance().validDept(args[params.indexOf(FIELD_DEPT)]));
            comp.addEmployee(e);
        }
        estabilishRelations();
        in.close();
    }

    private void estabilishRelations() throws NullPointerException {
        for (Entry<String, Specialist> e : specialists.entrySet()) {
            e.getValue().changeManager(Company.getInstance().findManager(e.getKey(), e.getValue().getDepartment()));
        }
        specialists = null;
    }

    private Employee getConcreteEmployee()
            throws SecurityException, NullPointerException,
            ArrayIndexOutOfBoundsException, InputMismatchException,
            ClassCastException {

        int indexOfAdditional = params.indexOf(FIELD_ADDITIONAL);
        Position p = Employee.Position.valueOf(
                Validator.getInstance().validPosition(
                        args[params.indexOf(FIELD_POS)]).toUpperCase());
        switch (p) {
            case MANAGER:
                return new Manager();
            case SPECIALIST:
                Specialist s;
                Employee manager;
                String managerName = Validator.getInstance().validName(args[indexOfAdditional]);
                manager = Company.getInstance().findEmployee(managerName);
                specialists.put(managerName, s = new Specialist((Manager) manager));
                return s;
            case DIRECTOR:
            case SECRETARY:
                return new Others(
                        p, Validator.getInstance().validDesc(
                        args[indexOfAdditional])
                );
            default:
                return new Others(
                        Position.SECRETARY, Validator.getInstance().validDesc(
                        args[indexOfAdditional])
                );
        }
    }
}
