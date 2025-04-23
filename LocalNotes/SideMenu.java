import processing.core.*;

import java.io.File;
import java.util.ArrayList;

public class SideMenu extends HoverableObject implements ScrollResponsive, ClickResponsive, RightClickResponsive {

    private int scrolly = 0; // 0 er hvor man er scrollet til toppen af menuen
    private int buttonHeight = 80; // Højden af hver knap
    private int totalHeight = 0; // Højden af alle knapperne lagt sammen
    private float scrollSpeed = 7;

    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>(); // Listen over knapper

    public SideMenu(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h);
    }

    public void setCurrentFile(File newCurrentFile) {
        for (MenuButton but : menuButtons) {
            if (but instanceof FileButton) {
                FileButton fb = (FileButton) but;
                if (fb.getButtonFile().equals(newCurrentFile)) {
                    fb.makeCurrentFile();
                } else {
                    fb.makeNotCurrentFile();
                }
            }
        }
    }

    public void setMenuButtons(ArrayList<MenuButton> newList) {
        menuButtons = newList;
        updateTotalHeight();
    }

    public void setMenuButtons(String[] dirList) {
        for(int i = 0; i < dirList.length; i++) {
            if (dirList[i].length() > 3 && dirList[i].substring(dirList[i].length() -4) == ".json") {
                addFileButton(dirList[i].substring(dirList[i].length() - 4));
            } else {
                addFolderButton(dirList[i]);
            }
        }
    }

    public void setMenuButtons(File dir) {
        clearButtons();
        if (!dir.isDirectory()) return;
        File[] subDirs = dir.listFiles();
        if (subDirs.length <= 0) { return; }
        for(int i = 0; i < subDirs.length; i++) {
            dir = subDirs[i];
            if (dir.isDirectory()) {
                addFolderButton(dir.getName());
            } else if (dir.isFile()) {
                addFileButton(dir.getName());
            }
        }
    }

    public void clearButtons() {
        menuButtons.clear();
        scrolly = 0;
        totalHeight = 0;
    }

    public void addButton(MenuButton newButton) {
        menuButtons.add(newButton);
        updateTotalHeight();
    }

    private void updateTotalHeight() {
        totalHeight = menuButtons.size() * buttonHeight;
    }

    public void addFileButton(String fileName) {
        FileButton tempButton = new FileButton(p, x, y, w, buttonHeight, fileName);
        addButton(tempButton);
    }

    public void addFolderButton(String folderName) {
        FolderButton tempButton = new FolderButton(p, x, y, w, buttonHeight, folderName);
        addButton(tempButton);
    }

    public void onScroll(float scrollAmount) {
        if ((scrollAmount > 0 && scrolly < 0) || (scrollAmount < 0 && scrolly > h - totalHeight)) {
            scrolly += (int) (scrollAmount * scrollSpeed);
            if (scrolly > 0) { scrolly = 0; }
            if (scrolly < h - totalHeight) { scrolly = h - totalHeight; }
        }
    }

    public void onMouseClicked() {
        ArrayList<MenuButton> tempButtons = new ArrayList<>(menuButtons);
        for (MenuButton button : tempButtons) {
            button.onMouseClicked();
        }
    }

    public void onMouseReleased() {
        ArrayList<MenuButton> tempButtons = new ArrayList<>(menuButtons);
        for (MenuButton button : tempButtons) {
            button.onMouseReleased();
        }
    }

    public void onRightMouseClicked() {
        ArrayList<MenuButton> tempButtons = new ArrayList<>(menuButtons);
        for (MenuButton button : tempButtons) {
            button.onRightMouseClicked();
        }
    }

    public void onRightMouseReleased() {
        ArrayList<MenuButton> tempButtons = new ArrayList<>(menuButtons);
        System.out.println("HØJRE");
        for (MenuButton button : tempButtons) {
            button.onRightMouseReleased();
        }
    }

    public void draw() {
        p.fill(255);
        p.stroke(0);
        p.strokeWeight(0);
        int addedY = 0;
        for (MenuButton button : menuButtons) { // Enhanced for-loop - går igennem alle elementer i menuButtons-arrayet
            button.parentHovered = hovered;
            button.draw(scrolly + addedY);
            addedY += button.h;
        }
        p.fill(255);
        p.stroke(255); // Hvide kantlinjer så man ikke kan se, hvad der i virkeligheden foregår
        // Strokeweight holdes på 2, så man ikke kan se kanterne af de "usynlige" MenuButtons
        p.rect(x, 0, w, y);
        p.rect(x, y + h, w, p.height - y + h);
        p.noFill();
        p.stroke(0);
        super.draw();
    }
}