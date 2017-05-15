import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PioneerTest
{
    private static List<String> dataList;
    private static LazerBoard lazerBeamVO = new LazerBoard();
    public PioneerTest()
    {
        // TODO Auto-generated constructor stub

    }


    private void readFile(String arg) throws IOException,URISyntaxException{
       // PioneerTest.dataList=Files.readAllLines(Paths.get(PioneerTest.class.getClassLoader().getResource("LazerBeamInputFile").toURI()), StandardCharsets.US_ASCII);
        dataList=Files.readAllLines(Paths.get(arg), StandardCharsets.US_ASCII);

    }

    private void parseInput(){

        //Board Domensions
        lazerBeamVO.setBoardXCoordinate(Integer.parseInt(  dataList.get(0).split(",") [0]) );
        lazerBeamVO.setBoardYCoordinate(Integer.parseInt(  dataList.get(0).split(",") [1]) );
        //Input LazerBeam dimensions and direction
        String lazerBeamDimensionsString= dataList.get(dataList.size()-2);
        lazerBeamVO.setLazerBeamXCoordinate(Integer.parseInt( lazerBeamDimensionsString.split(",") [0]));
        lazerBeamVO.setLazerBeamYCoordinate(Integer.parseInt( lazerBeamDimensionsString.split(",") [1].substring(0, lazerBeamDimensionsString.split(",") [1].length()-1)));
        lazerBeamVO.setLazerBeamDirection(String.valueOf(lazerBeamDimensionsString.charAt(lazerBeamDimensionsString.length()-1)));
        //Mirror
        int mirrorCooridnateStart = 2;
        int mirrorCoordinateEnd = dataList.size()-4;
        List<Mirror> mirrorList=  new ArrayList<Mirror>();
        for(int i=mirrorCooridnateStart;i<=mirrorCoordinateEnd;i++){
            Mirror mirror=null;
            String mirrorTempString =  dataList.get(i).split(",")[1];
          //get last 2 chars . if last but one is number then there is no mirrorReflectionSide .
            if(  Character.isLetter( mirrorTempString.charAt(mirrorTempString.length()-2 )) ){
                mirror=new Mirror(String.valueOf(mirrorTempString.charAt(mirrorTempString.length()-2 )) ,String.valueOf(mirrorTempString.charAt(mirrorTempString.length()-1 )) );//direction and reflection both
                mirror.setMirrorYCoordinate(Integer.parseInt(mirrorTempString.substring(0, mirrorTempString.length()-2)) );
            }else{
                mirror=new Mirror(String.valueOf( mirrorTempString.charAt(mirrorTempString.length()-1 )));//only direction
                mirror.setMirrorYCoordinate(Integer.parseInt(mirrorTempString.substring(0, mirrorTempString.length()-1)) );
            }
            mirror.setMirrorXCoordinate(Integer.parseInt( dataList.get(i).split(",")[0])  );
            mirrorList.add(mirror);
        }
        lazerBeamVO.setMirrorList(mirrorList);
    }

    public static void main(String[] args)
    {

        if (args.length == 0) {
            System.out.println("please give input file.");
            return;
        }

        try{
            PioneerTest pioneerTest = new PioneerTest();

            // Read input file
            pioneerTest.readFile(args[0]);
            //Validate input file
            pioneerTest.validateInput();
            //Parse file and set values to VO
            pioneerTest.parseInput();
            //Validate board and lazer beam coordinates
            pioneerTest.validateLazerBeamVO();
            //Validate Mirror Coordinates
            pioneerTest.validateMirrorDimensions();
            System.out.println(lazerBeamVO.toString());
            //create a 2 dimensional array of board and add mirrors as per the coordinates
            Mirror[][] mirrorMaze = new Mirror[lazerBeamVO.getBoardXCoordinate()][lazerBeamVO.getBoardYCoordinate()];
            for(Mirror mirror:lazerBeamVO.getMirrorList()){
                mirrorMaze[mirror.getMirrorXCoordinate()][mirror.getMirrorYCoordinate()] = mirror;
            }

            mazePath(mirrorMaze, lazerBeamVO.getLazerBeamXCoordinate(), lazerBeamVO.getLazerBeamYCoordinate(), lazerBeamVO.getLazerBeamDirection());



        }
        catch(IOException ioe){
            System.out.println("Error Reading file");
            ioe.printStackTrace();
        }catch(URISyntaxException urie){
            System.out.println("Error getting file path");
            urie.printStackTrace();
        }catch(RuntimeException re){
            re.printStackTrace();
        }


    }

    private void validateMirrorDimensions() {
        //mirror dimension X and Y cordinates must be boards cordinates-1.
        for(Mirror mirror :lazerBeamVO.getMirrorList()){

            if(mirror.getMirrorXCoordinate()>=lazerBeamVO.getBoardXCoordinate()  ||
                mirror.getMirrorYCoordinate()>=lazerBeamVO.getBoardYCoordinate() ){
                throw new RuntimeException(" Mirror input dimensions(both X and Y) must be 1 less than the board dimensions");
            }
        }

        //
    }

    private void validateLazerBeamVO(){
        //lazer beam dimensions must be board dimensions-1 . assumption:-board coordinates start from 0.
        if(lazerBeamVO.getLazerBeamXCoordinate()>=lazerBeamVO.getBoardXCoordinate()  ||
            lazerBeamVO.getLazerBeamYCoordinate()>=lazerBeamVO.getBoardYCoordinate() ){
            throw new RuntimeException(" lazer beam input dimensions(both X and Y) must be 1 less than the board dimensions");
        }
    }

    private void validateInput() throws RuntimeException{
        int dataListSize = dataList.size();
        //-1 must be there in line 1,size-3,size-1 . these positions are fixed.
        if(dataList==null||dataList.size()==0 || !"-1".equalsIgnoreCase(dataList.get(1))
            ||  !"-1".equalsIgnoreCase(dataList.get(dataListSize-1))
            ||  !"-1".equalsIgnoreCase( dataList.get(dataListSize-3) )
            ){

           throw new RuntimeException("Input file is invalid.Invalid -1 position.Blank lines at the end of file nor allowed");
        }

        //can not have more than one comma in each row .

        for(String line:dataList){
            if( !"-1".equalsIgnoreCase(line)&& line.split(",").length!=2){
                throw new RuntimeException("Input file is invalid.Can not have more than one comma in each line.Also blank lines not allowed");
            }

        }

        //board dimensions must be numbers
        try{
            Integer.parseInt(  dataList.get(0).split(",") [0]) ;
            Integer.parseInt(  dataList.get(0).split(",") [1]) ;
        }catch(NumberFormatException nfe){
            throw new RuntimeException("Invalid board dimensions.They must be numbers");
        }


        //lazer input dimensions must be numeric . and the last char must be V or H

        try{
            String lazerBeamDimensionsString= dataList.get(dataList.size()-2);
            Integer.parseInt( lazerBeamDimensionsString.split(",") [0]);
            Integer.parseInt( lazerBeamDimensionsString.split(",") [1].substring(0, lazerBeamDimensionsString.split(",") [1].length()-1));

            if(!"V".equalsIgnoreCase(String.valueOf(lazerBeamDimensionsString.charAt(lazerBeamDimensionsString.length()-1)))
                &&
                !"H".equalsIgnoreCase(String.valueOf(lazerBeamDimensionsString.charAt(lazerBeamDimensionsString.length()-1)))
                ){
                throw new RuntimeException("Invalid Lazer Beam Input direction.Must be V or H");
            }
        }catch(NumberFormatException nfe){
            throw new RuntimeException("Invalid Lazer Beam Input dimensions.Invalid coordinates");
        }
        //If last char is int then mirror direction is missing
        int mirrorCooridnateStart = 2;
        int mirrorCoordinateEnd = dataList.size()-4;
        for(int i=mirrorCooridnateStart;i<=mirrorCoordinateEnd;i++){
            if(!Character.isLetter(dataList.get(i).charAt(dataList.get(i).length()-1))){
                throw new RuntimeException("Mirror direction required.line must end with R/L");
            }

        }

    }



 // find the path of a laser in the maze.
    public static void mazePath(Mirror[][] board, int col, int row, String orientation) {
        // validate the input of the laser
        if (col < 0 || row < 0
                || col >= board.length
                || row >= board[0].length
                || (!orientation.equals("H") && !orientation.equals("V"))) {
            System.out.println("incorrect input");
            return;
        }

        System.out.println("Dimensions of board: " + board.length + " x " + board[0].length);

        // track the path of the laser
        List<Position> path = new ArrayList<Position>();
        String direction = "+"; // "+": increase step; "-": decrease step
        path.add(new Position(col, row, orientation, direction));


        // if last position is out of board, it is finished.
        Position last = path.get(path.size() - 1);
        while ((last.col >= 0 && last.col < board.length)
                && (last.row >= 0 && last.row < board[0].length)) {
            nextPosition(board, path);
            last = path.get(path.size() - 1);
        }

        // print the path from start to exit.
        System.out.println("Path of the laser: ");
        for (int i = 0; i < path.size() - 1; i++) {
            Position p = path.get(i);
            if(i==0){
              System.out.println("Starting position of lazer beam:"+p);
            }else if (i+2>path.size() - 1){
                System.out.println("Ending position of lazer beam:"+p);
            }else{
                System.out.println("LazerBeam next position:"+p);
            }

        }

    }


 // calculate next position of the laser
    public static void nextPosition(Mirror[][] board, List<Position> path) {
        Position prev = path.get(path.size() - 1);
        int prevCol = prev.col;
        int prevRow = prev.row;
        String prevOrient = prev.orientation;
        String prevDirection = prev.direction;
        int nextCol = -1;
        int nextRow = -1;
        String nextOrient = prevOrient;
        String nextDirection = prevDirection;

        if (prevOrient.equals("H")) {
            nextCol = prevCol + ((prevDirection.equals("+")) ? 1 : -1);
            nextRow = prevRow;
        }
        if (prevOrient.equals("V")) {
            nextRow = prevRow + ((prevDirection.equals("+")) ? 1 : -1);
            nextCol = prevCol;
        }

        if ((nextCol >= 0 && nextCol < board.length)
                && (nextRow >= 0 && nextRow < board[0].length)) {

            Mirror mirror = board[nextCol][nextRow];
            if (mirror != null) {
                if (mirror.direction.equals("R")) {

                    if (mirror.rightSide) {
                        if (prevOrient.equals("V") && prevDirection.equals("+")) {
                            nextOrient = "H";
                            nextDirection = "+";

                        }
                        if (prevOrient.equals("H") && prevDirection.equals("-")) {
                            nextOrient = "V";
                            nextDirection = "-";
                        }
                    }

                    if (mirror.leftSide) {
                        if (prevOrient.equals("V") && prevDirection.equals("-")) {
                            nextOrient = "H";
                            nextDirection = "-";

                        }
                        if (prevOrient.equals("H") && prevDirection.equals("+")) {
                            nextOrient = "V";
                            nextDirection = "+";
                        }
                    }
                }

                if (mirror.direction.equals("L")) {
                    if (mirror.rightSide) {
                        if (prevOrient.equals("V") && prevDirection.equals("-")) {
                            nextOrient = "H";
                            nextDirection = "+";

                        }
                        if (prevOrient.equals("H") && prevDirection.equals("-")) {
                            nextOrient = "V";
                            nextDirection = "+";
                        }
                    }

                    if (mirror.leftSide) {
                        if (prevOrient.equals("V") && prevDirection.equals("+")) {
                            nextOrient = "H";
                            nextDirection = "-";

                        }
                        if (prevOrient.equals("H") && prevDirection.equals("+")) {
                            nextOrient = "V";
                            nextDirection = "-";
                        }
                    }
                }
            }
        }

        Position next = new Position(nextCol, nextRow, nextOrient, nextDirection);
        for (Position p : path) {
            // check if the laser is trapped in the maze.
            // path is handy for this.
            if (p.equals(next))
                throw new RuntimeException("the laser is trapped in the maze.");
        }
        path.add(next);

    }
}
