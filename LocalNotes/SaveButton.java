import processing.core.*;

public class SaveButton extends ClickableObject {

    private PImage saveImage;

    public SaveButton(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
        saveImage = p.loadImage("Assets/SaveIcon.png");
    }

    public void draw() {
        p.strokeWeight(0);
        super.draw();
        p.image(saveImage, x, y, h, h);
    }

    protected void onClick() {
        LocalNotesSingleton.getInstance().save();
    }
}
