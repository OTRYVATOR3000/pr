package app;

import io.github.humbleui.jwm.*;

import java.io.File;
import java.util.function.Consumer;

/**
 * класс приложения
 */
public class Application implements Consumer<Event> {
    // окно приложения
    /**
     * окно приложения
     */
    private final Window window;

    /**
     * конструктор приложения
     */
    public Application() {
        // создаём окно
        window = App.makeWindow();
        // задаём обработчиком событий текущий объект
        window.setEventListener(this);
        // делаем окно видимым
        // задаём размер окна
        window.setWindowSize(900, 900);
        // задаём его положение
        window.setWindowPosition(100, 100);
        // задаём иконку
        switch (Platform.CURRENT) {
            case WINDOWS -> window.setIcon(new File("src/main/resources/windows.ico"));
            case MACOS -> window.setIcon(new File("src/main/resources/macos.icns"));
        }
        window.setVisible(true);
        window.setTitle("Java 2D");
    }



    /**
     * Обработчик событий
     * @param e событие
     */
    @Override
    public void accept(Event e) {
        // если событие - это закрытие окна
        if (e instanceof EventWindowClose) {
            // завершаем работу приложения
            App.terminate();
        }else if (e instanceof EventWindowCloseRequest) {
            window.close();
        }
    }
}