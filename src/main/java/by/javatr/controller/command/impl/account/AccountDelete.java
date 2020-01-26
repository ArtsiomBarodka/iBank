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
public class AccountDelete implements Command {
    @Override
    public String execute(String request) {
        String login = null;
        int password = 0;
        String response = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AuthenticationService authenticationService = serviceFactory.getAuthenticationService();
        UserService userService = serviceFactory.getUserService();

        try {
            String[]arr =request.split(Constants.REQUEST_DELIMITER);

            login = arr[1];
            password = Integer.parseInt(arr[2]);

            if(authenticationService.deleteAccount(login, password)){
                userService.deleteUser(userService.loadUser(login));

                response = String.format("аккаунт %s удален%n%n", login);

            } else if(!authenticationService.hasAccount(login)){
                response = String.format("аккаунт %s не существует %n%n", login);

            } else {
                response = "не верный пароль %n%n";
            }

        } catch (ServiceValidationException e) {
            response = "Data entry error during delete account procedure: "
                        + e.getMessage();
        } catch (ServiceSourceException e) {
            response = "Source error, restart your app";
        } catch (Exception e){
            response = "Request error";
        }

        return response;
    }
}
