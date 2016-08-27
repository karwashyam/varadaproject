package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.dto.EmployeeDto;
import com.webapp.models.EmployeeModel;


public interface EmployeeDao {

	List<EmployeeDto> fetchEmployeeList(@Param("JQTableUtils") JQTableUtils tableUtils);
	
	public long fetchTotalEmployeeList(@Param("JQTableUtils") JQTableUtils tableUtils);
	
	public boolean fetchEmpByPhone(EmployeeModel employeeModel);
	
	public boolean fetchEmpByEmail(EmployeeModel employeeModel);
	
	public int fetchEmployeeByUserName(EmployeeModel employeeModel);
	
	public int postAddEmployee(EmployeeModel employeeModel);
	
	public int changeEmployeeStatus(EmployeeModel employeeModel);
	
	public int editEmployee(EmployeeModel employeeModel);
	
	public EmployeeModel fetchEmployeeDetail(String employeeId);
	
}