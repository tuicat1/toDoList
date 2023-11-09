abstract class MyFrustumShape {
    protected int height;
    public MyFrustumShape(){
        height = 1;
    }
    public MyFrustumShape(int height){
        this.height = height;
    }
    public int getHeight(){
        return height;
    }
    abstract double getTopArea();
    abstract double getBottomArea();
    public double getVolume(){
        return height / 3.0 * (getTopArea() + getBottomArea() + Math.sqrt(getTopArea() * getBottomArea()));
    }
    public String toString(){
        return String.format("MyFrustumPyramid, volume=%.2f", getVolume());
    }
}
class MyFrustumPyramid extends MyFrustumShape{
    private int topSideLength;
    private int bottomSideLength;
    public MyFrustumPyramid(){
        topSideLength = 1;
        bottomSideLength = 1;
    }
    public MyFrustumPyramid(int topSideLength, int bottomSideLength, int height){
        super(height);
        this.topSideLength = topSideLength;
        this.bottomSideLength = bottomSideLength;

    }
    public double getTopArea(){
        return topSideLength * topSideLength;
    }
    public double getBottomArea(){
        return bottomSideLength * bottomSideLength;
    }
}
class MyFrustumCone extends MyFrustumShape {
    private int topRadius;
    private int bottomRadius;
    public MyFrustumCone(){
        topRadius = 1;
        bottomRadius = 1;
    }
    public MyFrustumCone(int topRadius, int bottomRadius, int height){
        super(height);
        this.topRadius = topRadius;
        this.bottomRadius = bottomRadius;
    }
    public double getTopArea(){
        return Math.PI * (topRadius * topRadius);
    }
    public double getBottomArea(){
        return Math.PI * (bottomRadius * bottomRadius);
    }
}