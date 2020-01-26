package by.javatr.ibank.controller.command.impl.account;

import by.javatr.ibank.controller.command.Command;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class AccountExit implements Command {
    @Override
    public String execute(String request) {
        String response = "Выход из программы";

        return response;
    }
}
