import app.Application;
import io.github.humbleui.jwm.App;

/**
 * главный метод
 */
public class Main {
    /**
     * главный класс
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        App.start(Application ::new);
    }

}