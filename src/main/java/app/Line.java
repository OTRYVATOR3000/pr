package app;

import misc.Vector2d;

/**
 * Класс линии
 */
public class Line {
    /**
     * Первая опорная точка
     */
    private final Vector2d pointA;
    /**
     * Вторая опорная точка
     */
    private final Vector2d pointB;
    /**
     * Первый коэффициент канонического уравнения прямой
     */
    private final double a;
    /**
     * Второй коэффициент канонического уравнения прямой
     */
    private final double c;
    /**
     * Третий коэффициент канонического уравнения прямой
     */
    private final double b;

    /**
     * Конструктор линии
     *
     * @param pointA первая опорная точка
     * @param pointB вторая опорная точка
     */
    public Line(Vector2d pointA, Vector2d pointB) {
        this.pointA = pointA;
        this.pointB = pointB;

        a = pointA.y - pointB.y;
        b = pointB.x - pointA.x;
        c = pointA.x * pointB.y - pointB.x * pointA.y;
    }

    /**
     * Получить расстояние до точки
     *
     * @param pos координаты точки
     * @return расстояние
     */
    public double getDistance(Vector2d pos) {
        return Math.abs(a * pos.x + b * pos.y + c) / Math.sqrt(a * a + b * b);
    }

    /**
     * Получить расстояние до точки
     *
     * @return расстояние
     */
    public double getDistance() {
        double dx = pointA.x - pointB.x;
        double dy = pointA.y - pointB.y;

        return Math.abs(dx * dx + dy * dy);
    }


    // ToDo: пересечение прямой this и прямой l
    // если прямые параллельны (или совпадают), то вернуть null
    public Vector2d cross(Line l) {
        // каноническое уравнение прямой a1x+b1y+c1=0;
        System.out.println(this.a); // a1
        System.out.println(this.b); // b1
        System.out.println(this.c); // c1

        // каноническое уравнение прямой (из аргуменитов) a2x+b2y+c2=0
        System.out.println(l.a); // a2
        System.out.println(l.b); // b2
        System.out.println(l.c); // c2



        return null;
    }

    // ToDo: правда ли что точка `o` лежит на отрезке [pointA; pointB]
    public boolean contains(Vector2d o) {
        double d = pointB.x;
        double d2 = pointA.x;
        double d3 = o.x;

        return true;
    }
}