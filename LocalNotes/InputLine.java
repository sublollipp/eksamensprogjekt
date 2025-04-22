import processing.core.*;
import java.util.ArrayList;

public class InputLine extends ClickableObject implements TypeResponsive {
    public String text = "";
    private int lineHeight;
    private int cursorX = 0;
    private int xMargin = 5, yMargin = 1;
    private boolean focused = true;

    public InputLine(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h, p.TEXT);
        lineHeight = h - 2 * yMargin;
    }

    public void focus() {
        focused = true; // Setter til focused
    }

    public void keyPressed() {
        p.textSize(lineHeight);
        if (!focused) return;
        if (p.key == p.BACKSPACE) {
            if (cursorX > 0) {
                text = text.substring(0, cursorX - 1) + text.substring(cursorX);
                cursorX --;
            }
        } else if (p.key == p.ENTER || p.key == p.RETURN) {
            return;
        } else if (p.key == p.CODED) {
            return;
        } else if (p.key >= 32) {
            String newText = text.substring(0, cursorX) + p.key + text.substring(cursorX);
            if (p.textWidth(newText) > w - xMargin * 2) return;
            text = newText;
            cursorX += 1;
        }
    }

    public void onMouseReleased() {
        super.onMouseReleased();
        if (!hovered) focused = false;
    }

    public void onClick() {
        focused = true;
        p.textSize(lineHeight);
        float mindist = Float.MAX_VALUE;
        int newCursorX = 0;
        for (int i = 0; i <= text.length(); i++) {
            float newDist = p.abs((x + xMargin + p.textWidth(text.substring(0, i))) - p.mouseX);
            if (newDist < mindist) {
                mindist = newDist;
                newCursorX = i;
            }
        }
        cursorX = newCursorX;
    }

    public void draw() {

        p.stroke(0);
        p.strokeWeight(2);
        p.fill(255);
        super.draw();
        p.fill(0);
        p.stroke(0);
        p.strokeWeight(2);
        p.textAlign(p.LEFT, p.TOP);
        p.textSize(lineHeight);
        int cursorPosX = x + xMargin + (int) p.textWidth(text.substring(0, cursorX));
        p.text(text, x + xMargin, y + yMargin);

        if (focused) {
            p.line(cursorPosX, y + yMargin, cursorPosX, y + yMargin + lineHeight);
        }
    }
}
