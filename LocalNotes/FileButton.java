import processing.core.*;
import java.util.ArrayList;
import java.io.File;

public class FileButton extends MenuButton {

    public FileButton(PApplet p, int x, int y, int w, int h, String name) {
        super(p, x, y, w, h, name.substring(0, name.length() - 5), "Assets/NewFileButton.png");
        System.out.println("OIWBESGONWEONL");
    }

    protected File getButtonFile() {
        return (new File(LocalNotesSingleton.getInstance().currentDir.getAbsolutePath() + "\\" + name + ".json"));
    }

    public void makeCurrentFile() {
        selected = true;
    }

    public void makeNotCurrentFile() {
        selected = false;
    }

    protected void onClick() {
        super.onClick();
        LocalNotesSingleton.getInstance().setCurrentFile(getButtonFile());
    }

}
