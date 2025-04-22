import processing.core.*;
import java.util.ArrayList;

public class NewSomethingPopup extends Popup {
    private InputLine nameInput;

    public NewSomethingPopup(PApplet p) {
        super(p, p.width, p.height, 500, 200);

        nameInput = new InputLine(p, 10, 10, 480, 60);
        DynamicButton newFolderButton = new DynamicButton(p, "New Folder", 10, 80, 235, 50, () -> {this.makeNewFolder();});
        DynamicButton newFileButton = new DynamicButton(p, "New File", 255, 80, 235, 50, () -> {this.makeNewFile();});
        DynamicButton cancelButton = new DynamicButton(p, "Cancel", 10, 140, 480, 50, () -> {this.close();});

        embeddedObjects.add(nameInput);
        embeddedObjects.add(cancelButton);
        embeddedObjects.add(newFileButton);
        embeddedObjects.add(newFolderButton);
    }

    public void makeNewFile() {
        String fileName = "";
        if (nameInput.text.length() > 0) {
            fileName = nameInput.text;
            close();
            FileManager.makeFile(LocalNotesSingleton.getInstance().currentDir, fileName);
        }
    }

    public void makeNewFolder() {
        if (nameInput.text.length() > 0) {
            close();
            FileManager.makeFolder(LocalNotesSingleton.getInstance().currentDir, nameInput.text);
        }
    }

}
