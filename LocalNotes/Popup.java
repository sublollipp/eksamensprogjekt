import processing.core.*;
import java.util.ArrayList;

public abstract class Popup extends HoverableObject implements ClickResponsive, TypeResponsive {

    protected ArrayList<GraphicObject> embeddedObjects = new ArrayList<GraphicObject>();

    protected Popup(PApplet p, int screenw, int screenh, int w, int h) {
        super(p, screenw / 2 - w / 2, screenh / 2 - h / 2, w, h); // Gør popuppen centreret
    }
    
    // Bruges i rightclickmenu fordi den ikke skal være centreret
    protected Popup(PApplet p) {
        super(p, 1, 1, 1, 1);
        return;
    }

    public void keyPressed() {
        for (GraphicObject obj : embeddedObjects) {
            if (obj instanceof TypeResponsive) {
                ((TypeResponsive) obj).keyPressed();
            }
        }
    }

    public void onMouseClicked() {
        for (GraphicObject obj : embeddedObjects) {
            if (obj instanceof ClickResponsive) {
                ((ClickResponsive) obj).onMouseClicked();
            }
        }
    }

    public void onMouseReleased() {
        for (GraphicObject obj : embeddedObjects) {
            if (obj instanceof ClickResponsive) {
                ((ClickResponsive) obj).onMouseReleased();
            }
        }
    }

    public void draw() {
        p.strokeWeight(2);
        p.stroke(0);
        p.fill(125);
        p.rect(x, y, w, h);
        for (GraphicObject obj : embeddedObjects) {
            obj.draw(x, y);
        }
    }

    public void close() {
        LocalNotesSingleton.getInstance().removePopup(this);
    }
}