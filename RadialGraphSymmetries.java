package geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RadialGraphSymmetries implements Symmetries<RadialGraph>{

    @Override
    public boolean areSymmetric(RadialGraph s1, RadialGraph s2) {
        if(s1.Neighbors.size() == s2.Neighbors.size()){
            int placeholder = -1;
            if(checkLength(s1,s2)){
                int counter = 0;
                for(int i =0; i < s1.Neighbors.size(); i++){
                    for(int j =0; j < s1.Neighbors.size(); j++){
                        if(s1.Neighbors.get(i).x == s2.Neighbors.get(j).x && s1.Neighbors.get(i).y == s2.Neighbors.get(j).y){
                            counter++;
                        }
                    }
                }
                if (counter == s1.Neighbors.size()){
                    return true;
                }

            }

        }
        return false;
    }


    @Override
    public List<RadialGraph> symmetriesOf(RadialGraph radialGraph) {
        List<RadialGraph> list = new ArrayList<RadialGraph>();
        RadialGraph tempGraph = radialGraph;
        for(int i = 0; i <359 ; i++){
            list.add(tempGraph);
            tempGraph.rotateBy(1);
        }
        return list;
    }

    protected static boolean checkLength(RadialGraph s1, RadialGraph s2){
        double firstDistance = Math.sqrt(Math.pow(s1.Neighbors.get(1).x - s1.Center.x,2) + Math.pow(s1.Neighbors.get(1).y - s1.Center.y,2));
        double secondDistance = Math.sqrt(Math.pow(s2.Neighbors.get(1).x - s2.Center.x,2) + Math.pow(s2.Neighbors.get(1).y - s2.Center.y,2));
        if(firstDistance == secondDistance){
            return true;
        }
        return false;
    }
}
