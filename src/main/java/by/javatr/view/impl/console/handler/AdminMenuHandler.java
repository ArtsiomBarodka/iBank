package by.javatr.ibank.view.impl.console.handler;

import by.javatr.ibank.Constants;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class AdminMenuHandler extends MenuHandler {
    private final static String ADMIN_PREFIX = "ADMIN_";

    private String adminLogin;

    public AdminMenuHandler(BufferedReader reader, String adminLogin) {
        super(reader);
        this.adminLogin = adminLogin;
        isNotExit = true;
    }

    @Override
    public void run() throws IOException {
        print(controller.executeTask(
                    String.valueOf(Constants.CommandName.ADMIN_HELP) +
                            Constants.REQUEST_DELIMITER));

        while (isNotExit){
            String choice = ADMIN_PREFIX + reader.readLine() +
                        Constants.REQUEST_DELIMITER;

            String command = choice.substring(0,
                        choice.indexOf(Constants.REQUEST_DELIMITER));

            String request =  command + Constants.REQUEST_DELIMITER + adminLogin +
                        Constants.REQUEST_DELIMITER + choice.substring(command.length());

            if(command.equalsIgnoreCase(
                        String.valueOf(Constants.CommandName.ADMIN_EXIT))){

                print(controller.executeTask(request));
                isNotExit = false;
            } else {
                print(controller.executeTask(request));
            }

        }
    }
}
