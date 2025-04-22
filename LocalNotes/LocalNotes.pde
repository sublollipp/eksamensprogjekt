LocalNotesSingleton localNotes = null;

void setup() {
    size(1200, 800);
    LocalNotesSingleton.makeInstance(this);
    localNotes = LocalNotesSingleton.getInstance();
    localNotes.setup();
}

void draw() {
    localNotes.draw();
}

void keyPressed() {
    localNotes.keyPressed();
}

void mousePressed() {
    if (mouseButton == LEFT) {
        localNotes.mousePressed();
    }

    if (mouseButton == RIGHT) {
        localNotes.rightMousePressed();
    }
}

void mouseReleased() {
    if (mouseButton == LEFT) {
        localNotes.mouseReleased();
    }

    if (mouseButton == RIGHT) {
        localNotes.rightMouseReleased();
    }
}

void mouseWheel(MouseEvent mouseEvent) {
    localNotes.mouseWheel(mouseEvent);
}
