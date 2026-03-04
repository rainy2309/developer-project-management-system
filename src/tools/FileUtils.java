package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    public static <T> void saveToFile(String fileName, List<T> data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (T item : data) {
                // Ghi mỗi đối tượng thành một dòng trong file
                pw.println(item.toString());
            }
            System.out.println("Save to file " + fileName + " successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static List<String> readFile(String fileName) {
        List<String> list = new ArrayList<>();
        File f = new File(fileName);

        if (!f.exists()) {
            return list;
        }

// Mở Scanner để đọc file trong ngoặc đơn (Try-with-resources)
// đóng file (sc.close()) khi đọc xong hoặc khi có lỗi
        try (Scanner sc = new Scanner(f)) {

            // lặp khi trong file còn dòng tiếp theo để đọc
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    list.add(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }
}
