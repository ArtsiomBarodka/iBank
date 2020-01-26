package by.javatr.ibank.controller.command.impl.account;

import by.javatr.ibank.Constants;
import by.javatr.ibank.controller.command.Command;
import by.javatr.ibank.service.AuthenticationService;
import by.javatr.ibank.service.UserService;
import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.exception.ServiceValidationException;
import by.javatr.ibank.service.factory.ServiceFactory;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class AccountCreate implements Command {
    @Override
    public String execute(String request) {
        String login = null;
        int password = 0;
        String email = null;
        String response = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AuthenticationService authenticationService = serviceFactory.getAuthenticationService();
        UserService userService = serviceFactory.getUserService();

        try {
            String[]arr =request.split(Constants.REQUEST_DELIMITER);

            login = arr[1];
            password = Integer.parseInt(arr[2]);
            email = arr[3];

            if(authenticationService.hasAccount(login)){
                response = String.format("аккаунт %s уже существует.%n%n", login);

            } else {
                authenticationService.createAccount(login, password, email);
                userService.createUser(login);

                response = String.format("аккаунт %s создан%n%n", login);
            }

        } catch (ServiceValidationException e) {
            response = "Data entry error during create account procedure: "
                        + e.getMessage();
        } catch (ServiceSourceException e) {
            response = "Source error, restart your app";
        } catch (Exception e){
            response = "Request error";
        }

        return response;
    }
}
