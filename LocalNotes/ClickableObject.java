import processing.core.*;

public class ClickableObject extends GraphicObject{

    protected boolean hovered = false, mouseHeld = false;

    protected ClickableObject(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
    }

    public void onMouseClicked() {
        if (hovered) {
            mouseHeld = true;
        }
    }

    public void onMouseReleased() {
        if (hovered && mouseHeld) {
            onClick();
        }
    }

    protected void checkIfHovered() {
        if (p.mouseX >= x && p.mouseX <= x + w && p.mouseY >= y && p.mouseY <= y + h) { // Tester, om musen er på objektet
            hovered = true;
            p.cursor(p.HAND); // Ændrer musen til at være en lille hånd
        } else {
            hovered = false;
            p.cursor(p.ARROW); // Ændrer musen til at være normal
        }
    }

    protected void onClick() {
        return;
    }

    public void draw() {
        checkIfHovered();

        if (!p.mousePressed) { mouseHeld = false; }

        if (mouseHeld) {
            p.fill(128);
        } else if (hovered) {
            p.fill(200);
        } else {
            p.fill(255);
        }

        p.rect(x, y, w, h);
    }
}

