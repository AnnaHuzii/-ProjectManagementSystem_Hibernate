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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AllInfoAboutProjectCommand implements Command {
    ProjectService projectDaoService = ProjectDaoService.getInstance();
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {
        resp.setContentType("text/html; charset=utf-8");
        String projectName = req.getParameter("projectName");
        if (!projectName.equals("")) {
            // Отримати дані по назві проекту
            String getInfoByName = projectDaoService.getProjectByName(projectName);
            String[] words = getInfoByName.split("<br>");
            ArrayList listInfoByName = new ArrayList();
            Collections.addAll(listInfoByName, words);

            // Отримати список всіх розробників конкретного проекту KUP Agro
           String listOfProjectDevelopers = projectDaoService.listOfProjectDevelopers(projectName);
            String[] wordsName = listOfProjectDevelopers.split("<br>");
            ArrayList listDeveloper = new ArrayList();
            Collections.addAll(listDeveloper, wordsName);

            // Суму зарплат всіх розробників по проекту
            float projectCost = projectDaoService.getBudgetByProjectName(projectName);

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("nameProject", projectName, "listInfoByName", getInfoByName,
                            "listDeveloper", listOfProjectDevelopers, "projectCost", projectCost)
            );


            engine.process("project_all_info_about", simpleContext, resp.getWriter());
            resp.getWriter().close();
        } else {
            Context incorrectlyContext = new Context();
            engine.process("error_projects_incorrectly", incorrectlyContext, resp.getWriter());
            resp.getWriter().close();
        }
    }
}
