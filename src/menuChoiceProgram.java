
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class menuChoiceProgram {

    private static boolean needsToBeSaved = false;      // Dirty flag
    private static String currentFilename = null;       // Null means unsaved new list

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();

        boolean done = false;

        while (!done) {

            displayList(list);
            displayMenu();

            String choice = SafeInput.getRegExString(
                    in,
                    "Enter menu choice",
                    "[AaDdIiMmOoSsCcVvQq]"
            );

            switch (choice.toUpperCase()) {

                case "A":
                    addItem(in, list);
                    break;

                case "D":
                    deleteItem(in, list);
                    break;

                case "I":
                    insertItem(in, list);
                    break;

                case "M":
                    moveItem(in, list);
                    break;

                case "C":
                    clearList(in, list);
                    break;

                case "O":
                    try {
                        openList(in, list);
                    } catch (FileNotFoundException e) {
                        System.out.println("ERROR: File not found.");
                    } catch (IOException e) {
                        System.out.println("ERROR reading file.");
                    }
                    break;

                case "S":
                    try {
                        saveList(in, list);
                    } catch (IOException e) {
                        System.out.println("ERROR saving file.");
                    }
                    break;

                case "V":
                    displayList(list);
                    break;

                case "Q":
                    try {
                        if (!handleQuit(in, list)) {
                            break;
                        }
                    } catch (IOException e) {
                        System.out.println("ERROR during quit-save operation.");
                        break;
                    }
                    done = true;
                    System.out.println("Thank you! Program ending.");
                    break;
            }
        }
    }

    // ------------------------------------------------------------
    // MENU CHOICE DISPLAY
    // ------------------------------------------------------------

    private static void displayMenu() {
        System.out.println("\n========== MENU CHOICE OPTIONS ==========");
        System.out.println("A - Add item");
        System.out.println("D - Delete item");
        System.out.println("I - Insert item");
        System.out.println("M - Move item");
        System.out.println("O - Open list from file");
        System.out.println("S - Save list to disk");
        System.out.println("C - Clear list");
        System.out.println("V - View list");
        System.out.println("Q - Quit");
        System.out.println("==================================");
    }

    // ------------------------------------------------------------
    // MENU LIST TABLE DISPLAY
    // ------------------------------------------------------------

    private static void displayList(ArrayList<String> list) {
        System.out.println("\n========== CURRENT LIST ==========");
        System.out.println("Dirty: " + (needsToBeSaved ? "YES" : "NO"));
        System.out.println("----------------------------------");

        if (list.isEmpty()) {
            System.out.println("[EMPTY]");
            return;
        }

        System.out.printf("%-5s | %-30s%n", "No.", "Item");
        System.out.println("----------------------------------");

        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%-5d | %-30s%n", i + 1, list.get(i));
        }
    }

    // ------------------------------------------------------------
    // DIRTY FLAG THROWS METHODS
    // ------------------------------------------------------------

    private static void markDirty() {
        needsToBeSaved = true;
    }

    private static boolean confirmSaveBeforeLosingWork(Scanner in, ArrayList<String> list)
            throws IOException {

        System.out.println("WARNING: Unsaved changes exist!");

        if (SafeInput.getYNConfirm(in, "Save before continuing? (Y/N)")) {
            saveList(in, list);
            return true;
        }

        return SafeInput.getYNConfirm(in, "Discard menu choice changes? (Y/N)");
    }

    private static boolean handleQuit(Scanner in, ArrayList<String> list)
            throws IOException {

        if (needsToBeSaved) {
            if (!confirmSaveBeforeLosingWork(in, list)) {
                return false;
            }
        }

        return SafeInput.getYNConfirm(in, "Quit program? (Y/N)");
    }

    // ------------------------------------------------------------
    // MENU METHOD OPERATIONS
    // ------------------------------------------------------------

    private static void addItem(Scanner in, ArrayList<String> list) {
        String item = SafeInput.getRegExString(in, "Enter the menu choice item", ".+");
        list.add(item);
        markDirty();
    }

    private static void deleteItem(Scanner in, ArrayList<String> list) {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }

        int index = SafeInput.getRangedInt(in,
                "Delete which item (1 - " + list.size() + ")?",
                1, list.size()
        );

        list.remove(index - 1);
        markDirty();
    }

    private static void insertItem(Scanner in, ArrayList<String> list) {
        int pos = SafeInput.getRangedInt(in,
                "Insert menu choice at position ?",
                1, list.size() + 1);

        String item = SafeInput.getRegExString(in, "Enter item", ".+");
        list.add(pos - 1, item);
        markDirty();
    }

    private static void moveItem(Scanner in, ArrayList<String> list) {
        if (list.size() < 2) {
            System.out.println("Not enough items to move.");
            return;
        }

        int from = SafeInput.getRangedInt(in,
                "Move the menu choice item from which item (1 - " + list.size() + ")?",
                1, list.size()
        );

        int to = SafeInput.getRangedInt(in,
                "Move the menu choice item to which position (1 - " + list.size() + ")?",
                1, list.size()
        );

        String temp = list.remove(from - 1);
        list.add(to - 1, temp);
        markDirty();
    }

    private static void clearList(Scanner in, ArrayList<String> list) {
        if (list.isEmpty()) {
            System.out.println("List already empty.");
            return;
        }

        if (SafeInput.getYNConfirm(in, "Clear entire list? (Y/N)")) {
            list.clear();
            markDirty();
        }
    }

    // ------------------------------------------------------------
    // MENU FILE OPERATIONS (NIO)
    // ------------------------------------------------------------

    private static void openList(Scanner in, ArrayList<String> list)
            throws IOException, FileNotFoundException {

        if (needsToBeSaved) {
            if (!confirmSaveBeforeLosingWork(in, list)) return;
        }

        String filename = SafeInput.getRegExString(
                in,
                "Enter the menu choice filename (no extension)",
                "[A-Za-z0-9_\\-]+"
        ) + ".txt";

        Path path = Paths.get(filename);

        if (!Files.exists(path)) {
            throw new FileNotFoundException();
        }

        List<String> lines = Files.readAllLines(path);

        list.clear();
        list.addAll(lines);

        currentFilename = filename;
        needsToBeSaved = false;

        System.out.println("Loaded: " + filename);
    }

    private static void saveList(Scanner in, ArrayList<String> list)
            throws IOException {

        if (currentFilename == null) {
            String filename = SafeInput.getRegExString(
                    in, "Save as (no extension)", "[A-Za-z0-9_\\-]+"
            ) + ".txt";

            currentFilename = filename;
        }

        Path path = Paths.get(currentFilename);

        Files.write(path, list);

        needsToBeSaved = false;
        System.out.println("Saved to: " + currentFilename);
    }
}

