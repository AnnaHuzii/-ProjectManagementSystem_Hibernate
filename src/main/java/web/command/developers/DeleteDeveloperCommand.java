package web.command.developers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.developer.DeveloperDaoService;
import servisDB.developer.DeveloperService;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class DeleteDeveloperCommand implements Command {
    DeveloperService developerDaoService = DeveloperDaoService.getInstance();
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException {
        resp.setContentType("text/html; charset=utf-8");
        Context context = new Context();
        long IdDeveloper = Long.parseLong(req.getParameter("developerFullName"));

        if (IdDeveloper == 0) {

            boolean deleteDeveloper = developerDaoService.deleteDeveloperById(IdDeveloper);
            if (deleteDeveloper) {
                context.setVariable("IdDeveloper", IdDeveloper);
                context.setVariable("trueDelete", deleteDeveloper);
            } else {
                context.setVariable("IdDeveloper", IdDeveloper);
                context.setVariable("falseDelete", deleteDeveloper);
            }

            engine.process("developer_delete", context, resp.getWriter());
            resp.getWriter().close();
        } else {
            engine.process("error_developer_incorrectly", context, resp.getWriter());
            resp.getWriter().close();
        }
    }
}

