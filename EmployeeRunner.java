package com.cognixia.jump.corejava.project1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors; 



public class EmployeeRunner{

	
	/*******************************************************************************
	* File name: EmployeeRunner.java                                               *
	* Project 1                                                                    *
	* Author(s): Nina Potts                                                        *
	* Date: July 16,2021                                                           *
	* Description: This file creates an Employee Management System for a company.  *
	* You can add, update, remove, or list an Employee's information. The data is saved *
	* to a CSV file under 'resources/EmployeeInfo.csv'                                        
	 * *
	*******************************************************************************/


	public static void main(String[] args)  {
		
		//read in the employee information from EmployeeInfo.txt
		//The format of the text file is "Name Salary Department"
		
		
		File file = new File("resources/EmployeeInfo.txt");
				
        
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		ArrayList<Employee> employees= new ArrayList();
		
		try {
			
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;
			
			
			while((line = bufferedReader.readLine()) != null) {
				
				//Split and parse the line to populate the employees array list with
				//the individual employee data using a stream
				String[] individualEmp = Arrays.stream(line.split(" "))
						  .map(String::trim)
						  .toArray(String[]::new);
				
				
				
				//Do some checks to make sure the data is in the correct format
				if(individualEmp.length > 3)
					System.out.println("The employee data was entered wrong. It should be in the format 'Name Salary Department' ");
				else{
					String name = individualEmp[0];
					int salary = 0;
					try {
						//System.out.println(individualEmp[1]);
						salary = Integer.parseInt(individualEmp[1]);
					}
					catch (NumberFormatException e)
					{
					   System.out.println("The input data is not formated correctly. It has to be a 'String Integer String'");
					  
					}
					String position = individualEmp[2];
					Employee employee = new Employee(name,salary,position);
					//save the employee to CSV
					
					employees.add(employee);
					//System.out.println("The employee was added successfully!");
				}
				
		
				
			}
			

			//write to the csv file before any changes are made
			finalCSV(employees);
			//boolean to check if the user wants to ask more questions
			boolean askAgain = true;
			Scanner input = new Scanner(System.in);
			String request;
			do {

				System.out.println("What would you like to do?\n");
				System.out.println("'add' = Add an employee. \n'update' = Update an existing employees information.");
				System.out.println(
						"'remove' = Remove an employee from the system. \n'department' = Choose a department to list out the employees from. \n'list' = List out an existing employees information.\n\n");
				
				//Pre-process the input to make sure it is in the correct format
				request = input.nextLine();
				request = request.toLowerCase();
				request = request.trim();
				
				switch(request) {
				case "add":
					System.out.println("Enter the new employees information in the format: Name Salary Department ");
					String newEmployee = input.nextLine();
					String[] newEmpList = newEmployee.split(" ");
					if(newEmpList.length > 3) {
						System.out.println("The input you entered was in the wrong format. Try again...");
						
					}else {
						
						String newName = newEmpList[0];
						int salary = 0;
						try {
							salary = Integer.parseInt(newEmpList[1]);
						}
						catch (NumberFormatException e)
						{
							
						   System.out.println("The input data is not formated correctly. It has to be: String Integer String ");
						   e.printStackTrace();
						   
						}
						String position = newEmpList[2];
						Employee employee = new Employee(newName,salary,position);
						employees.add(employee);
						System.out.println("The employee was added successfully!");
						
					}
		
					
					break;
				case "update":
					System.out.println("Enter the person's name whose information you would like to update: ");
					String name = input.nextLine();
					name = name.toLowerCase();
					name = name.trim();
					String[] newName = name.split(" ");
					if(newName.length > 1) {
						System.out.println("The input you entered was in the wrong format. Just enter the first name. Try again...");
						break;
					}
						
					else {

						
						//find the index of the person's name that was input, and if it is not found the method
						//throws a custom EmployeeNotFoundException
						int personIdx = findEmployeeIndex(employees, name);
						
						System.out.println("What information would you like to update? 'name', 'salary', or 'department':");
						name = input.nextLine();
						name = name.toLowerCase();
						name = name.trim();
						
						switch(name) {
						
						case "name":
							System.out.println("Enter the new name for " + employees.get(personIdx).getName()  + ':');
							name = input.nextLine();
							name = name.trim();
							employees.get(personIdx).setName(name);
							break;
						case "salary":
							System.out.println("Enter the new salary for " + employees.get(personIdx).getName()+ ':');
							String salary = input.nextLine();
							salary = salary.trim();
							//check if the value they put in is an integer
							int newSalary;
							try {
								newSalary = Integer.valueOf(salary);
								employees.get(personIdx).setSalary(newSalary);
								System.out.println("The employee's salary was updated successfully!");
							}
							catch (NumberFormatException e)
							{
							   System.out.println("The number you input was not an integer so the salary was not updated.");
							}
							break;
						case "department":
							System.out.println("Enter the new department for " + employees.get(personIdx).getName()+ ':');
							String position = input.nextLine();
							position = position.trim();
							employees.get(personIdx).setDepartment(position);
							System.out.println("The employee's department was changed successfully!");
							break;
						default:
								System.out.println("The input you entered is not valid. You have to choose : 'name', 'salary', or 'department'");
								
						}
						break;
						}
						
					
						
					
					
				case "department":
					System.out.println("Enter the number for the corresponding department:\n1: Developer\n2: Engineer\n3: FrontEnd\n4: Database\n5: Management\n6: Secretary\n7: Intern\n8: CEO");
					String department = input.nextLine();
					department = department.toLowerCase();
					department = department.trim();
					switch(department) {
					case"1":
						List<Employee> developerList = employees.stream().filter(s->s.getDepartment().equals("Developer")).collect(Collectors.toList());
						System.out.println("The employees in the Developer department are: ");
						for (Employee employee : developerList) {
							System.out.println("Name: " + employee.getName() + ", Department: " + employee.getDepartment() + ", Salary: " + employee.getSalary());
						}
						
						break;
					case"2":
						
						List<Employee> engineerList = employees.stream().filter(s->s.getDepartment().equals("Engineer")).collect(Collectors.toList());
						System.out.println("The employees in the Engineer department are: ");
						for (Employee employee : engineerList) {
							System.out.println("Name: " + employee.getName() + ", Department: " + employee.getDepartment() + ", Salary: " + employee.getSalary());
						}
						
						break;
					case "3":
						List<Employee> frontList= employees.stream().filter(s->s.getDepartment().equals("FrontEnd")).collect(Collectors.toList());
						System.out.println("The employees in the Front end department are: ");
						for (Employee employee : frontList) {
							System.out.println("Name: " + employee.getName() + ", Department: " + employee.getDepartment() + ", Salary: " + employee.getSalary());
						}
						break;
					case "4":
						List<Employee> dataList= employees.stream().filter(s->s.getDepartment().equals("Database")).collect(Collectors.toList());
						System.out.println("The employees in the Database department are: ");
						for (Employee employee : dataList) {
							System.out.println("Name: " + employee.getName() + ", Department: " + employee.getDepartment() + ", Salary: " + employee.getSalary());
						}
						break;
					case "5":
						List<Employee> managementList= employees.stream().filter(s->s.getDepartment().equals("Management")).collect(Collectors.toList());
						System.out.println("The employees in the Management department are: ");
						for (Employee employee : managementList) {
							System.out.println("Name: " + employee.getName() + ", Department: " + employee.getDepartment() + ", Salary: " + employee.getSalary());
						}
						break;
					case"6":
						List<Employee> secretaryList= employees.stream().filter(s->s.getDepartment().equals("Secretary")).collect(Collectors.toList());
						System.out.println("The employees in the Secretary department are: ");
						for (Employee employee : secretaryList) {
							System.out.println("Name: " + employee.getName() + ", Department: " + employee.getDepartment() + ", Salary: " + employee.getSalary());
						}
						break;
						
					case "7":
						 List<Employee> internList= employees.stream().filter(s->s.getDepartment().equals("Intern")).collect(Collectors.toList());
						 System.out.println("The employees in the Intern department are: ");
							for (Employee employee : internList) {
								System.out.println("Name: " + employee.getName() + ", Department: " + employee.getDepartment() + ", Salary: " + employee.getSalary());
							}
						break;
					case "8":
						List<Employee> ceoList = employees.stream().filter(s->s.getDepartment().equals("CEO")).collect(Collectors.toList());
						System.out.println("The employees in the CEO department are: ");
						for (Employee employee : ceoList) {
							System.out.println("Name: " + employee.getName() + ", Department: " + employee.getDepartment() + ", Salary: " + employee.getSalary());
						}
						break;
					default:
						System.out.println("Wrong input... You have to type a number from 1-8.");
						break;
							
					
					}
					break;
					
				
				case "remove":
					System.out.println("Enter the person's name who you want removed: ");
					name = input.nextLine();
					name = name.toLowerCase();
					name = name.trim();
					for(int i = 0; i < employees.size(); i ++) {
						if(employees.get(i).getName().toLowerCase().equals(name)) {
							//found the employee at index i
							employees.remove(i);
							System.out.println("The employee you entered was removed!");
						}
							
					}
					break;
				case "list":
					System.out.println("Enter the person's name whose information you want: ");
					name = input.nextLine();
					name = name.trim();
					name = name.toLowerCase();
					boolean found = false;
					for(int i = 0; i < employees.size(); i ++) {
						if(employees.get(i).getName().toLowerCase().equals(name)) {
							//found the employee at index i
							System.out.println(employees.get(i));
							found = true;
							continue;
						}
							
					}
					if(!found) {
						System.out.println("It looks like the employee you entered was not in the database. ");
					}
					break;
				default:
					System.out.println("The input you entered is not valid. You have to choose : 'add' 'update' 'remove' or 'list'");
					break;
						
				}
				
				
				//The switch case to determine if any more CRUD operations need to happen
				System.out.println("Would you like to make any more requests? 'yes' or 'no' ");
				request = input.nextLine();
				request = request.toLowerCase();
				request = request.trim();
				switch (request) {
					case "no":
						askAgain = false;
						System.out.println("Goodbye!");
						break;
					case "yes":
						askAgain = true;
						break;
					default:
						System.out.println("The input you entered is not valid. Exiting the program now...");
						askAgain = false;
						break;

				}
			} while (askAgain);
			
			
			finalCSV(employees);
			input.close();
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				fileReader.close();
				bufferedReader.close();
				
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		

	}

	
	public static void saveToCSV(String name, int salary, String department) throws IOException {
		//write the data to a CSV file
		File filepath = new File("resources/EmployeeInfo.csv");
		FileWriter fw= new FileWriter(filepath);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        
        pw.write(name + ',' + salary + ',' + department + "\n");
        pw.flush();
        pw.close();
       
       
        bw.close();
        fw.close();
	}
	
	
public static void finalCSV(ArrayList<Employee> list) {
	

	try (FileWriter writer = new FileWriter("resources/EmployeeInfo.csv");){
		
		for(Employee l : list) {
			List<String> test = new ArrayList<>();
			test.add(l.getName());
			String salary = String.valueOf(l.getSalary());
			test.add(salary);
			test.add(l.getDepartment());
			String collect = test.stream().collect(Collectors.joining(","));
		    //System.out.println(collect);
		    writer.write(collect);
		    writer.write("\n");
		}
		writer.close();
	} catch (IOException e) {
		System.out.println("Could not write to the EmployeeInfo.csv. ");
		e.printStackTrace();
	}


    
    
}
	
	
public static int findEmployeeIndex(ArrayList<Employee> employees, String name) throws EmployeeNotFoundException{
		int personIdx = 0;
		boolean found = false;
		for(int i = 0; i < employees.size(); i ++) {
			if(employees.get(i).getName().toLowerCase().equals(name)) {
				//found the employee at index i
				personIdx = i;
				found = true;
			}
				
		}
		
		if(found)
			return personIdx;
		else {
			throw new EmployeeNotFoundException(
	                "Could not find employee with name " + name);
	        }
		}
			
		
}
	




