/**
 * Created by Gabriel on 1/10/2017.
 */
public class HospitalBuilderProgram {
    //vars
    double lastDist0 = 1000, lastDist1 = 1000, lastDist2 = 1000, lastDist3 = 1000;
    int mapNum = 0, y = 0, x = 0, townNum = 0, closestX0 = 0, closestX1 = 0, closestX2 = 0, closestX3 = 0, closestY0 = 0, closestY1 = 0, closestY2 = 0, closestY3 = 0;
    double lastCoordDist0 = 10000000, lastCoordDist1 = 10000000, lastCoordDist2 = 10000000, lastCoordDist3 = 10000000, totalDist0 = 0, totalDist1 = 0, totalDist2 = 0, totalDist3 = 0;

    private static byte[][][] maps = {{{2, 2}, {2, 8}, {5, 15}, {19, 1}, {17, 17}},
            {{1, 1}, {7, 19}, {13, 19}, {19, 7}, {19, 13}},
            {{0, 19}, {2, 19}, {4, 19}, {6, 19}, {18, 19}},
            {{7, 19}, {13, 19}, {19, 19}, {19, 13}, {19, 7}}};

    public double calculateDist(int xVal, int yVal){
        double distance0 = Math.sqrt((((maps[mapNum][0][0])-xVal)^2)+(((maps[mapNum][0][1])-yVal)^2));
        double distance1 = Math.sqrt((((maps[mapNum][1][0])-xVal)^2)+(((maps[mapNum][1][1])-yVal)^2));
        double distance2 = Math.sqrt((((maps[mapNum][2][0])-xVal)^2)+(((maps[mapNum][2][1])-yVal)^2));
        double distance3 = Math.sqrt((((maps[mapNum][3][0])-xVal)^2)+(((maps[mapNum][3][1])-yVal)^2));
        double distance4 = Math.sqrt((((maps[mapNum][4][0])-xVal)^2)+(((maps[mapNum][4][1])-yVal)^2));
        double distance = distance0 + distance1 + distance2 + distance3 + distance4;
        return distance;
    }

    public void main(String[] args) {


        //map 1
        for (int mapNum = 0; mapNum<4; mapNum++) {
            for (int y = 0; y<21; y++){
                for (int x = 0; x<21; x++){
                    if (mapNum == 0){
                        double coordDist0 = calculateDist(x,y);
                        if (coordDist0 < lastCoordDist0){
                            lastCoordDist0 = coordDist0;
                            closestX0 = x;
                            closestY0 = y;
                        }
                    }
                    else if (mapNum == 1){
                        double coordDist1 = calculateDist(x,y);
                        if (coordDist1 < lastCoordDist1){
                            lastCoordDist1 = coordDist1;
                            closestX1 = x;
                            closestY1 = y;
                        }
                    }
                    else if (mapNum == 2){
                        double coordDist2 = calculateDist(x,y);
                        if (coordDist2 < lastCoordDist2){
                            lastCoordDist2 = coordDist2;
                            closestX2 = x;
                            closestY2 = y;
                        }
                    }
                    else if (mapNum == 3){
                        double coordDist2 = calculateDist(x,y);
                        if (coordDist2 < lastCoordDist2){
                            lastCoordDist2 = coordDist2;
                            closestX3 = x;
                            closestY3 = y;
                        }
                    }

                }
            }
        }
    }
        System.out.println(closestX0+","+closestY0);
        System.out.println(closestX1+","+closestY1);
        System.out.println(closestX2+","+closestY2);
        System.out.println(closestX3+","+closestY3);
}
