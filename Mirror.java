public class Mirror {
    private int mirrorXCoordinate;
    private int mirrorYCoordinate;
    private String mirrorDirection;
    private String mirrorReflectionSide=null;

    // direction
    public String direction;
    //which side is reflective
    public boolean rightSide;
    public boolean leftSide;

    public Mirror(String direct) {
        if (!direct.equals("R") && !direct.equals("L")) {
            throw new RuntimeException("The " + direct + " direction of mirror is not supported.");
        }

        direction = direct;
        rightSide = true;
        leftSide = true;
    }

    public Mirror(String direct, String side) {
        this(direct);

        if (!side.equals("R") && !side.equals("L")) {
            throw new RuntimeException("The side" + side + " of a mirror is not supported.");
        }
        if (side.equals("R")) {
            leftSide = false;
        }
        if (side.equals("L")) {
            rightSide = false;
        }

    }

    public int getMirrorXCoordinate()
    {
        return mirrorXCoordinate;
    }

    public void setMirrorXCoordinate(int mirrorXCoordinate)
    {
        this.mirrorXCoordinate = mirrorXCoordinate;
    }

    public int getMirrorYCoordinate()
    {
        return mirrorYCoordinate;
    }

    public void setMirrorYCoordinate(int mirrorYCoordinate)
    {
        this.mirrorYCoordinate = mirrorYCoordinate;
    }

    public String getMirrorDirection()
    {
        return mirrorDirection;
    }

    public void setMirrorDirection(String mirrorDirection)
    {
        this.mirrorDirection = mirrorDirection;
    }

    public String getMirrorReflectionSide()
    {
        return mirrorReflectionSide;
    }

    public void setMirrorReflectionSide(String mirrorReflectionSide)
    {
        this.mirrorReflectionSide = mirrorReflectionSide;
    }



}