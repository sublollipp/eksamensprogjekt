import processing.core.*;

public abstract class HoverableObject extends GraphicObject {
    protected boolean hovered = false, hasCursor = false;
    protected int hoverCursor = p.ARROW;
    protected LocalNotesSingleton ln;

    HoverableObject(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
        ln = LocalNotesSingleton.getInstance();
    }

    HoverableObject(PApplet p, int x, int y, int w, int h, boolean triggerHoverCursor, int hoverCursor) {
        super(p, x, y, w, h);
        ln = LocalNotesSingleton.getInstance();
        hasCursor = triggerHoverCursor;
        this.hoverCursor = hoverCursor;
    }

    boolean getHovered() {
        return hovered;
    }

    protected void checkIfHovered() {
        if (p.mouseX >= x && p.mouseX <= x + w && p.mouseY >= y && p.mouseY <= y + h) { // Tester, om musen er på objektet
            hovered = true;
            if (hasCursor) {
                ln.currentCursor = hoverCursor;
            }
        } else {
            hovered = false;
        }
    }

    protected void draw() {
        checkIfHovered();
        p.rect(x, y, w, h);
    }
}
