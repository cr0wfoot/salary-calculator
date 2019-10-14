package controller;

import model.Company.Department;
import model.distribution.Distribution;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Config {

	private String dataDivider;
	private final Properties props = new Properties();
	
	public static String DATE_FORMAT = "dd.MM.yy";
	public static String DATE_FORMAT_BIRTHDAY = "dd.MM";
	static Distribution[] DISTRIBUTION;
	
	void load(String patternsPath, String configPath)
			throws IOException, InstantiationException,
				   IllegalAccessException,
				   InvocationTargetException, ClassNotFoundException {
		
		props.load(new BufferedInputStream(new FileInputStream(patternsPath)));
		Validator.DATE_PATTERN = props.getProperty("date");
		Validator.NAME_PATTERN = props.getProperty("name");
		Validator.POSITION_PATTERN = props.getProperty("position");
		Validator.DEPARTMENT_PATTERN = props.getProperty("department");
		Validator.DESCRIPTION_PATTERN = props.getProperty("description");
		props.load(new BufferedInputStream(new FileInputStream(configPath)));
		dataDivider = props.getProperty("PROPS.data.divider").trim();
		DATE_FORMAT = props.getProperty("date.format");
		DATE_FORMAT_BIRTHDAY = props.getProperty("date.format.birthday").trim();
		Distribution.PREMIUM_BIRTHDAY = Double.parseDouble(props.getProperty("premium.birthday"));
		Distribution.PREMIUM_SPECIALISTS = Double.parseDouble(props.getProperty("premium.specialists"));
		loadDistribution();
	}
	
	private void loadDistribution()
			throws InstantiationException, IllegalAccessException,
				   InvocationTargetException, ClassNotFoundException {
		
		String budget = props.getProperty("company.budget");
		String distribution = props.getProperty("company.distribution");
		int distributionLength = 1,
			args = 0;
		if(budget == null || distribution == null) {
			DISTRIBUTION = new Distribution[Department.values().length];
			String tmpDept;
			int index = 0;
			for(Department d : Department.values()) {
				tmpDept = props.getProperty("company." + d.toString().toLowerCase());
				budget = tmpDept.split(dataDivider)[args++];
				distribution = tmpDept.split(dataDivider)[args--];
				DISTRIBUTION[index++] = getDistribution(budget, distribution);
			}
		} else {
			DISTRIBUTION = new Distribution[distributionLength];
			DISTRIBUTION[args] = getDistribution(budget, distribution.trim());
		}
	}
	
	private Distribution getDistribution(String budget, String distr) 
			throws InstantiationException, IllegalAccessException, InvocationTargetException,
			       ClassNotFoundException {
		return (Distribution) Class.forName("model.distribution." + distr + "Distribution")
								   .getDeclaredConstructors()[0].newInstance(Double.valueOf(budget));
	}
}
