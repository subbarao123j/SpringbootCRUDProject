package com.psi.subba.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.subba.model.Employee;
import com.psi.subba.service.IEmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController { 

	@Autowired
	private IEmployeeService service;

	// 1.show Register page

	/*
	 * If End user enters /register , GET type then we should display one register
	 * page in browser
	 */

	
	/*
	 * @RequestMapping(value = "/secured-api/all-pendingapproval-tasks", method =
	 * RequestMethod.GET, produces = "application/json;charset=UTF-8")
	 * 
	 * @ResponseBody public Callable<String>
	 * getAllPendingApprovalTasks(@RequestParam(value = "skip", required = false,
	 * defaultValue = "0") int skip,
	 * 
	 * @RequestParam(value = "pageSize", required = false, defaultValue = "1000")
	 * int pageSize) { return () -> { try {
	 * 
	 * Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); User user =
	 * userAndRoleService.getUser(authentication.getName()); if (user == null) {
	 * throw new Exception("Cannot retrieve user information"); }
	 * 
	 * return responsePayloader.generate(true,
	 * landingPageService.getAllApprovalTasks(user.getUsername()));
	 * 
	 * } catch (Exception exception) { logger.error(exception.getMessage(),
	 * exception); return responsePayloader.generate(false, null,
	 * exception.getMessage()); } }; }
	 */
	
	
	@GetMapping("/register")
	public String showReg() {
		
		
			return "EmployeeRegister";
			
				
	
	}

	/*
	 * On click form submit read data as modleartribute save using service Return
	 * message to UI
	 */

	// 2.save(): Click Form submit
	@PostMapping("/save")
	public String saveEmp(

			@ModelAttribute Employee employee, Model model

	) {

		Integer id = service.saveEmployee(employee);

		String message = "Employee '" + id + "' created!!";

		model.addAttribute("message", message);

		return "EmployeeRegister";

	}

	/*
	 * Fetch DATA FROM DATABASE AS LIST<t> SEND THIS TO UI USE FOR EACH AND DISPLAY
	 * AS HTML PAGE
	 */

	// 3. display all rows

	@GetMapping("/all")
	public String getAllEmps(Model model) {

		List<Employee> emps = service.getAllEmployees();

		model.addAttribute("list", emps);

		return "EmployeeData";

	}

	// 4. Delete by id

	@GetMapping("/delete")
	public String deleteById(

			@RequestParam Integer id, Model model

	) {

		// delete record by id
		service.deleteEmployee(id);

		// send message to ui
		model.addAttribute("message", "Employee '" + id + "' deleted");

		// also load latest data
		List<Employee> emps = service.getAllEmployees();

		model.addAttribute("list", emps);

		return "EmployeeData";
	}

	// 5. Show Data in Edit (by id)

	/*
	 * On click Edit link, read id and load object from DB if exist goto edit page
	 * else redirect to all page
	 */

	public String showEdit(@RequestParam Integer id, Model model

	) {
		String page = null;

		// try to load from DB
		Optional<Employee> opt = service.getOneEmployee(id);

		// if data exist

		if (opt.isPresent()) {

			model.addAttribute("employee", opt.get());

			page = "EmployeeEdit";

		} else { // no data exist
			page = "redirect:all"; // goto data page

		}

		return page;
	}
	
	
	

	// 6. Update data

	/*
	 * on click update button , read form data as modelattribute update in db and
	 * send success message to UI also load latest data
	 */
	@PostMapping("/update")
	public String doUpdate(

			@ModelAttribute Employee employee, Model model

	) {

		service.updateEmployee(employee);   
		// send message to ui
		model.addAttribute("message", "Employee '" + employee.getEmpId() + "' Updated");

		// also load latest data
		List<Employee> emps = service.getAllEmployees();

		model.addAttribute("list", emps);

		return "EmployeeData";

	}
	
	
	
	
	
	

}
