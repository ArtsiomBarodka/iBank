package by.javatr.ibank.controller.command.impl;

import by.javatr.ibank.controller.command.Command;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class WrongRequest implements Command {
    @Override
    public String execute(String request) {
        String response = "Такой команды нет. \nВведите команду help";

        return response;
    }
}
