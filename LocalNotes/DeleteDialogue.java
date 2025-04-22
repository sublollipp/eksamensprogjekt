import java.io.File;

import processing.core.*;

// Klassen for den skærm der kommer op og spørger, om man er sikker på, at man vil slette noget.
public class DeleteDialogue extends Popup {

    File chosenFile;
    String fileName, filetype = "note";
    boolean isFolder = false;

    public DeleteDialogue(PApplet p, File fileToDelete) {
        super(p, p.width, p.height, 500, 200);
        if (fileToDelete.isDirectory()) isFolder = true;
        chosenFile = fileToDelete;
        fileName = chosenFile.getName();
        if (!isFolder) fileName = fileName.substring(0, fileName.length() - 5);
        if (isFolder) filetype = "folder";

        embeddedObjects.add(new DynamicButton(p, "Delete", 10, 140, 235, 50, () -> {this.delete();}));
        embeddedObjects.add(new DynamicButton(p, "Cancel", 255, 140, 235, 50, () -> {close();}));

    }

    public void draw() {
        super.draw();
        p.textAlign(p.CENTER, p.CENTER);
        p.fill(0);
        p.textSize(20);
        p.text("Are you sure you want to delete the " + filetype + ":", x + w / 2, y + 12);
        p.text(fileName + "?", x + w / 2, y + 34);
    }

    // Kører når man trykker på delete-knappen
    private void delete() {
        FileManager.deleteDir(chosenFile);
        LocalNotesSingleton.getInstance().dirChange();
        close();
    }
}
