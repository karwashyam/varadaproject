package com.webapp.daos;

import java.util.List;

import com.webapp.models.ProjectPicsModel;

public interface ProjectPicsDao {

	public void addProjecPics(List<ProjectPicsModel> projPicModel);
	public List<ProjectPicsModel> fetchProjectsPicsById();
	public void deleteProjectPics(int id);
}
