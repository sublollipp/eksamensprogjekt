import processing.core.*;
import java.io.File;

public abstract class MenuButton extends ClickableObject implements RightClickResponsive {

    public boolean parentHovered = false;
    
    protected boolean selected = false;

    protected String name = "";
    protected int buttonHeight = 50, iconWidth = 0;
    PImage buttonIcon;

    protected int actualMenuY = 0; // Bruges til at "huske" menuens y-værdi, da "y" ændrer sig afhængig af scroll

    protected MenuButton(PApplet p, int x, int y, int w, int h, String n, String imgPath) {
        super(p, x, y, w, h); // Her parses x, y og width af menuen ind - ikke af knappen. Height er af knappen og sættes via menuen.
        actualMenuY = y;
        name = n;
        buttonIcon = p.loadImage(imgPath);
    }

    protected void onClick() {
        LocalNotesSingleton.getInstance().save();
        super.onClick();
    }

    protected void draw(int actualy) {
        y = actualMenuY + actualy;
        if (!p.mousePressed) { mouseHeld = false; }

        if (parentHovered) {
            checkIfHovered();
        } else hovered = false;

        if (mouseHeld) {
            p.fill(128);
        } else if (hovered) {
            p.fill(200);
        } else {
            p.fill(255);
        }

        if (selected) {
            p.fill(125);
        }

        p.strokeWeight(3);
        p.stroke(0);
        p.rect(x, y, w, h);
        int imgWidth = 0;

        if(buttonIcon != null) {
            imgWidth = (int) (((float)(buttonIcon.width)) * ((float)(h - 8) / (float)(buttonIcon.height)));
            p.image(buttonIcon, x + 4, y + 4, imgWidth, h - 8);
        }

        p.fill(0);
        p.textSize(30);
        p.text(name, x + imgWidth + (int) (buttonHeight * 0.8), y + (int) ((h - p.g.textSize) / 2));
    }

    protected File getButtonFile() {
        return (new File(FileManager.root() + "\\eiwohg4ewiognvioednbdivolwnsikl"));
    }

    public void onRightMouseClicked() {
        if (hovered) {
            LocalNotesSingleton.getInstance().addPopup(new RightClickMenu(p, p.mouseX, p.mouseY, getButtonFile()));
        }
    }

    public void onRightMouseReleased() {
        return;
    }
}
