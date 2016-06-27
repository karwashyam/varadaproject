package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.dto.LeadJsonDto;
import com.webapp.models1.LeadModel;


public interface LeadFromDao {

	int addLead(LeadModel leadModel);

	List<LeadJsonDto> fetchLeads(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalLeads(@Param("JQTableUtils") JQTableUtils tableUtils);


}
