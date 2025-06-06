public import java.io.*;
import java.util.*;

public class fileapp {
    static final String DIRECTORY = "files";

    public static void main(String[] args) {
        new File(DIRECTORY).mkdir(); // Create a directory if it doesn't exist
        Scanner scanner = new Scanner(System.in);
        String choice;

        while (true) {
            System.out.println("----------------------");
            System.out.println("      File Manager ");
            System.out.println("----------------------");
            System.out.println("1. Create a File");
            System.out.println("2. Write to a File");
            System.out.println("3. Read a File");
            System.out.println("4. Delete a File");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createFile(scanner);
                    break;
                case "2":
                    writeFile(scanner);
                    break;
                case "3":
                    readFile(scanner);
                    break;
                case "4":
                    deleteFile(scanner);
                    break;
                case "5":
                    System.out.println("Exiting application...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void createFile(Scanner scanner) {
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine();
        File file = new File(DIRECTORY + "/" + fileName + ".txt");

        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    static void writeFile(Scanner scanner) {
        List<String> files = listFiles();
        if (files.isEmpty()) return;

        System.out.print("Enter file name to write: ");
        String fileName = scanner.nextLine();
        File file = new File(DIRECTORY + "/" + fileName + ".txt");

        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        System.out.println("Start typing (type 'exit' to stop):");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            while (true) {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase("exit")) break;
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    static void readFile(Scanner scanner) {
        List<String> files = listFiles();
        if (files.isEmpty()) return;

        System.out.print("Enter file name to read: ");
        String fileName = scanner.nextLine();
        File file = new File(DIRECTORY + "/" + fileName + ".txt");

        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("File contents:");
            String line;
            while ((line = reader.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    static void deleteFile(Scanner scanner) {
        List<String> files = listFiles();
        if (files.isEmpty()) return;

        System.out.print("Enter file name to delete: ");
        String fileName = scanner.nextLine();
        File file = new File(DIRECTORY + "/" + fileName + ".txt");

        if (file.exists()) {
            if (file.delete())
                System.out.println("File deleted successfully.");
            else
                System.out.println("File could not be deleted.");
        } else {
            System.out.println("File not found.");
        }
    }

    static List<String> listFiles() {
        File folder = new File(DIRECTORY);
        String[] fileList = folder.list((dir, name) -> name.endsWith(".txt"));

        if (fileList == null || fileList.length == 0) {
            System.out.println("No files available.");
            return Collections.emptyList();
        }

        System.out.println("Available files:");
        List<String> fileNames = new ArrayList<>();
        for (String fileName : fileList) {
            String name = fileName.replace(".txt", "");
            fileNames.add(name);
            System.out.println("- " + name);
        }
        return fileNames;
    }
} {
    
}
