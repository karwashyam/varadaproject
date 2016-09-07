package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.models.TdsModel;

public interface TdsDao {


	List<TdsModel> fetchTdsList(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalTdsList(@Param("JQTableUtils") JQTableUtils tableUtils);

	Long fetchTdsDue();

	Long fetchTdsCreditDue();
	

}
