/**
  _     ___   ____    _    _     _   _  ___ _____ _____ ____  
 | |   / _ \ / ___|  / \  | |   | \ | |/ _ \_   _| ____/ ___| 
 | |  | | | | |     / _ \ | |   |  \| | | | || | |  _| \___ \ 
 | |__| |_| | |___ / ___ \| |___| |\  | |_| || | | |___ ___) |
 |_____\___/ \____/_/   \_\_____|_| \_|\___/ |_| |_____|____/ 
                                                              
Dyre og besværlige cloud-løsninger samt kunstig intelligens, der bliver tvunget ned i brugerens hals, har ødelagt de fleste noteprogrammer.
Oftest er ens bedste mulighed bare at tage en notesblok med i skole - men den er fysisk og IKKE digital. Der er plads til et bedre noteprogram,
der respekterer brugerens evne til at skrive sine egne noter og brugerens ret til at have sine filer liggende på sin egen computer i stedet
for at blive luret ind i en unødvendig abonnementstjeneste. Dette er LocalNotes. Lightweight. Lokalt. Ordentligt.

*/

import processing.core.*;
import processing.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;

public class LocalNotesSingleton {

    private static LocalNotesSingleton instance = null; // Global reference til singleton-objektet

    private PApplet p; // Reference til vor kære PApplet

    public PApplet getApplet() {
        return p;
    }

