package controller;

import model.Company.Department;

import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class Validator {

    private final static char POSSIBLE_DATE_SYMBOL_COMMA = ',';
    private final static char POSSIBLE_DATE_SYMBOL_SLASH = '/';
    private final static char POSSIBLE_DATE_SYMBOL_DASH = '-';
    private final static char CURRENT_DATE_SYMBOL = '.';

    static String DATE_PATTERN;
    static String NAME_PATTERN;
    static String POSITION_PATTERN;
    static String DEPARTMENT_PATTERN;
    static String DESCRIPTION_PATTERN;

    private static volatile Validator instance;

    public static Validator getInstance() {
        if (instance == null) {
            synchronized (Validator.class) {
                if (instance == null) instance = new Validator();
            }
        }
        return instance;
    }

    public String validPosition(String arg) {
        if (arg == null || !Pattern.compile(POSITION_PATTERN).matcher(arg = arg.trim()).matches()) {
            throw new InputMismatchException();
        }
        return arg;
    }

    public String validName(String arg) {
        if (arg == null || !Pattern.compile(NAME_PATTERN).matcher(arg = arg.trim()).matches()) {
            throw new InputMismatchException();
        }
        return arg;
    }

    public String validDesc(String arg) {
        if (arg == null || !Pattern.compile(DESCRIPTION_PATTERN).matcher(arg = arg.trim()).matches()) {
            throw new InputMismatchException();
        }
        return arg;
    }

    public String validDate(String arg) {
        if (arg == null ||
                !Pattern.compile(DATE_PATTERN).matcher(
                        arg = arg.trim().replace(POSSIBLE_DATE_SYMBOL_COMMA, CURRENT_DATE_SYMBOL)
                                .replace(POSSIBLE_DATE_SYMBOL_DASH, CURRENT_DATE_SYMBOL)
                                .replace(POSSIBLE_DATE_SYMBOL_SLASH, CURRENT_DATE_SYMBOL))
                        .matches()
                ) {
            throw new InputMismatchException();
        }
        return arg;
    }

    public Department validDept(String arg) {
        if (arg == null ||
                !Pattern.compile(DEPARTMENT_PATTERN).matcher(arg = arg.trim().toUpperCase()).matches() ||
                Department.valueOf(arg) == null
                ) {
            throw new InputMismatchException();
        }
        return Department.valueOf(arg);
    }
}
