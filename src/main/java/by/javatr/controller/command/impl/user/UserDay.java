package by.javatr.ibank.controller.command.impl.user;

import by.javatr.ibank.Constants;
import by.javatr.ibank.bean.Category;
import by.javatr.ibank.bean.User;
import by.javatr.ibank.controller.command.Command;
import by.javatr.ibank.service.UserService;
import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.factory.ServiceFactory;

import java.util.List;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class UserDay implements Command {
    @Override
    public String execute(String request) {
        long userId = 0;
        String response = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            request = request.replaceAll("[\\s]{2,}", " ");
            String[]arr =request.split(Constants.REQUEST_DELIMITER);

            userId = Long.parseLong(arr[1]);

            List<Category> categoryList = userService.getAllCategoriesByDayExpense(userId);
            User user = userService.getUserById(userId);

            if (categoryList == null){
                response = "Сегодня не было трат";
            } else {
                StringBuilder result = new StringBuilder();

                result.append("Траты за сегодня:\n");

                for (Category c:categoryList) {
                    result.append(c.getName()).append(": ")
                            .append(c.getDayExpense()).append(";\n");
                }

                response = result.toString();

                response+= String.format("%nПользователь %s %nОстаток: %f%n" +
                                "Трат за месяц: %f%n" +
                                "Трат за день: %f%n",
                        user.getName(), user.getIncome(),
                        user.getMonthExpense(), user.getDayExpense());
            }

        } catch (ServiceSourceException e) {
            response = "Source error, restart your app";
        } catch (Exception e){
            response = "Request error";
        }

        return response;
    }
}
