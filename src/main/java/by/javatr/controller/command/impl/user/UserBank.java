package by.javatr.ibank.controller.command.impl.user;

import by.javatr.ibank.Constants;
import by.javatr.ibank.bean.User;
import by.javatr.ibank.controller.command.Command;
import by.javatr.ibank.service.UserService;
import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.exception.ServiceValidationException;
import by.javatr.ibank.service.factory.ServiceFactory;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class UserBank implements Command {
    @Override
    public String execute(String request) {
        long userId = 0;
        double income = 0;
        String response = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            request = request.replaceAll("[\\s]{2,}", " ");
            String[]arr =request.split(Constants.REQUEST_DELIMITER);

            userId = Long.parseLong(arr[1]);
            income = Double.parseDouble(arr[2]);

            userService.updateIncome(userId, income);

            User user = userService.getUserById(userId);
            
            response = String.format("Поступил доход в размере: %f%n%n", income);

            response+= String.format("Пользователь %s %nОстаток: %f%n" +
                            "Трат за месяц: %f%n" +
                            "Трат за день: %f%n",
                            user.getName(), user.getIncome(),
                            user.getMonthExpense(), user.getDayExpense());

        } catch (ServiceValidationException e) {
            response = "Data entry error during add income procedure";
        } catch (ServiceSourceException e) {
            response = "Source error, restart your app";
        } catch (Exception e){
            response = "Request error";
        }

        return response;
    }
}
