/**
 *	===============================================================================
 *	StarShape.java : A shape that is a rectangle with spotted circles.
 *  YOUR UPI: aedg256
 *	=============================================================================== */
import java.awt.*;
import java.util.*;
//Complete the SpottedRectangleShape


class SpottedRectangleShape extends RectangleShape {
    private ArrayList<Point> circleReferencePoints = new ArrayList<>();
    private final int CIRCLE_SIZE = 10;
    private final Color CIRCLE_COLOR = Color.BLACK;
    private final Random random = new Random(30);

    public SpottedRectangleShape() {
        super();
        generateRandomCircleReferencePoints(1);
    }

    public SpottedRectangleShape(Color c, Color bc, PathType pt) {
        super(c, bc, pt);
        generateRandomCircleReferencePoints(3);
    }

    public SpottedRectangleShape(int x, int y, int width, int height, int panelWidth, int panelHeight, Color c, Color bc, PathType pt) {
        super(x, y, width, height, panelWidth, panelHeight, c, bc, pt);
        generateRandomCircleReferencePoints(3);
    }

    public SpottedRectangleShape(Point... points) {
        generateRandomCircleReferencePoints(points.length);
        if (points.length > 0) {
            circleReferencePoints.clear();
            for (Point point : points) {
                circleReferencePoints.add(point);
            }
        }
    }

    private void generateRandomCircleReferencePoints(int numCircles) {
        for (int i = 0; i < numCircles; i++) {
            int circleX = random.nextInt(width - CIRCLE_SIZE);
            int circleY = random.nextInt(height - CIRCLE_SIZE);
            circleReferencePoints.add(new Point(circleX, circleY));
        }
    }

    public void draw(Graphics g) {
        super.draw(g);


        System.out.print("[");
        for (int i = 0; i < circleReferencePoints.size(); i++) {
            System.out.print(circleReferencePoints.get(i));
            if (i < circleReferencePoints.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }
}

