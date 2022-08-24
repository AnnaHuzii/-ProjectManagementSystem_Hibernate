package web.command.companies;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.company.Company;
import servisDB.company.CompanyDaoService;
import servisDB.company.CompanyService;
import web.settings.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllCompanyCompanies implements Command {
    CompanyService companyConnections = CompanyDaoService.getInstance();
      @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                "dd.MM.yyyy, HH:mm:ss"
        ));
        resp.getWriter().write("<br>" + currentTime + "</br>");

        List<Company> companies = companyConnections.getCompanyAll();
        List<Company> result = new ArrayList<>();
        for (Company company: companies){
            Company list = new Company();
            list.setCompanyId(company.getCompanyId());
            list.setCompanyName(company.getCompanyName());
            list.setCompanyDescription(company.getCompanyDescription());
            result.add(list);
        }

        Context  simpleContext = new Context(
                req.getLocale(),
                Map.of("list", result)
        );

        resp.setContentType("text/html; charset=utf-8");
        engine.process("companies_all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
