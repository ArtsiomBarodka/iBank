package by.javatr.ibank.view.impl.console.handler;

import by.javatr.ibank.Constants;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class MainMenuHandler extends MenuHandler {
    private final static String ACCOUNT_PREFIX = "ACCOUNT_";

    public MainMenuHandler(BufferedReader reader) {
        super(reader);
        isNotExit = true;
    }

    @Override
    public void run() throws IOException {
        print(controller.executeTask(
                    String.valueOf(Constants.CommandName.ACCOUNT_HELP) +
                            Constants.REQUEST_DELIMITER));

        while (isNotExit) {
            String choice = ACCOUNT_PREFIX + reader.readLine() +
                        Constants.REQUEST_DELIMITER;

            String command = choice.substring(0,
                        choice.indexOf(Constants.REQUEST_DELIMITER));

            if (command.equalsIgnoreCase(
                        String.valueOf(Constants.CommandName.ACCOUNT_ENTER))) {

                String []response = controller.executeTask(choice)
                            .split(Constants.RESPONSE_DELIMITER);

                try {
                    String message = response[0];
                    long id = Long.parseLong(response[1]);
                    Constants.Role role = Constants.Role.valueOf(response[2]);
                    String login = response[3];

                    print(message);

                    if(role.equals(Constants.Role.ADMIN)){
                        new AdminMenuHandler(reader, login).run();
                    } else {
                        new UserMenuHandler(reader, id).run();
                    }

                } catch (Exception e){
                    print(response[0]);
                }

            } else if(command.equalsIgnoreCase(
                        String.valueOf(Constants.CommandName.ACCOUNT_EXIT))){

                print(controller.executeTask(choice));
                isNotExit = false;
            } else {
                print(controller.executeTask(choice));
            }

        }
    }
}
