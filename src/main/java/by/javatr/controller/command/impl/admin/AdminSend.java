package by.javatr.ibank.controller.command.impl.admin;

import by.javatr.ibank.Constants;
import by.javatr.ibank.controller.command.Command;
import by.javatr.ibank.service.AdminService;
import by.javatr.ibank.service.AuthenticationService;
import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.exception.ServiceValidationException;
import by.javatr.ibank.service.factory.ServiceFactory;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class AdminSend implements Command {
    @Override
    public String execute(String request) {
        String login = null;
        String password = null;
        String response = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AdminService adminService = serviceFactory.getAdminService();
        AuthenticationService authenticationService =
                    serviceFactory.getAuthenticationService();

        try {
            request = request.replaceAll("[\\s]{2,}", " ");
            String[] arr = request.split(Constants.REQUEST_DELIMITER);

            login = arr[1];
            password = arr[2];

            StringBuilder message = new StringBuilder();

            for (int i = 3; i < arr.length; i++) {
                message.append(arr[i]);
            }

            String adminEmail = authenticationService.getEmail(login);

            adminService.sendAllUsersMessage(adminEmail, password, message.toString());

            response = "Сообщение отправлено всем пользователям на почтовый ящик";
        }catch (ServiceValidationException e){
            response = e.getMessage();
        } catch (ServiceSourceException e) {
            response = "Source error, can`t send message";
        } catch (Exception e){
            response = "Request error";
        }

        return response;
    }

}
