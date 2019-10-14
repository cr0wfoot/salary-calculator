package controller;

import model.Company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class ExceptionHandler {

    private final static String PATH = "resources/output/error.txt";
    private final static String ERROR_MESSAGE = "Error!";

    private static volatile ExceptionHandler instance;

    public static ExceptionHandler getInstance() {
        if (instance == null) {
            synchronized (Company.class) {
                if (instance == null) instance = new ExceptionHandler();
            }
        }
        return instance;
    }

    public void catchException(Exception ex) {
        System.out.println(ERROR_MESSAGE);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(PATH, true));
            out.write(new Date().toString());
            out.newLine();
            out.write(ex.toString());
            out.newLine();
            for (StackTraceElement st : ex.getStackTrace()) {
                out.write(st.toString());
                out.newLine();
            }
            out.newLine();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
