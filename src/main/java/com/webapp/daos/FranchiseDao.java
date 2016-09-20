package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.dto.FranchiseDto;
import com.webapp.models.FranchiseCommissionModel;
import com.webapp.models.FranchiseModel;


public interface FranchiseDao {

	List<FranchiseDto> fetchFranchiseList(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalFranchiseList(@Param("JQTableUtils") JQTableUtils tableUtils);

	int postAddFranchise(FranchiseModel franchiseModel);

	boolean fetchFranByEmail(FranchiseModel model);

	boolean fetchFranByPhone(FranchiseModel model);

	boolean fetchFranByPan(FranchiseModel model);

	int editFranchise(FranchiseModel franchiseModel);

	FranchiseModel fetchFranchiseDetail(String franchiseeId);

	int changeFranchiseStatus(FranchiseModel franchiseModel);

	List<FranchiseDto> fetchAllFranchiseList();

	int addFranchiseeCommission(FranchiseCommissionModel franchiseCommissionModel);

	int updateCommissionAmount(@Param("paymentId") String paymentId,@Param("amount")  long amount);
	
	List<FranchiseModel> fetchFranchiseCommissionList(@Param("JQTableUtils") JQTableUtils tableUtils);

	int updateCommission(@Param("amount") long amount,@Param("tdsAmount") long tdsAmount, @Param("franchiseeId") String franchiseeId);
	
}