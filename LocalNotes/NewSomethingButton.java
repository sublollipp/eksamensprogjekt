import processing.core.*;

public class NewSomethingButton extends ClickableObject {
    protected NewSomethingButton(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
    }

    public void draw() {
        p.stroke(0);
        p.strokeWeight(2);
        super.draw();
        p.fill(0);
        p.textAlign(p.CENTER, p.CENTER);
        p.textSize((int) (h * 0.4));
        p.text("+ New File / Folder", x + w / 2, y + h / 2);
    }

    protected void onClick() {
        LocalNotesSingleton.getInstance().addPopup(new NewSomethingPopup(p));
    }
}
