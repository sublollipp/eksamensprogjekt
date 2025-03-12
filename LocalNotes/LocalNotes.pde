ArrayList<GraphicObject> gO = new ArrayList<GraphicObject>(); // Alle grafiske objekter (alt fra skrivefladen til knapper) skal tilføjes til denne liste

void setup() {
    size(1200, 800);
    gO.add(new SaveButton(this, 50, 50, 200, 200));
    gO.add(new NewFolderButton(this, 50, 275, 200, 200));
    gO.add(new NewFileButton(this, 50, 500, 200, 200));
    gO.add(new TypeSurface(this, 410, 50, 750, 700));
}

void keyPressed() {
    for (GraphicObject object : gO) {
        if (object instanceof TypeSurface) {
            ((TypeSurface) object).keyPressed();
        }
    }
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
