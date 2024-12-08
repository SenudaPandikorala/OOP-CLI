package org.example;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {
    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());
    private static BufferedWriter fileWriter;
    //Method for Setting up  logging

    public static void setupLogging(){
        try{
            //calling this method to prevent duplicate logs
            removeExistingHandlers();

            //disabling the parent logger to avoid duplicate logging
            logger.setUseParentHandlers(false);


            //logging in console
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            logger.addHandler(consoleHandler);

            //File logging

            fileWriter =new BufferedWriter(new FileWriter("app.log",true));
            logger.setLevel(Level.ALL);

            logInfo("Logging setup complete");

        } catch (IOException e) {
            System.err.println("Failed to initialize logging : " + e.getMessage());
            logger.severe("Failed to initialize logging" + e.getMessage());
        }
    }

    //Method to  log  messages to console and file


    public static void logInfo(String message){
        logger.info(message);
        writeToFile("INFO" + message);
    }

    public static void logSevere(String message){
        logger.severe(message);
        writeToFile("ERROR" + message);
    }

    public static void logWarning(String message){
        logger.warning(message);
        writeToFile("WARNING" + message);
    }

    // Method to write messages to the log file

    private static void writeToFile (String message){
        try{
            if(fileWriter != null){
                fileWriter.write(message);
                fileWriter.newLine();
                fileWriter.flush();
            }
        }catch (IOException e) {
//            System.err.println("Failed to write to log file: " + e.getMessage());
            logger.severe("Failed to write to log file: " + e.getMessage());
        }
    }

    // Method to close the file writer

//    public static void closeLogFile() {
//        try {
//            if(fileWriter != null) {
//                fileWriter.close();
//            }
//        }catch (IOException e){
//
//            logger.severe("Failed to close log file:" + e.getMessage());
//        }
//    }

    private static void removeExistingHandlers(){
        var handlers = logger.getHandlers();
        for (var handler : handlers){
            logger.removeHandler(handler);
        }
    }



}
