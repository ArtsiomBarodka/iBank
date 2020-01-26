package by.javatr.ibank.controller;

import by.javatr.ibank.Constants;
import by.javatr.ibank.controller.command.Command;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class Controller {

    private final CommandProvider commandProvider = new CommandProvider();

    public String executeTask(String request){
        String commandName = request.substring(0,
                    request.indexOf(Constants.REQUEST_DELIMITER));

        Command command = commandProvider.getCommand(commandName.toUpperCase());

        String response = command.execute(request);

        return response;
    }

}
