package by.javatr.ibank.controller.command.impl.admin;

import by.javatr.ibank.controller.command.Command;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class AdminExit implements Command {
    @Override
    public String execute(String request) {
        String response = "Выход из меню администратора";

        return response;
    }
}
