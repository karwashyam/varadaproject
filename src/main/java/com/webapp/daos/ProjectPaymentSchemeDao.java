package com.webapp.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.webapp.models.ProjectPaymentSchemeModel;

public interface ProjectPaymentSchemeDao {

	public void addProjectPaymentScheme(ProjectPaymentSchemeModel projectPaymentSchemeModel);
	public void updateProjectPaymentScheme(ProjectPaymentSchemeModel p);
	public List<ProjectPaymentSchemeModel> fetchProjectPayementScheme();
	public ProjectPaymentSchemeModel getProjectsPaymentSchemeById(String projPaymentSchemeId);
	public void deleteProjectPaymentScheme(ProjectPaymentSchemeModel projectModel);
	public List<Map<String, Object>> fetchProjPaymentSchemeList(
			Map<String, Object> inputMap);
	public Long fetchTotalProjPaymentSchemeListCount();
	public boolean isProjPaymentSchemeExists(@Param("paymentSchemeId") String paymentSchemeId,@Param("projectId") String projectId);
}
