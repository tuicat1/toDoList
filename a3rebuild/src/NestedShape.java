import java.awt.*;
import java.util.ArrayList;

public class NestedShape extends RectangleShape {
    private ArrayList<Shape> innerShapes;

    public Shape createInnerShape(PathType pt, ShapeType st){
        Shape innerShape;
        int innerWidth = this.width / 5;
        int innerHeight = this.height / 5;
        switch (st) {
            case RECTANGLE:
                innerShape = new RectangleShape(0, 0, innerWidth, innerHeight, this.width, this.height, this.color, this.borderColor, pt);
                break;
            case OVAL:
                innerShape = new OvalShape(0, 0, innerWidth, innerHeight, this.width, this.height, this.color, this.borderColor, pt);
                break;
            case NESTED:
                innerShape = new NestedShape(0, 0, innerWidth, innerHeight, this.width, this.height, this.color, this.borderColor, pt);
                break;
            default:
                return null;
        }
        innerShape.setParent(this);
        innerShapes.add(innerShape);
        return innerShape;

    }
    public NestedShape(){
        super();
        innerShapes = new ArrayList<>();
        createInnerShape(PathType.BOUNCING, ShapeType.RECTANGLE);
    }
    public NestedShape(int x, int y, int width, int height, int panelWidth, int panelHeight, Color fillColor, Color borderColor, PathType pathType) {
        super(x, y, width, height, panelWidth, panelHeight, fillColor, borderColor, pathType);
        innerShapes = new ArrayList<>();
        createInnerShape(PathType.BOUNCING, ShapeType.RECTANGLE);
    }
    public NestedShape(int width, int height) {
        super(0, 0, width, height, Shape.DEFAULT_PANEL_WIDTH, Shape.DEFAULT_PANEL_HEIGHT, Shape.DEFAULT_COLOR, Shape.DEFAULT_BORDER_COLOR, PathType.BOUNCING);
        innerShapes = new ArrayList<>();
    }
    public Shape getInnerShapeAt(int index) {
            return innerShapes.get(index);
    }

    public int getSize() {
        return innerShapes.size();
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        g.drawRect(getX(), getY(), getWidth(), getHeight());

        int x = getX();
        int y = getY();
        for (Shape innerShape : innerShapes) {
            g.translate(x, y);
        }

        for (Shape innerShape : innerShapes) {
            innerShape.draw(g);
            if (innerShape.isSelected()) {
                innerShape.drawHandles(g);
            }
            innerShape.drawString(g);
        }

        for (Shape innerShape : innerShapes) {
            g.translate(-x, -y);
        }
    }

    @Override
    public void move() {
        super.move(); 

        for (Shape innerShape : innerShapes) {
            innerShape.move();
        }
    }
    public int indexOf(Shape s){
        return innerShapes.indexOf(s);
    }
    public void addInnerShape(Shape s){
        s.setParent(this);
        innerShapes.add(s);
    }
    public void removeInnerShape(Shape s){
        s.setParent(null);
        innerShapes.remove(s);
    }
    public void removeInnerShapeAt(int index){
        if (index >= 0 && index < innerShapes.size()){
            Shape s = innerShapes.get(index);
            s.setParent(null);
            innerShapes.remove(index);
        }
    }
    public ArrayList<Shape> getAllInnerShapes(){
        return innerShapes;
    }
}