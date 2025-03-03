import processing.core.*;

public class NewFolderButton extends ClickableObject {

    private PImage saveImage;

    public NewFolderButton(PApplet p, int x, int y, int w, int h) {
        super(p, w, y, w, h);
        saveImage = p.loadImage("Assets/NewFolderButton.png");
    }

    public void draw() {
        p.strokeWeight(0);
        super.draw();
        p.image(saveImage, x, y, w, h);
    }
}
