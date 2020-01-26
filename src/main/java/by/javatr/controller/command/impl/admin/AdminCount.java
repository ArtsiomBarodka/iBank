package by.javatr.ibank.controller.command.impl.admin;

import by.javatr.ibank.controller.command.Command;
import by.javatr.ibank.service.AdminService;
import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.factory.ServiceFactory;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class AdminCount implements Command {
    @Override
    public String execute(String request) {
        String response = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AdminService adminService = serviceFactory.getAdminService();

        try {
            response = String.format("В приложении зарегестрировано %d пользователей%n%n",
                    adminService.countAllUsers());
        } catch (ServiceSourceException e) {
            response = "Source error, restart your app";
        } catch (Exception e){
            response = "Request error";
        }

        return response;
    }
}
