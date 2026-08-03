public class ClosedTour {

    static int[] horizontal = {2,1,-1,-2,-2,-1,1,2};
    static int[] vertical = {-1,-2,-2,-1,1,2,2,1};

    public static boolean closedTour(int startRow,int startCol,
                                     int endRow,int endCol){

        for(int i=0;i<8;i++){

            if(endRow+vertical[i]==startRow &&
               endCol+horizontal[i]==startCol)

                return true;
        }

        return false;
    }

    public static void main(String[] args){

        int startRow=0;
        int startCol=0;

        int endRow=1;
        int endCol=2;

        if(closedTour(startRow,startCol,endRow,endCol))
            System.out.println("Closed Tour");
        else
            System.out.println("Not a Closed Tour");
    }
}