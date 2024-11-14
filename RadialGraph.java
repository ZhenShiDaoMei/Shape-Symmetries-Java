package geometry;

import java.util.*;

public class RadialGraph extends Shape {
    protected Point Center;
    protected List<Point> Neighbors = null;

    public RadialGraph(Point center, List<Point> neighbors){
        if(checkDistanceEqual(center,neighbors)){
            this.Neighbors = neighbors;
            this.Center = center;
        }
        else {
            throw new IllegalArgumentException("Not all points are equally far from center.");
        }
    }

    public RadialGraph(Point center) {
        this.Center = center;
    }
    protected static boolean checkDistanceEqual(Point center, List<Point> neighbors) {
        if(neighbors == null){
            return false;
        }
        if(neighbors.size() == 1){
            return true;
        }
        for(int i = 0; i < neighbors.size()-1 ; i++){
            double FirstX = neighbors.get(i).x - center.x;
            double FirstY = neighbors.get(i).y - center.y;
            double SecondX = neighbors.get(i+1).x - center.x;
            double SecondY = neighbors.get(i+1).y - center.y;
            double firstDistance = Math.sqrt(Math.pow(FirstX,2) + Math.pow(FirstY,2));
            double secondDistance = Math.sqrt(Math.pow(SecondX,2) + Math.pow(SecondY,2));
            if(firstDistance != secondDistance){
                return false;
            }
        }
    return true;
    }
    /*
    Without the matrix notation used in linear algebra, this simply means that such a
    rotation transforms the point (x, y) to (x cos θ − y sin θ, x sin θ + y cos θ).
     */
    @Override
    public RadialGraph rotateBy(int degrees) {
        double radian = Math.toRadians(degrees);
        for(int i = 0; i < Neighbors.size() ; i++){
            double newNeighborsX = (Neighbors.get(i).x * Math.cos(radian)) - (Neighbors.get(i).y * Math.sin(radian));
            double newNeighborsY = (Neighbors.get(i).x * Math.sin(radian)) + (Neighbors.get(i).y * Math.cos(radian));
            Point tempNeighbor = new Point(Neighbors.get(i).name, newNeighborsX, newNeighborsY);
            Neighbors.set(i, tempNeighbor);
        }
        return new RadialGraph(Center,Neighbors);
    }

    @Override
    public RadialGraph translateBy(double x, double y) {
        double addToX = x;
        double addToY = y;
        double newCenterX = Center.x + addToX;
        double newCenterY = Center.y + addToY;
        Center = new Point(Center.name, newCenterX,newCenterY);
        for (int i = 0 ; i < Neighbors.size() ; i++){
            Double newNeighborX = Neighbors.get(i).x + addToX;
            Double newNeighborY = Neighbors.get(i).y + addToY;
            Point tempNeighbor = new Point(Neighbors.get(i).name, newNeighborX, newNeighborY);
            Neighbors.set(i,tempNeighbor);
        }
        return new RadialGraph(Center, Neighbors);
    }

    @Override
    public String toString() {
        String temp = "";
        temp = "[" + Center.toString();
        Point saved = Center;
        List<Point> savedNeigh = Neighbors;
        List<Double> angles = new ArrayList<Double>();
        if (Neighbors != null) {
            for (Point neighbor : Neighbors) {
                // x = 0, y = -1
                double tempAngle = Math.atan((neighbor.y - Center.y) / (neighbor.x - Center.x));
                double tempDegrees = Math.toDegrees(tempAngle);
                if (neighbor.x > Center.x && neighbor.y >= Center.x &&tempDegrees>=0) {
                    tempDegrees = Math.abs(tempDegrees);
                }
                if (neighbor.x <= Center.x && neighbor.y > Center.x &&tempDegrees<=0) {
                    tempDegrees = Math.abs(tempDegrees);
                    tempDegrees = tempDegrees + 90;
                }
                if (neighbor.x <= Center.x && neighbor.y <= Center.x) {
                    tempDegrees = Math.abs(tempDegrees);
                    tempDegrees = tempDegrees + 180;
                }
                if (neighbor.x > Center.x && neighbor.y < Center.x) {
                    tempDegrees = Math.abs(tempDegrees);
                    tempDegrees = tempDegrees + 270;
                }
                angles.add(tempDegrees);
            }
            for (int i = 0; i < Neighbors.size(); i++) {
                for (int j = i + 1; j < Neighbors.size(); j++) {
                    double tmp = 0;
                    Point tempNeighbor;
                    if (angles.get(i) > angles.get(j)) {
                        tmp = angles.get(i);
                        angles.set(i, angles.get(j));
                        angles.set(j, tmp);
                        tempNeighbor = Neighbors.get(i);
                        Neighbors.set(i, Neighbors.get(j));
                        Neighbors.set(j, tempNeighbor);
                    }
                }
                String temp2 = temp;
                temp = temp2 + ";" + Neighbors.get(i).toString();
            }
        }
        temp = temp + "]";
        Center = saved;
        Neighbors = savedNeigh;
        return temp;
    }

    @Override
    public Point center() {
        return Center;
    }

    /* Driver method given to you as an outline for testing your code. You can modify this as you want, but please keep
     * in mind that the lines already provided here as expected to work exactly as they are (some lines have additional
     * explanation of what is expected) */
    public static void main(String... args) {
        Point center = new Point("center", 0, 0);
        Point east = new Point("east", 1, 0);
        Point west = new Point("west", -1, 0);
        Point north = new Point("north", 0, 1);
        Point south = new Point("south", 0, -1);
        Point toofarsouth = new Point("south", 0, -2);

        // A single node is a valid radial graph.
        RadialGraph lonely = new RadialGraph(center);

        // Must print: [(center, 0.0, 0.0)]
        System.out.println(lonely);


        // This line must throw IllegalArgumentException, since the edges will not be of the same length
        RadialGraph nope = new RadialGraph(center, Arrays.asList(north, toofarsouth, east, west));

        Shape g = new RadialGraph(center, Arrays.asList(north, south, east, west));

        // Must follow the documentation in the Shape abstract class, and print the following string:
        // [(center, 0.0, 0.0); (east, 1.0, 0.0); (north, 0.0, 1.0); (west, -1.0, 0.0); (south, 0.0, -1.0)]
        System.out.println(g);

        // After this counterclockwise rotation by 90 degrees, "north" must be at (-1, 0), and similarly for all the
        // other radial points. The center, however, must remain exactly where it was.
        g = g.rotateBy(90);
        System.out.println(g);
        g = g.translateBy(2,3);
        System.out.println(g);
        // you should similarly add tests for the translateBy(x, y) method
    }
}
