package com.webapp.daos;

import java.util.List;
import java.util.Map;

import com.webapp.models.ProjectPaymentSchemeModel;

public interface ProjectPaymentSchemeDao {

	public void addProjectPaymentScheme(ProjectPaymentSchemeModel projectPaymentSchemeModel);
	public void updateProjectPaymentScheme(ProjectPaymentSchemeModel p);
	public List<ProjectPaymentSchemeModel> fetchProjectPayementScheme();
	public ProjectPaymentSchemeModel getProjectsPaymentSchemeById(String projPaymentSchemeId);
	public void deleteProjectPaymentScheme(String projPaymentSchemeId);
	public List<Map<String, Object>> fetchProjPaymentSchemeList(
			Map<String, Object> inputMap);
	public Long fetchTotalProjPaymentSchemeListCount();
	public boolean isProjPaymentSchemeExists(String paymentSchemeId, String projectId);
}
