import java.io.*;
import java.util.ArrayList;

public class Storage {
    private String filepath;

    public Storage(String filepath) {
        this.filepath = filepath;
        File file = new File(filepath);
        file.getParentFile().mkdirs();
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filepath);

        try {
            if (!file.exists()) {
                file.createNewFile();
                return tasks;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    tasks.add(Parser.parseTask(line));
                } catch (Exception e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error: Could not load tasks: " + e.getMessage());
        }

        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (Task task : tasks) {
                bw.write(task.toSaveFormat());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Could not save tasks: " + e.getMessage());
        }
    }
}
