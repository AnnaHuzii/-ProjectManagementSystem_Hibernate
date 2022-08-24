package web.command.companies;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.company.CompanyDaoService;
import servisDB.company.CompanyService;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompaniesMenuCommand implements Command {
    CompanyService companyConnections = CompanyDaoService.getInstance();
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        resp.setContentType("text/html, charset=utf-8");
        Context context = new Context();
        engine.process("companies", context, resp.getWriter());
        resp.getWriter().close();
    }


}