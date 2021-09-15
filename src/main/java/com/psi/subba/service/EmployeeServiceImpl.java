package com.psi.subba.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.subba.model.Employee;
import com.psi.subba.repo.Employeerepository;


@Service
public class EmployeeServiceImpl implements IEmployeeService {
	
	@Autowired
	private Employeerepository repo;

	
	public Integer saveEmployee(Employee e) {
		var esal = e.getEmpSal();
		var har = esal * 20/100.0;
		var ta = esal * 10/100.0;
		
		//set data back to object
		e.setEmpHra(har);
		e.setEmpTa(ta);
		
		//save object
		var id = repo.save(e).getEmpId();
		
		return id;
	}
	
	public void updateEmployee(Employee e) {
		//if given id exist in db then update 
		
		if(repo.existsById(e.getEmpId())) {
			var esal = e.getEmpSal();
			var har = esal * 20/100.0;
			var ta = esal * 10/100.0;
			
			//set data back to object
			e.setEmpHra(har);
			e.setEmpTa(ta);
		repo.save(e);
		}

	}
	
	public void getAllApprovalTasks() {
		
		
		
	}
	
	public void deleteEmployee(Integer id) {
		repo.deleteById(id);
			}
	
	public Optional<Employee> getOneEmployee(Integer id) {
		
		return repo.findById(id);
	}
	
	public List<Employee> getAllEmployees() { 
		
		List<Employee> emps = repo.findAll();
		
		return emps
				.stream()
				.sorted(
						//(e1,e2)->e1.getEmpId().compareTo(e2.getEmpId()) //Asc
						(e1,e2)->e2.getEmpId().compareTo(e1.getEmpId())   //Desc
						)
				.collect(Collectors.toList());
		
	}

}
