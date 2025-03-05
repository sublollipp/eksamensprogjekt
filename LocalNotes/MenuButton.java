import processing.core.*;

public abstract class MenuButton extends ClickableObject {

    protected String name = "";

    protected MenuButton(PApplet p, int x, int y, int w, int h, String n) {
        super(p, x, y, w, h);
        name = n;
    }

    protected MenuButton(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
    }

    

}
