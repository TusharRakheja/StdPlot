
import java.awt.Font;

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
    private double majorXDiv;
    private double minorXDiv;
    private double majorYDiv;
    private double minorYDiv;
    private double xScale;
    private double yScale;
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
        canvasWidth = (provided[0]) ? (int) values[current++] : 800;
        canvasHeight = (provided[1]) ? (int) values[current++] : 600;
        xLower = (provided[2]) ? values[current++] : 0;
        xUpper = (provided[3]) ? values[current++] : 1000;
        yLower = (provided[4]) ? values[current++] : 0;
        yUpper = (provided[5]) ? values[current++] : 1000;
        majorXDiv = majorYDiv = 100;
        minorXDiv = minorYDiv = 10;
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(xLower, (xUpper - xLower)*(200)/(canvasWidth - 200) + xUpper);
        StdDraw.setYscale(yLower, yUpper);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        
        for (double x = xLower; x <= xUpper; x += minorXDiv) {
            StdDraw.line(x, yLower, x, yUpper);
        }
        for (double y = yLower; y <= yUpper; y += minorYDiv) {
            StdDraw.line(xLower, y, xUpper, y);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        for (double x = xLower; x <= xUpper; x += majorXDiv) {
            StdDraw.line(x, yLower, x, yUpper);
        }
        for (double y = yLower; y <= yUpper; y += majorYDiv) {
            StdDraw.line(xLower, y, xUpper, y);
        }        
        xScale = (xUpper - xLower)/(canvasWidth - 200);
        yScale = (yUpper - yLower)/(canvasHeight);
        StdDraw.setFont(new Font("Sans-Serif", Font.PLAIN, 13));
        StdDraw.setPenRadius(0.002);
        double textBoxWidth = 70;
        double textBoxHeight = 14;
        double coordinateBoxYOffset = 41;
        StdDraw.rectangle(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, textBoxWidth*xScale/2, textBoxHeight*yScale/2);
        StdDraw.rectangle(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - 2*coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, textBoxWidth*xScale/2, textBoxHeight*yScale/2);
        StdDraw.text(xUpper + (textBoxWidth - 10)*xScale, yUpper - coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, "x : ");
        StdDraw.text(xUpper + (textBoxWidth - 10)*xScale, yUpper - 2*coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, "y : ");
        boolean quit = false;
        double lastX = 0, lastY = 0, currentX, currentY, diffX, diffY;
        while (!quit) {
            
            while (StdDraw.mousePressed() && StdDraw.mouseX() > canvasWidth && StdDraw.mouseY() > canvasHeight) {
                quit = true;
                break;
            }
            currentX = StdDraw.mouseX();
            currentY = StdDraw.mouseY();
            diffX = currentX - lastX;
            diffY = currentY - lastY;
            if ((diffX < minorXDiv && diffX > 0) || (diffX < 0 && diffX > -minorXDiv)) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledRectangle(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, textBoxWidth*xScale/2, textBoxHeight*yScale/2);
                StdDraw.setPenColor(StdDraw.BLACK);                
                StdDraw.text(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, Double.toString(currentX));
            }
            if ((diffY < minorYDiv && diffY > 0) || (diffY < 0 && diffY > -minorYDiv)) { 
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledRectangle(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - 2*coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, textBoxWidth*xScale/2, textBoxHeight*yScale/2);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - 2*coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, Double.toString(currentY));
            }
            lastX = currentX;
            lastY = currentY;
        }
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
        double[] values = {200, 500, 40, 400};
        StdPlot2D plot = new StdPlot2D(providing, values);
    }
}
