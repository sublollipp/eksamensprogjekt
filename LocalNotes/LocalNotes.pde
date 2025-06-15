LocalNotesSingleton localNotes = null;

float programScale = LocalNotesSingleton.programScale();

void settings() {
    size((int) (programScale * 1200), (int) (programScale * 800));
}

void setup() {
    LocalNotesSingleton.makeInstance(this);
    localNotes = LocalNotesSingleton.getInstance();
    localNotes.setup();
}

void draw() {
    scale(programScale);
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
