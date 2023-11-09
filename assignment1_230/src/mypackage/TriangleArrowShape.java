/**
 *	===============================================================================
 *	StarShape.java : A shape that is a triangle arrow (i.e. upside down triangle).
 *  YOUR UPI: aedg256
 *	=============================================================================== */
import java.awt.*;
import java.util.*;
//Complete the TriangleArrowShape
class TriangleArrowShape extends Shape{
    public TriangleArrowShape(){}
    public TriangleArrowShape(Color c, Color bc, PathType pt){
        super(c, bc, pt);
    }
    public TriangleArrowShape(int x, int y, int width, int height, int panelWidth, int panelHeight, Color c, Color bc, PathType pt){
        super(x,y,width,height,panelWidth,panelHeight,c,bc,pt);
    }
    public void draw(Graphics g){
        System.out.println(color);
        System.out.println(borderColor);
        System.out.println(this.toString());

    }
}
