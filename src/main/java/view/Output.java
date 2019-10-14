package view;

import controller.persistence.LoaderFactory;
import model.Company;

public class Output {
	
	public final static String WORDS_SEPARATOR = " ";
	public final static String BR_L_OBJECT = " [";
	public final static String BR_R_OBJECT = "] ";
	public final static String BR_L_ARRAY = " {";
	public final static String BR_R_ARRAY = "} ";
	public final static String BR_L_POSITION = "(";
	public final static String BR_R_POSITION = ")";
	public final static String COUNTING = ": ";
	
	public void printEmployees() {
		System.out.println(Company.getInstance());
	}
	
	public void uploadPayroll(String path)
			throws Exception {
		
		LoaderFactory.getInstance().upload(path);
	}

}
