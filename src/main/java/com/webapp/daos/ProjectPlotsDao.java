package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapp.models.ProjectPlotsModel;

public interface ProjectPlotsDao {

	int addProjectPlots(@Param("projectPlotsList") List<ProjectPlotsModel> projPlotsModels);
}
