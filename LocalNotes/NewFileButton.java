import processing.core.*;

public class NewFileButton extends ClickableObject {

    private PImage saveImage;

    public NewFileButton(PApplet p, int x, int y, int w, int h) {
        super(p, w, y, w, h);
        saveImage = p.loadImage("Assets/NewFileButton.png");
    }

    public void draw() {
        p.strokeWeight(0);
        super.draw();
        p.image(saveImage, x, y, h, h);
    }
}