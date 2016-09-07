package com.webapp.daos;

import java.util.List;

import com.webapp.models.FranchiseCommissionModel;



public interface FranchiseCommissionDao {

	List<FranchiseCommissionModel> fetchFranchiseCommissionList(String franchiseeId);

	long fetchTotalFranchiseList();
	
}