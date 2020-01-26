package by.javatr.ibank.controller.command.impl.user;

import by.javatr.ibank.Constants;
import by.javatr.ibank.bean.User;
import by.javatr.ibank.controller.command.Command;
import by.javatr.ibank.service.UserService;
import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.factory.ServiceFactory;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class UserDelete implements Command {
    @Override
    public String execute(String request) {
        long userId = 0;
        String categoryName = null;
        String response = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        try {
            request = request.replaceAll("[\\s]{2,}", " ");
            String[]arr =request.split(Constants.REQUEST_DELIMITER);

            userId = Long.parseLong(arr[1]);
            categoryName = arr[2];

            boolean isDeleted =  userService.deleteCategory(userId, categoryName);

            if(!isDeleted){
                response = String.format("Не удалось удалить категорию %s%n",
                        categoryName);

            } else {
                User user = userService.getUserById(userId);

                response = String.format("Удалена категория %s%n", categoryName);

                response+= String.format("Пользователь %s %nОстаток: %f%n" +
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
