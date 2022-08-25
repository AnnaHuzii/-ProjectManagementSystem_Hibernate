package web.settings;

import web.command.companies.*;
import web.command.customers.CreateCustomersCommand;
import web.command.customers.CustomersMenuCommand;
import web.command.customers.GetInformationAboutAllCustomers;
import web.command.developers.*;
import web.command.main.ChooseTableCommand;
import web.command.main.MainMenuCommand;
import org.thymeleaf.TemplateEngine;
import web.command.project.AllInfoAboutProjectCommand;
import web.command.project.ListAllProjectCommand;
import web.command.project.ProjectMenuCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CommandService {
    private final Map<String, Command> commands;

    public CommandService() {
        commands = new HashMap<>();

        commands.put("GET /", new MainMenuCommand());
        commands.put("POST /", new ChooseTableCommand());

        commands.put("GET /developers", new DevelopersMenuCommand());
        commands.put("POST /developers/all_developers", new GetInformationAboutAllDevelopersCommand());
        commands.put("POST /developers/developer_info", new GetInformationAboutDeveloperByNameCommand());
        commands.put("POST /developers/quantity_java_developers", new NumberOfDevelopersBySkillLevel());
        commands.put("POST /developers/middle_developers", new ListMiddleDevelopersCommand());
        commands.put("POST /developers/add", new CreateDeveloperCommand());
        commands.put("POST /developers/update", new UpdateDeveloperCommand());
        commands.put("POST /developers/delete", new DeleteDeveloperCommand());

        commands.put("GET /companies", new CompaniesMenuCommand());
        commands.put("POST /companies/all_companies", new AllCompanyCompanies());
        commands.put("POST /companies/add", new CreateCompaniesCommand());
        commands.put("POST /companies/delete", new DeleteCompanyCompanies());
        commands.put("POST /companies/update", new UpdateCompanyCompanies());
        commands.put("GET /project", new ProjectMenuCommand());
        commands.put("POST /project/projects_list_of_all", new ListAllProjectCommand());
        commands.put("POST /project/project_all_info_about", new AllInfoAboutProjectCommand());

        commands.put("GET /customers", new CustomersMenuCommand());
        commands.put("POST /customers/all_customers", new GetInformationAboutAllCustomers());
        commands.put("POST /customers/add", new CreateCustomersCommand());

    }

    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException, ParseException {

        String requestUri = req.getRequestURI();
        String commandKey = req.getMethod() + " " + requestUri;

        commands.get(commandKey).process(req, resp, engine);
    }

}
