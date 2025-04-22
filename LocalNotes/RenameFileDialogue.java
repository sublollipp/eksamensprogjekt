import processing.core.*;
import java.io.File;

public class RenameFileDialogue extends Popup {

    InputLine nameInput;
    File fileToRename;

    RenameFileDialogue(PApplet p, File fileToRename) {
        super(p, p.width, p.height, 500, 140);
        nameInput = new InputLine(p, 10, 10, 480, 60);
        this.fileToRename = fileToRename;
        embeddedObjects.add(nameInput);
        embeddedObjects.add(new DynamicButton(p, "Rename", 10, 80, 235, 50, () -> {this.rename();}));
        embeddedObjects.add(new DynamicButton(p, "Cancel", 255, 80, 235, 50, () -> {this.close();}));
    }

    public void rename() {
        String name = nameInput.text;
        if (fileToRename.isDirectory()) {
            name = FileManager.checkFolderMatches(LocalNotesSingleton.getInstance().currentDir, name);
        } else {
            name = FileManager.checkFileMatches(LocalNotesSingleton.getInstance().currentDir, name) + ".json";
        }
        fileToRename.renameTo(new File(LocalNotesSingleton.getInstance().currentDir + "\\" + name));
        LocalNotesSingleton.getInstance().dirChange();
        close();
    }
}
