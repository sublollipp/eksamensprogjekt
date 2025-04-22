import processing.core.*;
import java.io.File;

public class BackButton extends ClickableObject {

    PImage backImage;

    public BackButton(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
        backImage = p.loadImage("Assets/BackButton.png");
    }

    protected void onClick() {
        LocalNotesSingleton.getInstance().save();
        LocalNotesSingleton.getInstance().goBack();
    }

    public void draw() {
        super.draw();
        p.image(backImage, x, y, w, h);
    }
}
