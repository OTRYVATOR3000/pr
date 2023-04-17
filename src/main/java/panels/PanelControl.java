package panels;

import app.Fonts;
import app.Point;
import app.Task;
import java.util.ArrayList;

import controls.*;
import dialogs.PanelInfo;
import io.github.humbleui.jwm.*;
import io.github.humbleui.skija.Canvas;
import misc.CoordinateSystem2i;
import misc.Vector2d;
import misc.Vector2i;

import java.util.List;

import static app.Application.PANEL_PADDING;
import static app.Colors.FIELD_BACKGROUND_COLOR;
import static app.Colors.FIELD_TEXT_COLOR;

/**
 * Панель управления
 */
public class PanelControl extends GridPanel {

    /**
     * Кнопка "решить"
     */
    private final Button solve;
    /**
     * Текст задания
     */
    MultiLineLabel task;
    /**
     * Заголовки
     */
    public List<Label> labels;
    /**
     * Поля ввода
     */
    public List<Input> inputs;

    /**
     * Панель управления
     *
     * @param window     окно
     * @param drawBG     флаг, нужно ли рисовать подложку
     * @param color      цвет подложки
     * @param padding    отступы
     * @param gridWidth  кол-во ячеек сетки по ширине
     * @param gridHeight кол-во ячеек сетки по высоте
     * @param gridX      координата в сетке x
     * @param gridY      координата в сетке y
     * @param colspan    кол-во колонок, занимаемых панелью
     * @param rowspan    кол-во строк, занимаемых панелью
     */
    public PanelControl(
            Window window, boolean drawBG, int color, int padding, int gridWidth, int gridHeight,
            int gridX, int gridY, int colspan, int rowspan
    ) {

        super(window, drawBG, color, padding, gridWidth, gridHeight, gridX, gridY, colspan, rowspan);

        int cGridHeight= 10;

        // создаём списки
        inputs = new ArrayList<>();
        labels = new ArrayList<>();
        buttons = new ArrayList<>();
        // задание
        task = new MultiLineLabel(
                window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 0, 0, 6, 2, Task.TASK_TEXT,
                false, true, Fonts.FONT12);
        // добавление вручную
        Label xLabel = new Label(window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 0, 2, 1, 1, "X", true, true);
        labels.add(xLabel);
        Input xField = InputFactory.getInput(window, false, FIELD_BACKGROUND_COLOR, PANEL_PADDING,
                6, cGridHeight, 1, 2, 2, 1, "0.0", true,
                FIELD_TEXT_COLOR, true);
        inputs.add(xField);
        Label yLabel = new Label(window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 3, 2, 1, 1, "Y", true, true);
        labels.add(yLabel);
        Input yField = InputFactory.getInput(window, false, FIELD_BACKGROUND_COLOR, PANEL_PADDING,
                6, cGridHeight, 4, 2, 2, 1, "0.0", true,
                FIELD_TEXT_COLOR, true);
        inputs.add(yField);
        buttons = new ArrayList<>();

        Button addToFirstSet = new Button(
                window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 0, 3, 3, 1, "Добавить в первое\nмножество",
                true, true, Fonts.FONT12);

        addToFirstSet.setOnClick(() -> {
            // если числа введены верно
            if (!xField.hasValidDoubleValue()) {
                PanelLog.warning("X координата введена неверно");
            } else if (!yField.hasValidDoubleValue())
                PanelLog.warning("Y координата введена неверно");
            else
                PanelRendering.task.addPoint(
                        new Vector2d(xField.doubleValue(), yField.doubleValue()), Point.PointSet.FIRST_SET
                );
        });

        buttons.add(addToFirstSet);



        Button addToFirstSet1 = new Button(
                window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 1, 6, 4, 1, "Задать первую точку прямоугольника",
                true, true, Fonts.FONT12);

        addToFirstSet1.setOnClick(() -> {

        });

        buttons.add(addToFirstSet1);
        Button addToFirstSet2 = new Button(
                window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 1, 7, 4, 1, "Случайный прямоугольник",
                true, true, Fonts.FONT12);

        addToFirstSet2.setOnClick(() -> {

        });

        buttons.add(addToFirstSet2);

        Button addToSecondSet = new Button(
                window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 3, 3, 3, 1, "Добавить во второе\nмножество",
                true, true, Fonts.FONT12);
        addToSecondSet.setOnClick(() -> {
            // если числа введены верно
            if (!xField.hasValidDoubleValue()) {
                PanelLog.warning("X координата введена неверно");
            } else if (!yField.hasValidDoubleValue())
                PanelLog.warning("Y координата введена неверно");
            else {
                PanelRendering.task.addPoint(
                        new Vector2d(xField.doubleValue(), yField.doubleValue()), Point.PointSet.SECOND_SET
                );
            }


        });
        buttons.add(addToSecondSet);
        // случайное добавление
        Label cntLabel = new Label(window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 0, 4, 1, 1, "Кол-во", true, true);
        labels.add(cntLabel);

        Input cntField = InputFactory.getInput(window, false, FIELD_BACKGROUND_COLOR, PANEL_PADDING,
                6, cGridHeight, 1, 4, 2, 1, "5", true,
                FIELD_TEXT_COLOR, true);
        inputs.add(cntField);

        Button addPoints = new Button(
                window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 3, 4, 3, 1, "Добавить\nслучайные точки",
                true, true, Fonts.FONT12);
        addPoints.setOnClick(() -> {
            // если числа введены верно
            if (!cntField.hasValidIntValue()) {
                PanelLog.warning("кол-во точек указано неверно");
            } else
                PanelRendering.task.addRandomPoints(cntField.intValue());
        });
        buttons.add(addPoints);

        // управление
        Button load = new Button(
                window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 0, 8, 3, 1, "Загрузить",
                true, true, Fonts.FONT12);
        load.setOnClick(() -> {
            PanelRendering.load();
            cancelTask();
        });
        buttons.add(load);

        Button save = new Button(
                window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 3, 8, 3, 1, "Сохранить",
                true, true, Fonts.FONT12);
        save.setOnClick(PanelRendering::save);
        buttons.add(save);

        Button clear = new Button(
                window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 0, 9, 3, 1, "Очистить",
                true, true, Fonts.FONT12);
        clear.setOnClick(() -> PanelRendering.task.clear());
        buttons.add(clear);

        solve = new Button(
                window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 3, 9, 3, 1, "Решить",
                true, true, Fonts.FONT12);
        solve.setOnClick(() -> {
            if (!PanelRendering.task.isSolved()) {
                PanelRendering.task.solve();
                String s = "Задача решена\n" +
                        "Пересечений: " + PanelRendering.task.getCrossed().size() / 2 + "\n" +
                        "Отдельных точек: " + PanelRendering.task.getSingle().size();
                PanelInfo.show(s + "\n\nНажмите Esc, чтобы вернуться");
                PanelLog.success(s);
                solve.text = "Сбросить";
            } else {
                cancelTask();
            }
            window.requestFrame();
        });
        buttons.add(solve);

        // добавление вручную
        Label xLabel2 = new Label(window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 0, 5, 1, 1, "X", true, true);
        labels.add(xLabel2);
        Input xField2 = InputFactory.getInput(window, false, FIELD_BACKGROUND_COLOR, PANEL_PADDING,
                6, cGridHeight, 1, 5, 2, 1, "0.0", true,
                FIELD_TEXT_COLOR, true);
        inputs.add(xField2);
        Label yLabel2 = new Label(window, false, backgroundColor, PANEL_PADDING,
                6, cGridHeight, 3, 5, 1, 1, "Y", true, true);
        labels.add(yLabel2);
        Input yField2 = InputFactory.getInput(window, false, FIELD_BACKGROUND_COLOR, PANEL_PADDING,
                6, cGridHeight, 4, 5, 2, 1, "0.0", true,
                FIELD_TEXT_COLOR, true);
        inputs.add(yField2);
    }

