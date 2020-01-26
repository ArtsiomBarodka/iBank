package by.javatr.ibank.controller.command.impl.account;

import by.javatr.ibank.controller.command.Command;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class AccountHelp implements Command {
    @Override
    public String execute(String request) {
        String commands = "Список команд:\n" +
                "help - помощь\n" +
                "create - создать аккаунт\n" +
                "delete - удалить аккаунт\n" +
                "enter - войти в аккаунт\n\n";

        String create = "Чтобы создать аккаунт введите команду create \n" +
                "затем через пробелы логин, пароль и адрес почты\n" +
                "Например: create artsiom123 123456 artsiom@gmail.com \n\n";

        String delete = "Чтобы удалить аккаунт введите команду delete\n" +
                "затем через пробелы логин и пароль\n" +
                "Например: delete artsiom123 123456\n\n";

        String enter = "Чтобы войти в аккаунт введите команду enter\n" +
                "затем через пробелы логин и пароль\n" +
                "Например: enter artsiom123 123456\n\n";

        String response = commands + create + delete + enter;

        return response;
    }
}
