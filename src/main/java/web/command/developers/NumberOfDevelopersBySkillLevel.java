package web.command.developers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.developer.DeveloperDaoService;
import servisDB.developer.DeveloperService;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class NumberOfDevelopersBySkillLevel implements Command {
    DeveloperService developerDaoService = DeveloperDaoService.getInstance();

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException {
        resp.setContentType("text/html; charset=utf-8");

        Context context = new Context();
        String skillLevel = req.getParameter("skillLevel");
        int QuantitySkillLevelDevelopers = developerDaoService.getDevelopersSkillLevel(skillLevel);
        context.setVariable("QuantitySkillLevelDevelopers", QuantitySkillLevelDevelopers);
        context.setVariable("skillLevel", skillLevel);
        engine.process("developers_quantity_java", context, resp.getWriter());
        resp.getWriter().close();

    }
}
