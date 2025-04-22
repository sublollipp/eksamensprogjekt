import processing.core.*;
import java.io.File;
import java.util.ArrayList;

public class FolderButton extends MenuButton {
    public FolderButton(PApplet p, int x, int y, int w, int h, String name) {
        super(p, x, y, w, h, name, "Assets/NewFolderButton.png");
    }

    protected File getButtonFile() {
        return new File(LocalNotesSingleton.getInstance().currentDir.getAbsolutePath() + "\\" + name);
    }

    protected void onClick() {
        LocalNotesSingleton.getInstance().currentDir = getButtonFile();
        //System.out.println(LocalNotesSingleton.getInstance().currentDir.getName());
        LocalNotesSingleton.getInstance().dirChange();
    }
}
