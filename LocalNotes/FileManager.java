import java.io.File;
import processing.core.*;

/*
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
*/

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.SysexMessage;

import java.io.FileReader;

public abstract class FileManager {

    public static void makeFile(File directory, String filName) {
        String fileName = checkFileMatches(directory, filName);

        try {
            File newFile = new File(directory.toString() + "\\" + fileName + ".json");
            if (newFile.createNewFile()) {
                System.out.println("Fil skabt succesfuldt: " + newFile.toString());

                LocalNotesSingleton.getInstance().fileAdded(newFile);
                
            } else {
                System.out.println("Noget gik galt");
            }
            FileWriter writer = new FileWriter(newFile.toString());
            writer.write("[\"\"]");
            writer.close();
        } catch (IOException e) {
            System.out.println("Noget gik galt da du prøvede at lave en fil");
        }
    }

    public static void makeFolder(File directory, String foldeName) {
        String folderName = checkFolderMatches(directory, foldeName);

        try {
            new File(directory + "\\" + folderName).mkdirs();
            File newFolder = new File(directory + "\\" + folderName);
            LocalNotesSingleton.getInstance().folderAdded(newFolder);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static String checkFileMatches(File dire, String fileName) {
        boolean hasMatch = false;
        String newName = fileName;
        File directory = dire;
        if (directory == null) directory = FileManager.root();
        System.out.println("Checker for navnet: " + newName + " i directoriet " + dire.getAbsolutePath());
        if (directory.listFiles() == null) return fileName; 
        for (File f : directory.listFiles()) {
            if (f.isFile()) {
                //System.out.println(f.getName().substring(0, f.getName().length() - 5));
                if (f.getName().substring(0, f.getName().length() - 5).equals(newName)) {
                    //System.out.println("De er ens :(");
                    char lastChar = fileName.charAt(fileName.length() - 1);
                    if (Character.isDigit(lastChar)) {
                        //System.out.println("DER VAR ET TAL I SLUTNINGEN AF FILNAVNET: " + lastChar);
                        int lastInt = ((int) (lastChar - '0')) + 1; // På grund af noget med nogle ASCII-koder skal ASCII-koden for 0 trækkes fra for at få det rigtige tal. 0's er 48
                        newName = newName.substring(0, newName.length() - 1);
                        String toAppend = String.valueOf(lastInt);
                        //System.out.println("Nyt navn sammensættes af " + newName + " og " + toAppend);
                        newName = newName + toAppend;
                    } else {
                        //System.out.println("Der var ikke noget tal til sidst i filnavnet");
                        newName = newName + " 1";
                    }
                    hasMatch = true;
                } //else System.out.println("De er ikke ens");
            }
        }
        if (hasMatch) {
            //System.out.println("Vi prøver igen");
            return checkFileMatches(directory, newName);
        } else {
            //System.out.println("Vi besluttede os for navnet " + newName);
            return newName;
        }
    }

    public static String checkFolderMatches(File directory, String folderName) {
        boolean hasMatch = false;
        String newName = folderName;
        if (directory.listFiles() == null) return folderName;
        for (File f : directory.listFiles()) {
            if (f.isDirectory()) {
                if (f.getName().equals(folderName)) {
                    char lastChar = folderName.charAt(folderName.length() - 1);
                    if (Character.isDigit(lastChar)) {
                        int lastInt = (int) (lastChar - '0') + 1;
                        newName = newName.substring(0, newName.length() - 1);
                        String toAppend = String.valueOf(lastInt);
                        newName = newName + toAppend;
                    } else {
                        newName = newName + "1";
                    }
                    hasMatch = true;
                }
            }
        }
        if (hasMatch) {
            return checkFolderMatches(directory, newName);
        } else {
            return newName;
        }
    }

    public static void saveLinesToFile(String[] lines, File currentFile) {
        if (currentFile == null) System.out.println("Den er altså null buddy");
        System.out.println(currentFile.toString());
        try (FileWriter writer = new FileWriter(currentFile, StandardCharsets.UTF_8)) {
            writer.write("[\n");
            for (int i = 0; i < lines.length; i++) {
                writer.write("  \"" + escapeJson(lines[i]) + "\"");
                if (i < lines.length - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Funktion for at slette en hel mappe.
    // De to øverste linjer er så man ikke ved et uheld sletter hele sin computer...
    public static void deleteDir(File dir) {
        if (dir.getAbsolutePath().length() < root().getAbsolutePath().length()) {
            System.out.println("Det navn var for kort");
            return;
        }
        if (!(dir.getAbsolutePath().substring(0, root().getAbsolutePath().length()).equals(root().getAbsolutePath()))) {
            System.out.println("Den sletning kunne have gået RIGTIG galt (FileManager.java)");
            return;
        }

        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if (f.isDirectory()) deleteDir(f);
                f.delete();
            }
        }

        dir.delete();
    }

    private static String escapeJson(String str) {
        return str
            .replace("\\", "\\\\") // erstat \ med \\
            .replace("\"", "\\\"") // erstat " med \"
            .replace("\n", "\\n") // erstat ny linje med \n
            .replace("\r", "\\r") // erstat return med \r
            .replace("\t", "\\t"); // erstat tab med \t
    }

    public static String[] loadLinesFromFile(File currentFile) {
        try {
            String content = Files.readString(currentFile.toPath(), StandardCharsets.UTF_8).trim();

            // Fjern []
            if (content.startsWith("[")) content = content.substring(1);
            if (content.endsWith("]")) content = content.substring(0, content.length() - 1);

            List<String> lines = new ArrayList<>();

            // Split ved kommaer, ikke indenfor gåseøjne
            boolean inQuotes = false;
            StringBuilder current = new StringBuilder();

            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                if (c == '\"') {
                    inQuotes = !inQuotes;
                } else if (c == ',' && !inQuotes) {
                    lines.add(unescapeJson(current.toString().trim().replaceAll("^\"|\"$", "")));
                    current.setLength(0);
                    continue;
                }
                current.append(c);
            }

            // Tilføj den sidste String, hvis den er der
            String last = current.toString().trim();
            if (!last.isEmpty()) {
                lines.add(unescapeJson(last.replaceAll("^\"|\"$", "")));
            }

            return lines.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    private static String unescapeJson(String str) {
        return str
            .replace("\\n", "\n")
            .replace("\\r", "\r")
            .replace("\\t", "\t")
            .replace("\\\"", "\"")
            .replace("\\\\", "\\");
    }

    public static String[] getCurrentDirectoryItems() {

        String progFil = System.getenv("ProgramFiles");

        File noteDir = new File(progFil + "/Notes");
        System.out.println(noteDir.getAbsolutePath());

        if (!noteDir.exists()) {
            new File(progFil + "/Notes").mkdirs();
            noteDir = new File(progFil + "/Notes");
        }

        File[] subDirs = noteDir.listFiles();
        String[] fileNames = new String[subDirs.length];

        if(subDirs.length != 0) {
            for (int i = 0; i < subDirs.length; i++) {
                System.out.println(subDirs[i].getName());
                fileNames[i] = subDirs[i].getName();
            }
        } else {
            System.out.println("Something went wrong trying to get to the \"Notes\" folder");
        }

        return fileNames;
    
    }

    public static File root() {
        return new File(System.getenv("ProgramFiles") + "/Notes");
    }
}
