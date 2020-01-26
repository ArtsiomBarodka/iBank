package by.javatr.ibank.controller.command.impl.admin;

import by.javatr.ibank.controller.command.Command;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class AdminHelp implements Command {
    @Override
    public String execute(String request) {
        String commands = "Список команд:\n" +
                "help - помощь\n" +
                "count - вывести колтчество пользователей\n" +
                "send - отправить сообщение всем пользователям на почту\n\n";

        String send = "Чтобы отправить сообщение всем пользователям на почту\n" +
                "введите команду send затем через пробел пароль от почты и сообщение\n" +
                "Например: send 111111 Всем привет! \n\n";

        String response = commands + send;

        return response;
    }
}