    public static LocalNotesSingleton getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Singleton ikke lavet endnu");
        } else {
            return instance;
        }
    }

    public static void makeInstance(PApplet p) {
        if (instance == null) {
            instance = new LocalNotesSingleton();
            instance.p = p;
        }
    }

    public File currentFile = null; // Den fil, man er inde på
    public File currentDir = null; // Den mappe, man er inde på

    public int currentCursor = p.ARROW; // Integer fordi det er en enum :D

    ArrayList<GraphicObject> gO = new ArrayList<GraphicObject>(); // Alle grafiske objekter (alt fra skrivefladen til knapper) skal tilføjes til denne liste
    ArrayList<Popup> popUpQueue = new ArrayList<Popup>(); // Der kan kun være én popup af gangen. Dette system gør det muligt at sætte flere "i kø". Nr. 0 vises på skærmen
    // For at holde programmet i orden bruges kun setters/adders fra selve programmet til popups - ikke Arraylist's indbyggede

    boolean popupOpen = false;

    String fiskTilMig = "Kenneth"; // Debug

    private LocalNotesSingleton() {} // Den meget spændende constructor

    public void settings() { // Er ikke 100 på, hvorfor, men processing bad mig om at sætte size her i stedet for i setup
        p.size(1200, 800);
    }

    public void setup() {
        gO.add(new TypingSurface(p, 500, 50, 670, 700));
        Sidebar tempSideMenu = new Sidebar(p, 10, 50, 400, 600);
        currentDir = FileManager.root();
        tempSideMenu.setMenuButtons(currentDir);
        gO.add(tempSideMenu);
        gO.add(new NewSomethingButton(p, 10, 660, 400, 90));
        gO.add(new SaveButton(p, 1120, 750, 50, 50));
    }

    public void fileAdded(File newFile) {
        for(GraphicObject obj : gO) {
            if (obj instanceof Sidebar) {
                ((Sidebar) obj).setMenuButtons(currentDir);
            }
        }
        setCurrentFile(newFile);
    }

    public void folderAdded(File newFolder) {
        save();
        currentDir = newFolder;
        dirChange();
    }

    public void dirChange() { // Denne funktion skal køre, hver gang der bliver skiftet directory. Den sørger bl.a. for at opdatere sidemenuen
        for(GraphicObject obj : gO) {
            if (obj instanceof Sidebar) {
                ((Sidebar) obj).setMenuButtons(currentDir);
            }
        }
        currentFile = null;
        if (currentDir.listFiles().length <= 0) return;
        for (File curfil : currentDir.listFiles()) { // Går gennem alle mappens filer og directories og prøver at finde den første fil
            if (curfil.isFile()) {
                setCurrentFile(curfil);
                return;
            }
        }
        setCurrentFile(null);
    }

    public void keyPressed() { // Prøv og gæt
        if (!popupOpen) {
            for (GraphicObject object : gO) {
                if (object instanceof TypeResponsive) { // Kører, hvis objektet bruger TypeResponsive interfacet. Vigtigt fordi den skal kunne opfange, når man trykker på taster
                    ((TypeResponsive) object).keyPressed();
                }
            }
        } else {
            popUpQueue.get(0).keyPressed();
        }
    }

    public void mousePressed() { // Endnu en Einstein-level funktion
        if (!popupOpen) {
            for (GraphicObject object : gO) {
                if (object instanceof ClickResponsive) {
                    ((ClickResponsive) object).onMouseClicked();
                }
            }
        } else {
            popUpQueue.get(0).onMouseClicked();
        }
        //System.out.println(p.mouseX + ", " + p.mouseY);
    }

    public void mouseReleased() { // Du finder aldrig ud af det
        if (!popupOpen) {
            for (GraphicObject object : gO) {
                if (object instanceof ClickResponsive) {
                    ((ClickResponsive) object).onMouseReleased();
                }
            }
        } else {
            popUpQueue.get(0).onMouseReleased(); // Hvis en popup er åben er det kun den, der kan interageres med
        }
    }

    public void rightMousePressed() {
        System.out.println("Højreklik registreret");
        for (GraphicObject obj : gO) {
            if (obj instanceof RightClickResponsive) {
                System.out.println("Der var en højrekliksensitiv");
                ((RightClickResponsive) obj).onRightMouseClicked();
            }
        }
    }

    public void rightMouseReleased() {
        for (GraphicObject obj : gO) {
            if (obj instanceof RightClickResponsive) {
                ((RightClickResponsive) obj).onRightMouseReleased();
            }
        }
    }

    public void mouseWheel(MouseEvent event) { // Ok den her er måske lidt kringlet. MouseEvent er en datatype fra processing, som her fortæller, hvor meget der blev scrollet. Virker også med trackpad
        if (!popupOpen) {    
            for (GraphicObject object : gO) {
                if (object instanceof ScrollResponsive) {
                    ((ScrollResponsive) object).onScroll(Float.valueOf(event.getCount()));
                }
            }
        }
    }

    public void goBack() { // Funktionen for at gå et directory op
        if (!currentDir.toString().equals(FileManager.root().toString())) {
            currentDir = currentDir.getParentFile();
            dirChange();
        }
    }

    public void removeGraphicObject(GraphicObject object) {
        if (gO.contains(object)) {
            gO.remove(object);
        } else {
            System.err.println("gO does not contain the object: " + object.toString());
        }
    }

    /* 
     *Disse top popup-funktioner bruges i stedet for popUpQueue's .add() og .remove() for at holde popUpOpen opdateret.
     *Bedre for performance end at køre "if (popUpQueue.size() > 0)" hver frame plus det gør koden mere læsbar
     */
    public void addPopup(Popup addedPopup) {
        popUpQueue.add(addedPopup);
        popupOpen = true;
        //System.out.println(popUpQueue.toString());
    }

    public void removePopup() {
        if (popUpQueue.size() == 1) {
            popupOpen = false;
        }
        if (popUpQueue.size() > 0) {
            popUpQueue.remove(0);
        }
    }
    
    // Polymorfi er konge
    public void removePopup(Popup objectToRemove) {
        if (popUpQueue.size() == 1) {
            popupOpen = false;
        }
        if (popUpQueue.size() > 0) {
            popUpQueue.remove(objectToRemove);
        }
        //System.out.println(popUpQueue.toString());
    }

    /* 
     * Kan ikke HELT huske, hvorfor denne her funktion er her. Hvis i ser den, er det fordi jeg glemte at slette den.
     */
    void UI() {

    }

    public void setCurrentFile(File newCurrentFile) {
        if (currentFile != null) save();
        currentFile = newCurrentFile;
        TypingSurface ts = null;
        for(GraphicObject obj : gO) {
            if (obj instanceof GraphicObject) {
                ts = (TypingSurface) obj;
                break;
            }
        }
        if (!(ts instanceof TypingSurface) || ts == null) return;
        String[] lines;
        if (currentFile == null) {
            String[] toAdd = {""};
            ts.setLines(toAdd);
        } else {
            ts.setLines(FileManager.loadLinesFromFile(newCurrentFile));
        }
    }

    public void draw() {
        UI();
        currentCursor = p.ARROW;

        if (!popupOpen) {
            p.background(255);
            for(GraphicObject object : gO) {
                object.draw();
            }
        } else {
            popUpQueue.get(0).draw();
        }

        p.cursor(currentCursor);

    }

    // Gemmer den åbne fil
    public void save() {
        if (!(currentFile instanceof File) || currentFile == null) return;
        TypingSurface typeSurface = null;
        for (GraphicObject obj : gO) {
            if (obj instanceof TypingSurface) {
                typeSurface = (TypingSurface) obj;
                break;
            }
        }
        if (typeSurface instanceof TypingSurface) {
            System.out.println("Filens navn er " + currentFile.getName());
            FileManager.saveLinesToFile(typeSurface.getLines(), currentFile);
        }
    }
}