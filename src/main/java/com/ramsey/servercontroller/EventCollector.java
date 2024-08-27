package com.ramsey.servercontroller;

import com.ramsey.servercontroller.events.Event;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

public class EventCollector {
    private static ObjectOutputStream objectOutputStream;

    public static void record(Event event) {
        try {
            event.write(objectOutputStream);
            objectOutputStream.flush();
        } catch (IOException exception) {
            ServerControllerMain.LOGGER.error("Failed to write", exception);
        }
    }

    public static void init() {
        try {
            //check if file exists if it exists put it into gzip
            //combine Config.outputPath and Config.outputFile

            Path path = Path.of(Config.outputPath, Config.outputFile);
            File file = new File(path.toString());

            if (file.exists()) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = formatter.format(date);
                String backupName = "events-" + strDate + "-" + System.currentTimeMillis() + ".gz";

                File backupFile = new File(Config.outputPath + backupName);

                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    FileOutputStream fileOutputStream = new FileOutputStream(backupFile);
                    GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);

                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = fileInputStream.read(buffer)) > 0) {
                        gzipOutputStream.write(buffer, 0, length);
                    }

                    fileInputStream.close();
                    gzipOutputStream.close();
                } catch (Exception exception) {
                    ServerControllerMain.LOGGER.error("Failed to create events backup", exception);
                }

                if(file.delete()) {
                    ServerControllerMain.LOGGER.info("Old events file deleted");
                } else {
                    ServerControllerMain.LOGGER.error("Failed to delete old events file");
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            ServerControllerMain.LOGGER.info("EventCollector initialized");
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to initialize EventCollector", exception);
        }
    }

    public static void close() {
        try {
            objectOutputStream.close();
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to close EventCollector", exception);
        }
    }
}
