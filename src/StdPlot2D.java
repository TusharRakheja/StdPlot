/*******************************************************************************
 * A basic 2D plotting library with an intuitive API. 
 * Uses StdDraw.java from <a href = http://introcs.cs.princeton.edu/java/stdlib/StdDraw.java.html>here</a>.
 * 
 * The purpose of this library is to provide a simple plotting tool for 
 * experimentation and testing. My real reason for making this library is to 
 * see how far StdDraw.java, a basic drawing library, can be taken.
 * 
 * @author <i> Tushar </i> 
 ******************************************************************************/
/**
 * The class containing the plot.
 * @author <i> Tushar </i>
 */
public class StdPlot2D {
    /**
     * Properties of the plot's canvas.
     */
    private int canvasHeight;           
    private int canvasWidth;
    private double xLower;
    private double yLower;
    private double xUpper;
    private double yUpper;
    private double xOffset;
    private double yOffset;
    private double yAxisOffset;
    private double xAxisOffset;
    private double trueXLower;
    private double trueXUpper;
    private double trueYUpper;
    private double trueYLower;
    private double majorXDiv;
    private double minorXDiv;
    private double majorYDiv;
    private double minorYDiv;
    /**
     * Sets up a canvas with the given values.
     * @param values Order of values: 
     * <ol start = "0"> 
     *      <li> canvasWidth - The width of the canvas. </li>
     *      <li> canvasHeight - The height of the canvas. </li>
     *      <li> xLower - The lower limit of the range of x values. </li>
     *      <li> xUpper - The upper limit of the range of x values. </li>
     *      <li> yLower - The lower limit of the range of y values. </li>
     *      <li> yUpper - The upper limit of the range of y values. </li>
     * </ol>
     * @param provided provided[i] <=> The i<sup>th</sup> parameter has been provided.            
     */
    public StdPlot2D(boolean[] provided, double[] values) {
        if (provided.length != 6) throw new IllegalArgumentException("You need 6 boolean values.");
        int current = 0;
        canvasWidth = (provided[0]) ? (int) values[current++] : 680;
        canvasHeight = (provided[1]) ? (int) values[current++] : 680;
        xLower = (provided[2]) ? values[current++] : 0;
        xUpper = (provided[3]) ? values[current++] : 1000;
        yLower = (provided[4]) ? values[current++] : 0;
        yUpper = (provided[5]) ? values[current++] : 1000;
        majorXDiv = majorYDiv = 100;
        minorXDiv = minorYDiv = 10;
        setScale();
    }
    /**
     * Sets the scale for the X and Y axes.
     */
    public void setScale() {
        boolean xRev = false;
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        if (xLower > 0 && xUpper > 0) {                                          // Kink needed.
            trueXLower = (xLower*canvasWidth - 25*xUpper) / (canvasWidth - 25);
            trueXUpper = xUpper;
        }
        else if (xLower == 0 && xUpper > 0) {                                    // No kink needed.
            trueXLower = (xLower*canvasWidth - 15*xUpper) / (canvasWidth - 15);
            trueXUpper = xUpper;
        }
        else if (xLower < 0 && xUpper == 0) {                                    // No kink needed.
            trueXLower = xLower;
            //trueXUpper = (xUpper*canvasWidth - 15*xLower) / (canvasWidth - 15);
            trueXUpper = (xUpper*canvasWidth + 15*xLower) / (canvasWidth - 15);
            xRev = true;
        }
        else if (xLower < 0 && xUpper < 0) {                                     // Kink needed.
            trueXLower = xLower;
            trueXUpper = ((-xUpper*canvasWidth) + 25*xLower) / (canvasWidth - 25);
            xRev = true;
        }
        StdDraw.setXscale(trueXLower, trueXUpper);
        if (yLower > 0 && yUpper > 0) {
            trueYLower = (yLower*canvasHeight - 25*yUpper) / (canvasHeight - 25);
            trueYUpper = yUpper;
            StdDraw.setYscale(trueYLower, trueYUpper);
            StdDraw.line(trueXLower, yLower, trueXUpper, yLower);
        }
        else if (yLower == 0 && yUpper > 0) {
            trueYLower = (yLower*canvasHeight - 15*yUpper) / (canvasHeight - 15);
            trueYUpper = yUpper;   
            StdDraw.setYscale(trueYLower, trueYUpper);         
            StdDraw.line(trueXLower, yLower, trueXUpper, yLower);
        }
        else if (yLower < 0 && yUpper == 0) {
            trueYLower = yLower;
            trueYUpper = (yUpper*canvasHeight + 15*yLower) / (canvasHeight - 15);  
            StdDraw.setYscale(trueYLower, trueYUpper); 
            StdDraw.line(trueXLower, yUpper, trueXUpper, yUpper);
        }
        else if (yLower < 0 && yUpper < 0) {
            trueYLower = yLower;
            trueYUpper = (-yUpper*canvasHeight + 25*yLower) / (canvasHeight - 25);
            StdDraw.setYscale(trueYLower, trueYUpper);
            StdDraw.line(trueXLower, yUpper, trueXUpper, yUpper);
        }
        if (xRev) 
            StdDraw.line(xUpper, trueYLower, xUpper, trueYUpper);
        else
            StdDraw.line(xLower, trueYLower, xLower, trueYUpper);           
        //StdDraw.line(trueXLower, yLower, trueXUpper, yLower);

    }
    /**
     * Set the width represented by 1 major division along the x axis.
     * @param majorDiv 
     */
    private void majorDiv(double majorDiv) {
        this.majorXDiv = majorDiv;
    }
    /**
     * For testing.
     * @param args Conventional.
     */
    public static void main(String[] args) {
        boolean[] providing = {false, false, true, true, true, true};
        double[] values = {-200, -10, -400, -20};
        StdPlot2D plot = new StdPlot2D(providing, values);
    }
}
