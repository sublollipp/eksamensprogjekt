import processing.core.*;

public class TextSurface extends ClickableObject{

    public boolean onTextSurface = false;

    public TextSurface(PApplet p, int x, int y, int w, int h){
        super(p,x,y,w,h);
    }
    public void draw(){
        p.strokeWeight(3);
        p.stroke(0);
        super.draw();
    }
}