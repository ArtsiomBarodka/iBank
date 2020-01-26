package by.javatr.ibank.controller.command.impl.user;

import by.javatr.ibank.Constants;
import by.javatr.ibank.bean.Category;
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
public class UserAdd implements Command {
    @Override
    public String execute(String request) {
        long userId = 0;
        String categoryName = null;
        double expense = 0;
        String response = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            request = request.replaceAll("[\\s]{2,}", " ");
            String[]arr =request.split(Constants.REQUEST_DELIMITER);

            userId = Long.parseLong(arr[1]);
            categoryName = arr[2];
            expense = Double.parseDouble(arr[3]);

            userService.updateCategory(userId, categoryName, expense);

            Category category = userService.getCategory(userId, categoryName);
            User user = userService.getUserById(userId);

            response = String.format("По категории: %s добавлен расход %f%n%n",
                    category.getName(), expense);

            response+= String.format("%s%nТрат за день: %f%nТрат за месяц: %f%n",
                    category.getName(), category.getDayExpense(),
                    category.getMonthExpense());

            response+= String.format("%nПользователь %s %nОстаток: %f%n" +
                            "Трат за месяц: %f%n" +
                            "Трат за день: %f%n",
                    user.getName(), user.getIncome(),
                    user.getMonthExpense(), user.getDayExpense());

        } catch (ServiceValidationException e) {
            response = "Data entry error during add expense procedure";
        } catch (ServiceSourceException e) {
            response = "Source error, restart your app";
        } catch (Exception e){
            response = "Request error";
        }

        return response;
    }

}
