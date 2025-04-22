import processing.core.*;
import java.util.ArrayList;

public class DebugPopup extends Popup {

    public DebugPopup(PApplet p, int sw, int sh) {
        
        super(p, sw, sh, 450, 250);

        DynamicButton closeButton = new DynamicButton(p, 100, 200, 250, 40, () -> {this.close();});
        InputLine testInput = new InputLine(p, 100, 10, 250, 180);
        testInput.focus();

        embeddedObjects.add(closeButton);
        embeddedObjects.add(testInput);
    }

    public void draw() {
        super.draw();
    }

}
