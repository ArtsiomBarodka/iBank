package by.javatr.ibank.controller;


import by.javatr.ibank.Constants;
import by.javatr.ibank.controller.command.Command;
import by.javatr.ibank.controller.command.impl.account.*;
import by.javatr.ibank.controller.command.impl.admin.AdminCount;
import by.javatr.ibank.controller.command.impl.admin.AdminExit;
import by.javatr.ibank.controller.command.impl.admin.AdminHelp;
import by.javatr.ibank.controller.command.impl.admin.AdminSend;
import by.javatr.ibank.controller.command.impl.user.*;
import by.javatr.ibank.controller.command.impl.WrongRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
final class CommandProvider {

    private final Map<Constants.CommandName, Command> repository = new HashMap<>();

    CommandProvider(){
        repository.put(Constants.CommandName.WRONG_REQUEST, new WrongRequest());
        repository.put(Constants.CommandName.ACCOUNT_HELP, new AccountHelp());
        repository.put(Constants.CommandName.ACCOUNT_CREATE, new AccountCreate());
        repository.put(Constants.CommandName.ACCOUNT_DELETE, new AccountDelete());
        repository.put(Constants.CommandName.ACCOUNT_ENTER, new AccountEnter());
        repository.put(Constants.CommandName.ACCOUNT_EXIT, new AccountExit());
        repository.put(Constants.CommandName.USER_HELP, new UserHelp());
        repository.put(Constants.CommandName.USER_ADD, new UserAdd());
        repository.put(Constants.CommandName.USER_BANK, new UserBank());
        repository.put(Constants.CommandName.USER_MONTH, new UserMonth());
        repository.put(Constants.CommandName.USER_DAY, new UserDay());
        repository.put(Constants.CommandName.USER_DELETE, new UserDelete());
        repository.put(Constants.CommandName.USER_CREATE, new UserCreate());
        repository.put(Constants.CommandName.USER_EXIT, new UserExit());
        repository.put(Constants.CommandName.ADMIN_HELP, new AdminHelp());
        repository.put(Constants.CommandName.ADMIN_COUNT, new AdminCount());
        repository.put(Constants.CommandName.ADMIN_SEND, new AdminSend());
        repository.put(Constants.CommandName.ADMIN_EXIT, new AdminExit());
    }

     Command getCommand (String name){
        Constants.CommandName commandName = null;
        Command command = null;

        try {
            commandName = Constants.CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (Exception e){
            command = repository.get(Constants.CommandName.WRONG_REQUEST);
        }

        return command;
    }

}
