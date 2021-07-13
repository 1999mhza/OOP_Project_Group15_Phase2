package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Logger {

    public static void log(String type, String event) {
        try {
            File file = new File("src\\resource\\log.txt");
            if (!file.exists()) file.createNewFile();

            FileWriter fileWriter = new FileWriter(file, true);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
            fileWriter.write(String.format("%s, [%s], %s\n", formatter.format(new Date()), type.toUpperCase(), event));

            fileWriter.close();
        } catch (IOException ignored) {
        }
    }
}
