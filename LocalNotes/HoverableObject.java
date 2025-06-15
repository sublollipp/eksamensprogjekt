import processing.core.*;

// Checker, om musen er over den og kan evt. sætte cursor
public abstract class HoverableObject extends GraphicObject {
    protected boolean hovered = false, hasCursor = false;
    protected int hoverCursor = p.ARROW;
    protected LocalNotesSingleton ln;
    protected float progScale;
    protected int mX = 0, mY = 0  ; // mouseX og mouseY tranformet ved LocalNotesSingleton.programScale();

    // To constructors
    HoverableObject(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
        ln = LocalNotesSingleton.getInstance();
        progScale = LocalNotesSingleton.programScale();
        System.out.println("Programmet scale er " + String.valueOf(progScale));
    }

    HoverableObject(PApplet p, int x, int y, int w, int h, boolean triggerHoverCursor, int hoverCursor) {
        super(p, x, y, w, h);
        ln = LocalNotesSingleton.getInstance();
        progScale = LocalNotesSingleton.programScale();
        hasCursor = triggerHoverCursor;
        this.hoverCursor = hoverCursor;
    }

    boolean getHovered() {
        return hovered;
    }

    protected void checkIfHovered() {
        mX = (int) (p.mouseX / progScale);
        mY = (int) (p.mouseY / progScale);

        if (mX >= x && mX <= x + w && mY >= y && mY <= y + h) { // Tester, om musen er på objektet
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
