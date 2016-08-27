package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.EmployeeDao;
import com.webapp.dto.EmployeeDto;
import com.webapp.models.CityModel;
import com.webapp.models.EmployeeModel;

@Service("employeeService")
public class EmployeeService {

	private static final Logger logger = Logger.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeDao employeeDao;

	@Transactional
	public List<EmployeeDto> fetchEmployeeList(JQTableUtils tableUtils) {
		return employeeDao.fetchEmployeeList(tableUtils);
	}

	public long fetchTotalEmployeeList(JQTableUtils tableUtils) {
		return employeeDao.fetchTotalEmployeeList(tableUtils);
	}
	
	public boolean fetchEmpByPhone(EmployeeModel employeeModel){
		return employeeDao.fetchEmpByPhone(employeeModel);
	}
	
	public boolean fetchEmpByEmail(EmployeeModel employeeModel){
		return employeeDao.fetchEmpByEmail(employeeModel);
	}
	
	public int fetchEmployeeByUserName(EmployeeModel employeeModel){
		return employeeDao.fetchEmployeeByUserName(employeeModel);
	}
	
	public int postAddEmployee(EmployeeModel employeeModel, String userId) {
		long time = DateUtils.nowAsGmtMillisec();
		employeeModel.setUserId(UUIDGenerator.generateUUID());
		employeeModel.setCreatedBy(userId);
		employeeModel.setUpdatedBy(userId);
		employeeModel.setCreatedAt(time);
		employeeModel.setUpdatedAt(time);
		employeeModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		return employeeDao.postAddEmployee(employeeModel);
	}
	
	public int changeEmployeeStatus(EmployeeModel employeeModel){
		long time = DateUtils.nowAsGmtMillisec();
		employeeModel.setUpdatedAt(time);
		return employeeDao.changeEmployeeStatus(employeeModel);
	}
	
	public int editEmployee(EmployeeModel employeeModel){
		long time = DateUtils.nowAsGmtMillisec();
		employeeModel.setUpdatedAt(time);
		return employeeDao.editEmployee(employeeModel);
	}
	
	public EmployeeModel fetchEmployeeDetail(String employeeId){
		return employeeDao.fetchEmployeeDetail(employeeId);
	}
}