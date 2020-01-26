package by.javatr.ibank.controller.command.impl.user;

import by.javatr.ibank.controller.command.Command;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class UserHelp implements Command {
    @Override
    public String execute(String request) {
        String commands = "Список команд:\n" +
                "help - помощь\n" +
                "add - добавление траты\n" +
                "bank - добавление дохода\n" +
                "day - список расходов за день\n" +
                "month - список расходов за месяц\n" +
                "create - добавить новую категорию расходов\n" +
                "delete - удалить категорию расходов\n\n";

        String add = "Чтобы добавить расход введите команду add\n" +
                "затем через пробелы название категории и сумму потраченую\n" +
                "Например: add еда 50,25\n" +
                "Если название введенной категории не соотвествует названиям из списка,\n" +
                "то будет выбрана категория расходов другое\n\n";

        String bank = "Чтобы добавить доход введите команду bank\n" +
                "затем через пробелы сумму поступившую\n" +
                "Например: bank 250,75\n\n";

        String day = "Чтобы вывести список всех расходов по категориям за день\n" +
                "введите команду day\nНапример: day\n\n";

        String month = "Чтобы вывести список всех расходов по категориям за месяц\n" +
                "введите команду month\nНапример: month\n\n";

        String create = "Чтобы добавить новую категорию расходов\n" +
                "введите команду create затем через пробелы название новой категории\n" +
                "Например: create животные\n\n";

        String delete = "Чтобы удалить категорию расходов\n" +
                "введите команду delete затем через пробелы название категории\n" +
                "Например: delete такси\n\n";

        String response = commands + add + bank + day + month + create + delete;

        return response;
    }
}
