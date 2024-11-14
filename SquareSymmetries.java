package geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SquareSymmetries implements Symmetries<Square>{

    @Override
    public boolean areSymmetric(Square s1, Square s2) {

        Double lengthS1 = Math.sqrt(Math.pow(s1.corners[1].x - s1.corners[0].x,2) + Math.pow(s1.corners[1].y - s1.corners[0].y,2));
        Double lengthS2 = Math.sqrt(Math.pow(s2.corners[1].x - s2.corners[0].x,2) + Math.pow(s2.corners[1].y - s2.corners[0].y,2));
        if(lengthS2.equals(lengthS1)){
            int counter = 0;
            Point[] tempCorners1 = s1.corners;
            Point[] tempCorners2 = s2.corners;
            for(int i =0; i < 4; i++){
                for(int j =0; j < 4; j++){
                    if(s1.corners[i].x == s2.corners[j].x && s1.corners[i].y == s2.corners[j].y){
                        counter++;
                    }
                }
            }
            if (counter == s1.corners.length){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Square> symmetriesOf(Square square) {
        List<Square> list = new ArrayList<Square>();
        Square tempSquare = square;
        for(int i = 0; i <4 ; i++){
            list.add(tempSquare);
            tempSquare.rotateBy(90);
        }
        Point tempCorner = tempSquare.corners[0];
        tempSquare.corners[0] = tempSquare.corners[3];
        tempSquare.corners[3] = tempCorner;
        tempCorner = tempSquare.corners[1];
        tempSquare.corners[1] = tempSquare.corners[2];
        tempSquare.corners[2] = tempCorner;
        for(int i = 0; i <4 ; i++){
            list.add(tempSquare);
            tempSquare.rotateBy(90);
        }
        return list;
    }
}
