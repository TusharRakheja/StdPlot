import java.lang.Math;
import java.awt.Color;
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
public final class StdPlot2D {
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
    private double textBoxWidth;
    private double textBoxHeight;
    private double coordinateBoxYOffset;
    private double pointCircleRadius;
    private double pointWidth;
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
     *      <li> majorXDiv - The width of interval represented by a major X division. </li>
     *      <li> minorXDiv - The width of interval represented by a minor X division. </li>
     *      <li> majorYDiv - The width of interval represented by a major Y division. </li>
     *      <li> majorYDiv - The width of interval represented by a minor Y division. </li>
     * </ol>
     * @param provided provided[i] <=> The i<sup>th</sup> parameter has been provided.            
     */
    public StdPlot2D(boolean[] provided, double[] values) {
        if (provided.length != 10) throw new IllegalArgumentException("You need 10 boolean values.");
        int current = 0;
        canvasWidth = (provided[0]) ? (int) values[current++] : 1360;
        canvasHeight = (provided[1]) ? (int) values[current++] : 670;
        xLower = (provided[2]) ? values[current++] : 0;
        xUpper = (provided[3]) ? values[current++] : 1000;
        yLower = (provided[4]) ? values[current++] : 0;
        yUpper = (provided[5]) ? values[current++] : 1000;
        majorXDiv = (provided[6]) ? values[current++] : 100;
        minorXDiv = (provided[7]) ? values[current++] : 10;
        majorYDiv = (provided[8]) ? values[current++] : 100;
        minorYDiv = (provided[9]) ? values[current++] : 10;
        setPointRadius(0.02);
        setGraph();
    }
    /**
     * Set up the graph.
     */
    public void setGraph() {
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
        textBoxWidth = 70;
        textBoxHeight = 14;
        coordinateBoxYOffset = 41;
        StdDraw.rectangle(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, textBoxWidth*xScale/2, textBoxHeight*yScale/2);
        StdDraw.rectangle(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - 2*coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, textBoxWidth*xScale/2, textBoxHeight*yScale/2);
        StdDraw.text(xUpper + (textBoxWidth - 10)*xScale, yUpper - coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, "x : ");
        StdDraw.text(xUpper + (textBoxWidth - 10)*xScale, yUpper - 2*coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, "y : ");
    }
    /**
     * Public graph loop. To be called if one wants to explore the current plot(s).
     */
    public void graphLoop() {
        boolean quit = false;
        double lastX = 0, lastY = 0, currentX, currentY, diffX, diffY;
        while (!quit) {
            while (StdDraw.mousePressed() && StdDraw.mouseX() > (xUpper + 200*xScale) && StdDraw.mouseY() > yUpper) {
                quit = true;
                break;
            }
            currentX = StdDraw.mouseX();
            currentY = StdDraw.mouseY();
            if (currentX <= xUpper) { 
                diffX = currentX - lastX;
                diffY = currentY - lastY;
                if ((diffX < minorXDiv && diffX > 0) || (diffX < 0 && diffX > -minorXDiv)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledRectangle(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, textBoxWidth*xScale/2, textBoxHeight*yScale/2);
                    StdDraw.setPenColor(StdDraw.BLACK);                
                    StdDraw.text(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, String.format("%.2f", currentX));
                }
                if ((diffY < minorYDiv && diffY > 0) || (diffY < 0 && diffY > -minorYDiv)) { 
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledRectangle(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - 2*coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, textBoxWidth*xScale/2, textBoxHeight*yScale/2);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.text(xUpper + (textBoxWidth)*xScale + textBoxWidth*xScale/2, yUpper - 2*coordinateBoxYOffset*yScale - textBoxHeight*yScale/2, String.format("%.2f", currentY));
                }
                lastX = currentX;
                lastY = currentY; 
            }
        }
    }
    /**
     * Plots the points (x<sub>i</sub>, y<sub>i</sub>) on the graph. 
     * @param x The array of x-coordinates.
     * @param y The array of y-coordinates.
     * @param str Draw points as a dot or a circle.
     */
    public void points(double[] x, double[] y, String str) {
        if (x.length != y.length) throw new IllegalArgumentException("There should be an equal number of x and y coordinates.");
        for (int i = 0; i < x.length; i++) {
            point(x[i], y[i], str);
        }
    }
    /**
     * Plots a point at x and y.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param dotOrCircle Draw point as a dot or a circle.
     */
    public void point(double x, double y, String dotOrCircle) {
        if (dotOrCircle.equals("dot"))
            StdDraw.point(x, y);
        else
            StdDraw.ellipse(x, y, pointCircleRadius*xScale, pointCircleRadius*yScale);
    }
    /**
     * Sets the color of the pen.
     * @param color
     */
    public void setPenColor(Color color) {
        StdDraw.setPenColor(color);
    }
    /**
     * Sets the width of the points.
     * @param radius Variable argument, doesn't need to be here.
     */
    public void setPointRadius(double... radius) {
        if (radius != null) pointCircleRadius = radius[0];
        else pointCircleRadius = (minorXDiv + minorYDiv) / 2;
    }
    /**
     * Set the width of the pen used to draw stuff.
     * @param width Variable width.
     */
    public void setPenWidth(double... width) {
        if (width != null)
            StdDraw.setPenRadius(width[0]);
        else StdDraw.setPenRadius();
    }   
    /**
     * Set the width represented by 1 major division along the x axis.
     * @param majorXDiv The width of a major X division. 
     */
    public void majorXDiv(double majorXDiv) {
        this.majorXDiv = majorXDiv;
    }
    /**
     * Set the width represented by 1 major division along the y axis.
     * @param majorYDiv The width of a major Y division.
     */
    public void majorYDiv(double majorYDiv) {
        this.majorYDiv = majorYDiv;
    }
    /**
     * Set the width represented by 1 minor division along the x axis.
     * @param minorXDiv The width of a minor X division.
     */
    public void minorXDiv(double minorXDiv) {
        this.minorXDiv = minorXDiv;
    }
    /**
     * Set the width represented by 1 minor division along the y axis.
     * @param minorYDiv The width of a minor Y division.
     */
    public void minorYDiv(double minorYDiv) {
        this.minorYDiv = minorYDiv;
    }
    /**
     * For testing.
     * @param args Conventional.
     */
    public static void main(String[] args) {
        boolean[] providing = {false, false, true, true, true, true, false, false, false, false};
        double[] values = {200, 500, 0, 400};
        StdPlot2D plot = new StdPlot2D(providing, values);
        plot.setGraph();
        double[] x = {200, 300, 400, 500};
        double[] y = {0, 100, 200, 300};
        plot.setPenWidth(0.002);
        plot.setPenColor(StdDraw.RED);
        plot.setPointRadius(2);
        //plot.points(x, y);
        for (double angle = 0; angle <= 2*Math.PI; angle += 0.01) 
            plot.point(350 + 50*Math.sin(angle), 200 + 40*Math.cos(angle), "dot");
        plot.graphLoop();
    }
}
