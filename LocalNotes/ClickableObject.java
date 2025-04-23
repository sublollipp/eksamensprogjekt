import processing.core.*;

public abstract class ClickableObject extends HoverableObject implements ClickResponsive {

    protected boolean mouseHeld = false;

    protected ClickableObject(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h, true, p.HAND);
    }

    protected ClickableObject(PApplet p, int x, int y, int w, int h, int curs) {
        super(p, x, y, w, h, true, curs);
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

    protected void onClick() {
        return;
    }

    public void draw() {
        if (!p.mousePressed) { mouseHeld = false; }

        if (mouseHeld) {
            p.fill(128);
        } else if (hovered) {
            p.fill(200);
        } else {
            p.fill(255);
        }

        super.draw();
    }
}

