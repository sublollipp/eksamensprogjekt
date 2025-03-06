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
    private void onClick(){
        onTextSurface = true;
    }
    void KeyPressed(){
    if (onTextSurface) {
        if (key == BACKSPACE && inputText.length() > 0) {
            inputText = inputText.substring(0, inputText.length() - 1);  // Remove last char
        } 
        else if (key == ENTER) {
            println("User typed: " + inputText);  // Example action
            onTextSurface = false;  // Optional: deactivate on Enter
        } 
        else if (key != CODED) {
            inputText += key;  // Append key input
    }
}
    
    
}