import processing.core.*;

abstract class GraphicObject {

    protected PApplet p;
    protected int x, y, w, h;

    protected GraphicObject(PApplet p, int x, int y, int w, int h) {
        this.p = p;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    protected GraphicObject(PApplet p, int x, int y) {
        this.p = p;
        this.x = x;
        this.y = y;
        this.w = 0;
        this.h = 0;
    }

    protected void draw() {
        return;
    }
}
