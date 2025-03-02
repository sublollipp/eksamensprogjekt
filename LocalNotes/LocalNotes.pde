ArrayList<GraphicObject> gO = new ArrayList<GraphicObject>(); // Alle grafiske objekter (alt fra skrivefladen til knapper) skal tilføjes til denne liste

void setup() {
    size(1200, 800);
    gO.add(new SaveButton(this, 200, 200, 200, 200));
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

void draw() {
    background(255);
    for(GraphicObject object : gO) {
        object.draw();
    }
}
