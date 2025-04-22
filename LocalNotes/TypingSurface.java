import processing.core.*;
import java.util.ArrayList;
import java.util.Collections;

public class TypingSurface extends ClickableObject implements TypeResponsive {
    private String text = "";
    private ArrayList<String> lines = new ArrayList<String>();
    private int lineHeight = 20;
    private int maxWidth = 380;  // Maximum text width before wrapping
    private int cursorX = 0, cursorY = 0;

    public TypingSurface(PApplet p, int x, int y, int w, int h) {
        super(p, x, y, w, h, p.TEXT);
        lines.add("");
        maxWidth = w - 20;
    }

    public String[] getLines() {
        return lines.toArray(new String[0]); // String[0] får java til at vælge den rigtige længde
    }

    public void setLines(String[] newLines) {
        lines.clear();
        Collections.addAll(lines, newLines); // "Oversætter" fra String[] til arraylist
        cursorX = 0;
        cursorY = 0;
    }

    public void draw() {
        if (lines.size() == 0) lines.add("");

        p.strokeWeight(3);
        p.stroke(0);
        p.fill(255);
        super.draw();
        
        p.textAlign(p.LEFT, p.TOP);
        p.textSize(lineHeight);
        p.fill(0);

        int texty = y + 5;
        
        for (int i = 0; i < lines.size(); i++) {
            p.text(lines.get(i), x + 5, texty);
            texty += lineHeight;
        }
        
        p.stroke(0);
        if (cursorX > lines.get(cursorY).length()) cursorX = lines.get(cursorY).length();
        int cursorPosX = x + 5 + (int) p.textWidth(lines.get(cursorY).substring(0, cursorX));
        int cursorPosY = y + 5 + cursorY * lineHeight;
        p.line(cursorPosX, cursorPosY, cursorPosX, cursorPosY + lineHeight); // Tegner linjen, der viser, hvor man er
    }

    public void keyPressed() {
        if (LocalNotesSingleton.getInstance().currentFile == null) return;
        if (p.key == p.BACKSPACE) {
            if (cursorX > 0) { // Checker om man er i starten af en linje. Hvis man er, bliver cursoren rykket op i linjen ovenover
                String line = lines.get(cursorY);
                lines.set(cursorY, line.substring(0, cursorX - 1) + line.substring(cursorX));
                cursorX--;
            } else if (cursorY > 0) {
                String previous = lines.get(cursorY - 1);
                cursorX = previous.length();
                lines.set(cursorY - 1, previous + lines.get(cursorY));
                lines.remove(cursorY);
                cursorY--;
            }
        } else if (p.key == p.ENTER || p.key == p.RETURN) {
            String line = lines.get(cursorY);
            if (lines.size() > (int) (h / lineHeight - 2)) return;
            lines.set(cursorY, line.substring(0, cursorX));
            lines.add(cursorY + 1, line.substring(cursorX));
            cursorX = 0;
            cursorY++;
        } else if (p.key == p.CODED) { // Piletaster
            if (p.keyCode == p.LEFT && cursorX > 0) cursorX--;
            else if (p.keyCode == p.RIGHT && cursorX < lines.get(cursorY).length()) cursorX++;
            else if (p.keyCode == p.UP && cursorY > 0) {
                cursorY--;
                cursorX = p.min(cursorX, lines.get(cursorY).length());
            } else if (p.keyCode == p.DOWN && cursorY < lines.size() - 1) {
                cursorY++;
                cursorX = p.min(cursorX, lines.get(cursorY).length());
            }
        } else if (p.key == ' ') {  // Fix for spacebar at end of line
            String line = lines.get(cursorY);
            if (cursorX == line.length()) {  
                lines.set(cursorY, line + " ");  // Explicitly append space when at the end
            } else {
                lines.set(cursorY, line.substring(0, cursorX) + " " + line.substring(cursorX));
            }
            cursorX++;
        } else if (p.key >= 32) { // Bogstaver, tal, symboler osv
            String line = lines.get(cursorY);
            lines.set(cursorY, line.substring(0, cursorX) + p.key + line.substring(cursorX));
            cursorX++;
        }

        checkWrap(); // SKAL DEBUGGES tror jeg? idk det virker tilsyneladende fint nu
    }

    public void onClick() {

        int clickedLine = (p.mouseY - y) / lineHeight;
        cursorY = p.constrain(clickedLine, 0, lines.size() - 1);

        String line = lines.get(cursorY);
        float minDist = Float.MAX_VALUE;
        int closestIndex = 0;

        p.textSize(lineHeight);

        for (int i = 0; i <= line.length(); i++) {

            float charX = x + 5 + p.textWidth(line.substring(0, i));
            float dist = p.abs(p.mouseX - charX);

            if (dist < minDist) {
                minDist = dist;
                closestIndex = i;
            }
        }

        cursorX = closestIndex;
    }

    void checkWrap() {
        p.textSize(lineHeight);
        for (int i = 0; i < lines.size(); i++) {

            String line = lines.get(i);
            
            if (p.textWidth(line) > maxWidth) {

                int breakIndex = line.length();
                
                while (breakIndex > 0 && p.textWidth(line.substring(0, breakIndex)) > maxWidth) {
                    breakIndex--;
                }
                
                if (breakIndex > 0) {

                    String overflowText = line.substring(breakIndex);
                    
                    lines.set(i, line.substring(0, breakIndex));

                    if (lines.size() > (int) (h / lineHeight - 2)) {
                        return;
                    }

                    if (i + 1 < lines.size()) {
                        lines.set(i + 1, overflowText + lines.get(i + 1));
                    } else {
                        lines.add(overflowText);
                    }

                    if (cursorY == i && cursorX > breakIndex) {
                        cursorY++;
                        cursorX -= breakIndex;
                    }
                }
            }
        }

        /*
        if (lines.size() > (int) (h / lineHeight - 1)) {
            lines.remove(lines.size() - 1);
            cursorY = (int) (h / lineHeight - 1) - 1;
        }
        */
        System.out.println("Der er " + String.valueOf(lines.size()) + " linjer - plads til " + String.valueOf((int) h / lineHeight));
    }
}