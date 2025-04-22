import processing.core.*;
import java.io.File;

public class RightClickMenu extends Popup implements RightClickResponsive {

    File fileClicked;

    public RightClickMenu(PApplet p, int x, int y, File fileClicked) {
        super(p);
        w = 200;
        h = 76;
        this.x = x;
        this.y = y - h;
        this.fileClicked = fileClicked;
        embeddedObjects.add(new DynamicButton(p, "Rename", 2, 2, w - 4, 35, () -> {this.rename();}));
        embeddedObjects.add(new DynamicButton(p, "Delete", 2, 39, w - 4, 35, () -> {this.delete();}));
    }

    public void onMouseReleased() {
        if (!hovered) {
            close();
        } else {
            System.out.println("Else pelse pølsesnak");
            super.onMouseReleased();
        }
    }

    public void onRightMouseReleased() {
        if (!hovered) close();
        System.out.println("Right click detected by RightClickMenu");
    }

    public void onRightMouseClicked() {
        return;
    }

    private void delete() {
        LocalNotesSingleton.getInstance().addPopup(new DeleteDialogue(p, fileClicked));
        close();
    }

    private void rename() {
        LocalNotesSingleton.getInstance().addPopup(new RenameFileDialogue(p, fileClicked));
        close();
    }

    public void draw() {
        checkIfHovered();
        super.draw();
    }


}
