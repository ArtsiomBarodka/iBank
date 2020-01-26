package by.javatr.ibank.controller.command.impl.user;

import by.javatr.ibank.controller.command.Command;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class UserExit implements Command {
    @Override
    public String execute(String request) {
        String response = "Выход из меню пользователя";

        return response;
    }
}
