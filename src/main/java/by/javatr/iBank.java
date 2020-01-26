package by.javatr.ibank;

import by.javatr.ibank.view.Viewer;
import by.javatr.ibank.view.impl.console.ConsoleViewerImpl;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class iBank {
    public static void main(String[] args) {
        Viewer viewer = new ConsoleViewerImpl();
        viewer.view();
    }
}
