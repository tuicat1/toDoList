/**
 *	===============================================================================
 *	RectangleShape.java : A shape that is a rectangle.
 *  YOUR UPI: aedg256
 *	=============================================================================== */
import java.awt.*;
//Complete the RectangleShape

class RectangleShape extends Shape{
    public RectangleShape (){}
    public RectangleShape (Color c, Color bc, PathType pt){
        super(c,bc,pt);
    }
    public RectangleShape (int x, int y, int width, int height, int panelWidth, int panelHeight, Color c, Color bc, PathType pt){
        super(x,y,width,height,panelWidth,panelHeight,c,bc,pt);
    }

    public void draw(Graphics g) {
        System.out.println(color);
        System.out.println(borderColor);
        System.out.println(this.toString());

        // Draw the filled rectangle
        //g.setColor(color);
        //g.fillRect(x, y, width, height);

        // Draw the rectangle border
        //g.setColor(borderColor);
        //g.drawRect(x, y, width, height);

    }
    public boolean contains(Point mousePt) {
        // Check if the mouse point is within the bounds of the rectangle
        if (mousePt.x >= x && mousePt.x <= x + width && mousePt.y >= y && mousePt.y <= y + height) {
            return true;
        }
        return false;
    }
}