import processing.core.*;

public class DynamicButton extends ClickableObject {
    private Runnable clickFunction; // Funktionen der skal køres, når knappen bliver trykket på
    private String buttonText = "";
    private boolean hasText = false;

    public DynamicButton(PApplet p, int x, int y, int w, int h, Runnable r) {
        super(p, x, y, w, h);
        clickFunction = r;
    }

    public DynamicButton(PApplet p, String text, int x, int y, int w, int h, Runnable r) {
        super(p, x, y, w, h);
        clickFunction = r;
        buttonText = text;
        hasText = true;
    }

    public void onClick() {
        clickFunction.run();
    }

    public void draw() {
        super.draw();
        if (hasText) {
            p.fill(0);
            p.textSize((int) (h * 0.6));
            p.textAlign(p.CENTER, p.CENTER);
            p.text(buttonText, x + w / 2, y + h / 2);
        }
    }

}
