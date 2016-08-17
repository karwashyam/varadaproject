package com.webapp.daos;

import java.util.List;

import com.webapp.models.ProjectModel;
import com.webapp.models.ProjectPaymentSchemeModel;

public interface ProjectPaymentSchemeDao {

	public void addProjectPaymentScheme(ProjectPaymentSchemeModel projectPaymentSchemeModel);
	public void updateProjectPaymentScheme(ProjectPaymentSchemeModel p);
	public List<ProjectPaymentSchemeModel> fetchProjectPayementScheme();
	public ProjectModel getProjectsPaymentSchemeById(int id);
	public void deleteProjectPaymentScheme(int id);
}
