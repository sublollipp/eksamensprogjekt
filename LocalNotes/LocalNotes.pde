ArrayList<GraphicObject> gO = new ArrayList<GraphicObject>(); // Alle grafiske objekter (alt fra skrivefladen til knapper) skal tilføjes til denne liste

void setup() {
    size(1200, 800);
    gO.add(new SaveButton(this, 50, 50, 200, 200));
    gO.add(new NewFolderButton(this, 50, 275, 200, 200));
    gO.add(new NewFileButton(this, 50, 500, 200, 200));
    gO.add(new TextSurface(this, 300, 50, 800, 750));
}

void mousePressed() {
    for (GraphicObject object : gO) {
        if (object instanceof ClickableObject) {
            ((ClickableObject) object).onMouseClicked();
        }
    }
}

void mouseReleased() {
    for (GraphicObject object : gO) {
        if (object instanceof ClickableObject) {
            ((ClickableObject) object).onMouseReleased();
        }
    }
}

void UI() {

}

void draw() {
    background(255);
    UI();
    for(GraphicObject object : gO) {
        object.draw();
    }
}
