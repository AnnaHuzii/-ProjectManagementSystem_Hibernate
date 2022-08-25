package servisDB.project;

import java.util.List;

public interface ProjectService {

    long createProject(String name, Project project);

    Project getProjectById(long id);

    String getProjectByName (String name);

    List<Project> getProjectList();

    float getBudgetByProjectName(String name);

   String listOfProjectDevelopers(String name);

    String getProjectsListInSpecialFormat();
}
