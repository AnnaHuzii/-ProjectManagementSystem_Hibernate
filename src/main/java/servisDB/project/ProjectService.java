package servisDB.project;

import java.util.List;

public interface ProjectService {

    long createProject(String name, Project project);

    Project getProjectById(long id);

    Project getProjectByName (String name);

    List<Project> getProjectList();

    float getBudgetByProjectName(String name);

   List <String> listOfProjectDevelopers(String name);

   List <Project> getProjectsListInSpecialFormat();
}
