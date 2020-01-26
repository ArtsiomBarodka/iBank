package by.javatr.ibank.view.impl.console.handler;

import by.javatr.ibank.controller.Controller;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public abstract class MenuHandler {
    protected boolean isNotExit;
    protected BufferedReader reader;
    protected Controller controller;

    public MenuHandler(BufferedReader reader) {
        this.reader = reader;
        controller = new Controller();
    }

    public abstract void run() throws IOException;

    protected void print(String response){
        System.out.println(response);
    }
}
