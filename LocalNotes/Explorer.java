import processing.core.*;
import java.util.ArrayList;

public class Explorer extends GraphicObject {

    private int scrolly = 0; // 0 er hvor man er scrollet til toppen af menuen

    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>(); // Listen over knapper

    public Explorer(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
    }

    public void clearButtons() {
        menuButtons.clear();
    }

    public void addButton(MenuButton newButton) {
        menuButtons.add(newButton);
    }

    public void draw() {
        int addedY = 0;
        for (MenuButton button : menuButtons) { // Enhanced for-loop - går igennem alle elementer i menuButtons-arrayet
            button.draw(scrolly + addedY);
            addedY += button.h;
        }
        p.stroke(0);
        p.fill(255);
        p.rect(x, 0, w, y);
        p.rect(x, y + h, w, p.height - y + h);
        p.stroke(2);
        super.draw();
    }

}
