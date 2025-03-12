import processing.core.*;

public class TypeSurface extends ClickableObject{

    public boolean onTextSurface = false;

    String inputText = "";

    public TypeSurface(PApplet p, int x, int y, int w, int h){
        super(p,x,y,w,h);
    }
    public void draw(){
        p.strokeWeight(3);
        p.stroke(0);
        super.draw();
    }
    protected void onClick() {
        onTextSurface = true;
    }

    public void keyPressed(){
        if (onTextSurface) {
            if (p.key == p.BACKSPACE && inputText.length() > 0) {
                inputText = inputText.substring(0, inputText.length() - 1);  // Remove last char
            } 
            else if (p.key == p.ENTER) {
                p.println("User typed: " + inputText);  // Example action
                onTextSurface = false;  // Optional: deactivate on Enter
            } 
            else if (p.key != p.CODED) {
                inputText += p.key;  // Append key input
            }
        }
    }
}