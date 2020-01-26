package by.javatr.ibank.view.impl.console;

import by.javatr.ibank.view.Viewer;
import by.javatr.ibank.view.impl.console.handler.MainMenuHandler;
import by.javatr.ibank.view.impl.console.handler.MenuHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class ConsoleViewerImpl implements Viewer {
    @Override
    public void view() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            MenuHandler menuHandler = new MainMenuHandler(reader);
            menuHandler.run();
        } catch (IOException e) {
            System.out.println("App error");
        }
    }
}
