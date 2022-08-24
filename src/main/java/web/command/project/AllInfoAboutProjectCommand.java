package web.command.project;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.project.Project;
import servisDB.project.ProjectDaoService;
import servisDB.project.ProjectService;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class AllInfoAboutProjectCommand implements Command {
    ProjectService projectDaoService = ProjectDaoService.getInstance();
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {

        String projectName = req.getParameter("projectName");
        if (!projectName.equals("")) {
            // Отримати дані по назві проекту
            Project getInfoByName = projectDaoService.getProjectByName(projectName);

            // Отримати список всіх розробників конкретного проекту KUP Agro
            List <String> listOfProjectDevelopers = projectDaoService.listOfProjectDevelopers(projectName);

            // Суму зарплат всіх розробників по проекту
            float projectCost = projectDaoService.getBudgetByProjectName(projectName);

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("nameProject", projectName, "listInfoByName", getInfoByName,
                            "listDeveloper", listOfProjectDevelopers, "projectCost", projectCost)
            );

            resp.setContentType("text/html; charset=utf-8");
            engine.process("project_all_info_about", simpleContext, resp.getWriter());
            resp.getWriter().close();
        } else {
            Context incorrectlyContext = new Context();
            engine.process("error_projects_incorrectly", incorrectlyContext, resp.getWriter());
            resp.getWriter().close();
        }
    }
}
