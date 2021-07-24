package com.cognixia.jump.corejava.project1;

/**
 * Class Purpose - utility class (no main method)
 * 		used to stream Employees and the attributes that describe an Employee object
 * 
 * Make the attributes, getters and setters, and override the .toString() method
 * 
 * Can file / code to class, no need to live code.
 */

public class Employee extends Person{

private static int idCounter = 1;

	private int id;
	//private String name;
	private int salary;
	private String department;

	public Employee(String name, int salary, String department) {
		super(name);
		//name = super.getName();
		this.salary = salary;
		this.department = department;
		this.id = idCounter++;
	}

	

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getId() {
		return id;
	}



	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + super.getName() + ", salary=" + salary + ", department=" + department + "]";
	}

	
}




