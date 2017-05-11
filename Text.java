import java.awt.*;

/**
 * A string meant to be drawn on a canvas.
 *
 * @author Joaquin Paolo C. Jacinto and Luis Rainier T. Ligunas
 */

public class Text{
    private int x, y, size;
    private String text, font;

    /**
     * Stores x, y, string, font, and font size of the string.
     * 
     * @param x left-most x position of Text
     * @param y top-most y position of Text
     * @param text string that will be drawn
     * @param font string's font
     * @param size string's font size
     */
    public Text(int x, int y, String text, int size)  {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = Font.SANS_SERIF;
        this.size = size;
        
    }
    
    /**
     * Draws string on canvas.
     * 
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        g2d.setFont(new Font(font, Font.BOLD, size));
        g2d.drawString(text, x, y);
    }
}