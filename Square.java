package geometry;

import java.util.ArrayList;
import java.util.List;

public class Square extends Shape {
    protected Point Center;
    protected Point[] corners;

    /*
    public Square(Point[] corners){
        this.corners = corners;
        if(getDistance(corners[0],corners[1]) == getDistance(corners[1],corners[2]) && getDistance(corners[2],corners[3]) == getDistance(corners[3],corners[0]) ){
            double valueX = 0;
            double valueY = 0;
            if(corners[1].x == corners[2].x){
                valueY = (corners[1].y + corners[2].y)/2;
            }
            if(corners[1].y == corners[2].y) {
                valueX = (corners[1].x + corners[2].x) / 2;
            }
            if(corners[1].y == corners[0].y) {
                valueX = (corners[1].x + corners[0].x) / 2;
            }
            if(corners[1].x == corners[0].x) {
                valueY = (corners[1].y + corners[2].y) / 2;
            }
            Center = new Point("center", valueX, valueY);
        }else throw new IllegalArgumentException();
    }
    */
    protected static double getDistance(Point corner1, Point corner2){
            double distanceX = Math.abs(corner2.x - corner1.x);
            double distanceY = Math.abs(corner2.y - corner1.y);
            return Math.round(Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2)));

    }
    @Override
    public Point center() {
        return Center;
    }

    @Override
    public Square rotateBy(int degrees) {
        double radian = Math.toRadians(degrees);
        for(int i = 0; i < corners.length ; i++){
            double newCornerX = (corners[i].x * Math.cos(radian)) - (corners[i].y * Math.sin(radian));
            double newCornerY = (corners[i].x * Math.sin(radian)) + (corners[i].y * Math.cos(radian));
            Point tempCorner = new Point(corners[i].name, newCornerX, newCornerY);
            corners[i] = tempCorner;
        }
        return new Square(corners[0], corners[1], corners[2], corners[3]);
    }

    @Override
    public Shape translateBy(double x, double y) {
        for (int i = 0 ; i < corners.length  ; i++){
            double newCornerX = corners[i].x + x;
            double newCornerY = corners[i].y + y;
            Point tempCorner = new Point(corners[i].name, newCornerX, newCornerY);
            corners[i] = tempCorner;
        }
        return new Square(corners[0], corners[1], corners[2], corners[3]);
    }

    @Override
    public String toString() {
        String temp = "";
        temp = "[" + Center.toString();
        Point savedCenter = Center;
        Point[] savedCorners = corners;
        List<Double> angles = new ArrayList<Double>();
            for (Point corner : corners) {
                // x = 0, y = -1
                double tempAngle = Math.atan((corner.y - Center.y) / (corner.x - Center.x));
                double tempDegrees = Math.toDegrees(tempAngle);
                if (corner.x > Center.x && corner.y >= Center.x &&tempDegrees>=0) {
                    tempDegrees = Math.abs(tempDegrees);
                }
                if (corner.x <= Center.x && corner.y > Center.x &&tempDegrees<=0) {
                    tempDegrees = Math.abs(tempDegrees);
                    tempDegrees = tempDegrees + 90;
                }
                if (corner.x <= Center.x && corner.y <= Center.x) {
                    tempDegrees = Math.abs(tempDegrees);
                    tempDegrees = tempDegrees + 180;
                }
                if (corner.x > Center.x && corner.y < Center.x) {
                    tempDegrees = Math.abs(tempDegrees);
                    tempDegrees = tempDegrees + 270;
                }
                angles.add(tempDegrees);
            }
            for (int i = 0; i < corners.length ; i++) {
                for (int j = i + 1; j < corners.length ; j++) {
                    double tmp = 0;
                    if (angles.get(i) > angles.get(j)) {
                        tmp = angles.get(i);
                        angles.set(i, angles.get(j));
                        angles.set(j, tmp);
                        Point tempCorner = new Point(corners[i].name,corners[j].x,corners[j].y );
                        Point tempCorner2 = new Point(corners[j].name, corners[i].x, corners[i].y);
                        corners[i] = tempCorner;
                        corners[j] = tempCorner2;
                    }
                }
                String temp2 = temp;
                temp = temp2 + ";" + corners[i].toString();
            }
        temp = temp + "]";
        return temp;
    }

    public Square(Point a, Point b, Point c, Point d) {
        if(getDistance(a,b) == getDistance(b,c)){
            corners = new Point[]{a,b,c,d};
            double valueX = 0;
            double valueY = 0;
            if(corners[1].x == corners[2].x){
                valueY = (corners[1].y + corners[2].y)/2;
            }
            if(corners[1].y == corners[2].y) {
                valueX = (corners[1].x + corners[2].x) / 2;
            }
            if(corners[1].y == corners[0].y) {
                valueX = (corners[1].x + corners[0].x) / 2;
            }
            if(corners[1].x == corners[0].x) {
                valueY = (corners[1].y + corners[2].y) / 2;
            }
            Center = new Point("center", valueX, valueY);
        }
        else throw new IllegalArgumentException("Corners are not in the correct order.");
    }


}
