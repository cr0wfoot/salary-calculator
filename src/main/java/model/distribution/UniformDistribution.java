package model.distribution;

import model.position.Employee;

import java.util.List;

public class UniformDistribution extends Distribution {

	public UniformDistribution(double budget) {
		super(budget);
	}

	@Override
	public void recalculate(int birthdayCount, List<Employee> employees) {	
		double coefficient = super.budget / employees.size();
		for(Employee e : employees) {
			e.setSalary(e.getSalary() + coefficient);
		}
	}
}
