package shared.graphics;


public class Graphics2d {

    private static final Point CENTER = new Point(0, 0);

    public static final Point rotateClockwise(Point point, double degrees) {
        return rotateClockwise(CENTER, point, degrees);
    }

    public static final Point rotateClockwise(Point center, Point point, double degrees) {
        double x = point.getX();
        double y = point.getY();
        double angle = (360 - degrees) * Math.PI / 180;
        double newX = center.getX() + (x * Math.cos(angle) - y * Math.sin(angle));
        double newY = center.getY() + (y * Math.cos(angle) + x * Math.sin(angle));
        return new Point(newX, newY);
    }

}
