import processing.core.*;
import java.io.File;

public class Sidebar extends GraphicObject implements ClickResponsive, ScrollResponsive, RightClickResponsive {

    SideMenu sideMenu;
    MenuHeader header;

    int headerHeight = 50;

    public Sidebar(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
        header = new MenuHeader(p, x, y, w, headerHeight);
        sideMenu = new SideMenu(p, x, y + headerHeight, w, h - headerHeight);
    }

    public void setCurrentFile(File newCurrentFile) {
        sideMenu.setCurrentFile(newCurrentFile);
    }

    public void onMouseClicked() {
        header.onMouseClicked();
        sideMenu.onMouseClicked();
    }

    public void onMouseReleased() {
        header.onMouseReleased();
        sideMenu.onMouseReleased();
    }

    public void onRightMouseClicked() {
        sideMenu.onRightMouseClicked();
    }
    
    public void onRightMouseReleased() {
        sideMenu.onRightMouseReleased();
    }

    public void onScroll(float scrollAmount) {
        sideMenu.onScroll(scrollAmount);
    }

    public void setMenuButtons(File directory) {
        header.updateDirectory(directory);
        sideMenu.setMenuButtons(directory);
    }

    public void draw() {
        sideMenu.draw();
        header.draw();
        p.strokeWeight(2);
        p.stroke(0);
        p.noFill();
        super.draw();
    }
}
