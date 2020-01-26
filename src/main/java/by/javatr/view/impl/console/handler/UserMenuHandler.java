package by.javatr.ibank.view.impl.console.handler;

import by.javatr.ibank.Constants;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class UserMenuHandler extends MenuHandler {
    private final static String USER_PREFIX = "USER_";

    private long userId;

    public UserMenuHandler(BufferedReader reader, long userId) {
        super(reader);
        this.userId = userId;
        isNotExit = true;
    }

    @Override
    public void run() throws IOException {
        print(controller.executeTask(
                String.valueOf(Constants.CommandName.USER_HELP) +
                        Constants.REQUEST_DELIMITER));

        print(controller.executeTask(
                String.valueOf(Constants.CommandName.USER_MONTH)+
                        Constants.REQUEST_DELIMITER + userId +
                        Constants.REQUEST_DELIMITER));

        while (isNotExit){
            String choice = USER_PREFIX + reader.readLine() +
                        Constants.REQUEST_DELIMITER;

            String command = choice.substring(0,
                        choice.indexOf(Constants.REQUEST_DELIMITER));

            String request =  command + Constants.REQUEST_DELIMITER + userId +
                        Constants.REQUEST_DELIMITER + choice.substring(command.length());

            if(command.equalsIgnoreCase(
                        String.valueOf(Constants.CommandName.USER_EXIT))){

                print(controller.executeTask(request));
                isNotExit = false;
            } else {
                print(controller.executeTask(request));
            }

        }
    }
}
