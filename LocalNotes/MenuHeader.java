import java.io.File;

import processing.core.*;

public class MenuHeader extends GraphicObject implements ClickResponsive {

    private BackButton backButton;
    private String currentFolder = FileManager.root().getName();

    public MenuHeader(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
        backButton = new BackButton(p, x + 5, y + 5, h - 10, h - 10);
    }

    public void updateDirectory(File directory) {
        currentFolder = directory.getName();
    }

    public void onMouseClicked() {
        backButton.onMouseClicked();
    }

    public void onMouseReleased() {
        backButton.onMouseReleased();
    }

    public void draw() {
        p.fill(0);
        p.textSize(h - 10);
        p.text(currentFolder, x + h, y + 5);
        p.stroke(0);
        p.strokeWeight(0);
        p.fill(255);
        super.draw();
        backButton.draw();
    }
}