    /**
     * Обработчик событий
     *
     * @param e событие
     */
    @Override
    public void accept(Event e) {
        // вызываем обработчик предка
        super.accept(e);
        // событие движения мыши
        if (e instanceof EventMouseMove ee) {
            for (Input input : inputs)
                input.accept(ee);

            for (Button button : buttons) {
                if (lastWindowCS != null)
                    button.checkOver(lastWindowCS.getRelativePos(new Vector2i(ee)));
            }
            // событие нажатия мыши
        } else if (e instanceof EventMouseButton ee) {
            if (!lastInside || !ee.isPressed())
                return;

            Vector2i relPos = lastWindowCS.getRelativePos(lastMove);

            // пробуем кликнуть по всем кнопкам
            for (Button button : buttons) {
                button.click(relPos);
            }
            // перебираем поля ввода
            for (Input input : inputs) {
                // если клик внутри этого поля
                if (input.contains(relPos)) {
                    // переводим фокус на это поле ввода
                    input.setFocus();
                }
            }
            // перерисовываем окно
            window.requestFrame();
            // обработчик ввода текста
        } else if (e instanceof EventTextInput ee) {
            for (Input input : inputs) {
                if (input.isFocused()) {
                    input.accept(ee);
                }
            }
            // перерисовываем окно
            window.requestFrame();
            // обработчик ввода клавиш
        } else if (e instanceof EventKey ee) {
            for (Input input : inputs) {
                if (input.isFocused()) {
                    input.accept(ee);
                }
            }
            // перерисовываем окно
            window.requestFrame();
        }
    }

    /**
     * Метод под рисование в конкретной реализации
     *
     * @param canvas   область рисования
     * @param windowCS СК окна
     */
    @Override
    public void paintImpl(Canvas canvas, CoordinateSystem2i windowCS) {
        // выводим текст задачи
        task.paint(canvas, windowCS);

        // выводим кнопки
        for (Button button : buttons) {
            button.paint(canvas, windowCS);
        }
        // выводим поля ввода
        for (Input input : inputs) {
            input.paint(canvas, windowCS);
        }
        // выводим поля ввода
        for (Label label : labels) {
            label.paint(canvas, windowCS);
        }
    }
    /**
     * Кнопки
     */
    public List<Button> buttons;

    /**
     * Сброс решения задачи
     */
    private void cancelTask() {
        PanelRendering.task.cancel();
        // Задаём новый текст кнопке решения
        solve.text = "Решить";
    }
}
