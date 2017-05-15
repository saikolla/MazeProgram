import java.util.ArrayList;
import java.util.List;

public class LazerBoard
{
    private static final int propertyEndChar =-1;
    private int boardXCoordinate;
    private int boardYCoordinate;
    private List<Mirror> mirrorList=  new ArrayList<Mirror>();
    private int lazerBeamXCoordinate;
    private int lazerBeamYCoordinate;
    private String lazerBeamDirection;



    public int getBoardXCoordinate()
    {
        return boardXCoordinate;
    }
    public void setBoardXCoordinate(int boardXCoordinate)
    {
        this.boardXCoordinate = boardXCoordinate;
    }
    public int getBoardYCoordinate()
    {
        return boardYCoordinate;
    }
    public void setBoardYCoordinate(int boardYCoordinate)
    {
        this.boardYCoordinate = boardYCoordinate;
    }

    public List<Mirror> getMirrorList()
    {
        return mirrorList;
    }
    public void setMirrorList(List<Mirror> mirrorList)
    {
        this.mirrorList = mirrorList;
    }
    public int getLazerBeamXCoordinate()
    {
        return lazerBeamXCoordinate;
    }
    public void setLazerBeamXCoordinate(int lazerBeamXCoordinate)
    {
        this.lazerBeamXCoordinate = lazerBeamXCoordinate;
    }
    public int getLazerBeamYCoordinate()
    {
        return lazerBeamYCoordinate;
    }
    public void setLazerBeamYCoordinate(int lazerBeamYCoordinate)
    {
        this.lazerBeamYCoordinate = lazerBeamYCoordinate;
    }
    public String getLazerBeamDirection()
    {
        return lazerBeamDirection;
    }
    public void setLazerBeamDirection(String lazerBeamDirection)
    {
        this.lazerBeamDirection = lazerBeamDirection;
    }
    public static int getPropertyendchar()
    {
        return propertyEndChar;
    }
    @Override
    public String toString()
    {
        return "LazerBeamVO [boardXCoordinate=" + boardXCoordinate +",\n"+ " boardYCoordinate=" + boardYCoordinate
            +",\n"+ " mirrorCoordinatesList=" + mirrorList + ",\n"+ " lazerBeamXCoordinate=" + lazerBeamXCoordinate
            + ",\n"+" lazerBeamYCoordinate=" + lazerBeamYCoordinate +",\n"+ " lazerBeamDirection=" + lazerBeamDirection +",\n"+ "]";
    }

}
