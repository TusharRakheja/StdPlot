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
 * @author <i> Tushar </i>
 */
public class StdPlot2D {
    /**
     * Properties of the plot's canvas.
     */
    private int canvasHeight;           
    private int canvasWidth;
    private int xLower;
    private int yLower;
    private int xUpper;
    private int yUpper;
    private int xScale;
    private int yScale;
    private int xOffset;
    private int yOffset;
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
    public StdPlot2D(boolean[] provided, int[] values) {
        if (provided.length != 6) throw new IllegalArgumentException("You need 6 boolean values.");
        int current = 0;
        canvasWidth = (provided[0]) ? values[current++] : 680;
        canvasHeight = (provided[1]) ? values[current++] : 680;
        xLower = (provided[2]) ? values[current++] : 0;
        xUpper = (provided[3]) ? values[current++] : 1000;
        yLower = (provided[4]) ? values[current++] : 0;
        yUpper = (provided[5]) ? values[current++] : 1000;
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(xLower, xUpper);
        StdDraw.setYscale(yLower, yUpper);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line((xLower + xUpper)/2, yLower, (xLower + xUpper)/2, yUpper);
        StdDraw.line(xLower, (yLower + yUpper)/2, xUpper, (yLower + yUpper)/2);
    }
        
    public static void main(String[] args) {
        boolean[] providing = {true, true, true, true, true, true};
        int[] values = {600, 600, 0, 1000, 0, 1000};
        StdPlot2D plot = new StdPlot2D(providing, values);
    }
}
