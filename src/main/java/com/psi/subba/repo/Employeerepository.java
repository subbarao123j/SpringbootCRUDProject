package com.psi.subba.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psi.subba.model.Employee;

public interface Employeerepository 
     extends JpaRepository<Employee, Integer> {

}
