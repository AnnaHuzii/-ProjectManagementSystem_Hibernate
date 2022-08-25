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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ListAllProjectCommand implements Command {
    ProjectService projectDaoService = ProjectDaoService.getInstance();
        @Override
        public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {
            resp.setContentType("text/html; charset=utf-8");
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                    "dd.MM.yyyy, HH:mm:ss"
            ));
            resp.getWriter().write("<br>" + currentTime + "</br>");

            // Вивести всі назви проектів
            List <Project> getAllNames = projectDaoService.getProjectList();
            ArrayList list = new ArrayList();
            Collections.addAll(list, getAllNames);

            // Список проектів в наступному форматі:
            //Дата створення проекту - Назва проекту - Кількість розробників на цьому проекті
            String getProjectsListInSpecialFormat = projectDaoService.getProjectsListInSpecialFormat();

            String[] wordsName = getProjectsListInSpecialFormat.split("<br>");
            ArrayList listNameDate = new ArrayList();
            Collections.addAll(listNameDate, wordsName);

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("list", list, "listNameDate", listNameDate)
            );

            engine.process("projects_list_of_all", simpleContext, resp.getWriter());

            resp.getWriter().close();
        }
}
