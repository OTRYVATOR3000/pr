package app;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.RRect;
import misc.*;

import java.util.ArrayList;
import java.util.List;

public class MyRect {

    Vector2d posA;
    Vector2d posB;
    Vector2d posP;


    public MyRect(Vector2d posA, Vector2d posB, Vector2d posP) {
        this.posA = posA;
        this.posB = posB;
        this.posP = posP;
    }

    Line cross(Line inLine) {
        // создаём линию
        Line line = new Line((posA), (posB));
        // рассчитываем расстояние от прямой до точки
        double dist = line.getDistance((posP));
        // рассчитываем векторы для векторного умножения
        Vector2d AB = Vector2d.subtract((posB), (posA));
        Vector2d AP = Vector2d.subtract((posP), (posA));
        // определяем направление смещения
        double direction = Math.signum(AB.cross(AP));
        // получаем вектор смещения
        Vector2d offset = AB.rotated(Math.PI / 2 * direction).norm().mul(dist);
        // находим координаты вторых двух вершин прямоугольника
        Vector2d posC = Vector2d.sum(posB, offset);
        Vector2d posD = Vector2d.sum(posA, offset);

        List<Vector2d> crossedPoints = new ArrayList<>();

        Line AD = new Line(posA,posD);
        Vector2d c = AD.cross(inLine);
        if (c!=null&&AD.contains(c))
            crossedPoints.add(c);

        //ToDo: для трёх остальных сторон

        if (crossedPoints.size() != 2) {
            return null;
        } else
            return new Line(crossedPoints.get(0), crossedPoints.get(1));
    }

    public void render(Canvas canvas, CoordinateSystem2i windowCS, CoordinateSystem2d taskCS) {
        try (Paint p = new Paint()) {
            // первая вершина
            Vector2i pointA = windowCS.getCoords(posA, taskCS);
            // вторая вершина
            Vector2i pointB = windowCS.getCoords(posB, taskCS);
            // точка на противоположной стороне
            Vector2i pointP = windowCS.getCoords(posP, taskCS);
            // создаём линию
            Line line = new Line(new Vector2d(pointA), new Vector2d(pointB));
            // рассчитываем расстояние от прямой до точки
            double dist = line.getDistance(new Vector2d(pointP));
            // рассчитываем векторы для векторного умножения
            Vector2d AB = Vector2d.subtract(new Vector2d(pointB), new Vector2d(pointA));
            Vector2d AP = Vector2d.subtract(new Vector2d(pointP), new Vector2d(pointA));
            // определяем направление смещения
            double direction = Math.signum(AB.cross(AP));
            // получаем вектор смещения
            Vector2i offset = AB.rotated(Math.PI / 2 * direction).norm().mul(dist).intVector();

            // находим координаты вторых двух вершин прямоугольника
            Vector2i pointC = Vector2i.sum(pointB, offset);
            Vector2i pointD = Vector2i.sum(pointA, offset);

            // рисуем его стороны
            canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, p);
            canvas.drawLine(pointB.x, pointB.y, pointC.x, pointC.y, p);
            canvas.drawLine(pointC.x, pointC.y, pointD.x, pointD.y, p);
            canvas.drawLine(pointD.x, pointD.y, pointA.x, pointA.y, p);

            // сохраняем цвет рисования
            int paintColor = p.getColor();
            // задаём красный цвет
            p.setColor(Misc.getColor(200, 255, 0, 0));
            canvas.drawRRect(RRect.makeXYWH(pointA.x - 4, pointA.y - 4, 8, 8, 4), p);
            canvas.drawRRect(RRect.makeXYWH(pointB.x - 4, pointB.y - 4, 8, 8, 4), p);
            canvas.drawRRect(RRect.makeXYWH(pointP.x - 4, pointP.y - 4, 8, 8, 4), p);
            // восстанавливаем исходный цвет рисования
            p.setColor(paintColor);


        }
    }
}
