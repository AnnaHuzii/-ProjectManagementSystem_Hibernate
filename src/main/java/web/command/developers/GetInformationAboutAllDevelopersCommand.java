package web.command.developers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.developer.Developer;
import servisDB.developer.DeveloperDaoService;
import servisDB.developer.DeveloperService;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GetInformationAboutAllDevelopersCommand implements Command {
    DeveloperService developerConnections = DeveloperDaoService.getInstance();
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException {

        List<Developer> allFullName = developerConnections.getListDeveloper();

        List<Developer> result = new ArrayList<>();
        for (Developer developer: allFullName){
            Developer list = new Developer();
            list.setDeveloperId(developer.getDeveloperId());
            list.setFullName(developer.getFullName());
            list.setBirthDate(developer.getBirthDate());
            result.add(list);
        }
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("list", result)
        );
        resp.setContentType("text/html; charset=utf-8");
        engine.process("developer_all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }

}
