import processing.core.*;

public class TextSurface extends ClickableObject{

    public boolean onTextSurface = false;

    public TextSurface(PApplet p, int x, int y, int w, int h){
        super(p,x,y,w,h);
    }
    void draw(){
        strokeWeight(3);
        stroke(0);
        super.draw();
    }
}