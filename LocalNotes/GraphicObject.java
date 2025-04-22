import processing.core.*;

abstract class GraphicObject {

    protected PApplet p;
    protected int x, y, w, h, ox, oy;

    protected GraphicObject(PApplet p, int x, int y, int w, int h) {
        this.p = p;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        ox = x;
        oy = y;
    }

    protected GraphicObject(PApplet p, int x, int y) {
        this.p = p;
        this.x = x;
        this.y = y;
        this.w = 0;
        this.h = 0;
        ox = x;
        oy = y;
    }

    protected void draw() {
        return;
    }

    protected void draw(int relativex, int relativey) {
        x = ox + relativex;
        y = oy + relativey;
        draw();
    }
}
