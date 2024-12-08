package org.example;

import java.util.Scanner;
import java.util.logging.*;

public class Main {
    public static volatile boolean running= true;
    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        Configuration config = null;
        LoggerUtil.setupLogging();


        //looping until all configuration are valid
        while (true) {

            System.out.println("Welcome to the real time ticket management simulation!!");

            System.out.println("Enter the configuration details below");

            //Collecting the user inputs while validating


            int totalTickets = getValidatedInteger(userInput, "Enter total number of tickets:");
            int ticketReleaseRate = getValidatedInteger(userInput, "Enter ticket release rate:");
            int customerRetrievalRate = getValidatedInteger(userInput, "Enter customer retrieval rate:");
            int maxTicketCapacity = getValidatedInteger(userInput, "Enter maximum ticket capacity:");



            //creating a configuration object

            config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);

            if (config.validate()) {
                LoggerUtil.logInfo("Configuration is valid.");
                break;
            } else {
                LoggerUtil.logWarning("Invalid configuration. Please re-enter the values.");
            }
        }


        String fileName = "config.json";//Name of the file

        //saving configuration to json
        try {
            config.saveToFile(fileName);
            LoggerUtil.logInfo("Configuration saved to" + fileName);
        }catch (Exception e){
            LoggerUtil.logSevere("Failed to save configuration"+ e.getMessage());
            return;
        }


        Configuration loadedConfig;  //Load configuration from json

        try{
            loadedConfig = Configuration.loadFromFile(fileName);
            LoggerUtil.logInfo("Configuration loaded: " + loadedConfig);
        }catch (Exception e){
            LoggerUtil.logSevere("Failed to load configuration"+ e.getMessage());

            return;
        }

        TicketPool ticketPool =new TicketPool(loadedConfig.getTotalTickets());

        Vendor[] vendors = new Vendor[10];
        Thread[] vendorThreads = new Thread[vendors.length];


        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = new Vendor(245,ticketPool, loadedConfig.getTicketReleaseRate(), 20);
            vendorThreads[i] = new Thread(vendors[i], "Vendor - "+ i);
//            vendorThread.start();
        }

        Customer[] customers = new Customer[10];
        Thread[] customerThreads = new Thread[customers.length];

        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(5,ticketPool,loadedConfig.getCustomerRetrievalRate(),5);
            customerThreads[i] = new Thread(customers[i], "customer - "+ i);
//            customerThread.start();
        }

        //Creating the while loop for the start option
        System.out.println("Enter 'start' to begin the simulation. When started, the simulation can be stopped by entering 'stop'");
        while (true) {
            String command = userInput.nextLine().trim().toLowerCase();
            if ("start".equals(command)){
                LoggerUtil.logInfo("Starting all threads");

                for (Thread vendorThread : vendorThreads){
                    vendorThread.start();
                }
                for (Thread customerThread : customerThreads){
                    customerThread.start();
                }
                break;
            } else {
                System.out.println("Invalid command. Please enter start");
            }
        }
        //Creating the while loop for the stop option
        while (true){
            String command = userInput.nextLine().trim().toLowerCase();
            if ("stop".equals(command)) {
                LoggerUtil.logInfo("Stopping all threads");
                running = false;
                break;
            }else {
                System.out.println("please enter 'stop' to stop simulation");
                }
            }
        // waiting for all the threads to complete
        try{
            for (Thread vendorThread : vendorThreads){
                vendorThread.join();
            }
            for (Thread customerThread : customerThreads){
                customerThread.join();
            }
        }catch (InterruptedException e) {
            LoggerUtil.logSevere("Thread interruption"+ e.getMessage());
        }
        System.out.println("successfully terminated simulation. To start again re-run the program");
        userInput.close();


    }

    //Method to validate if a user input is an integer or if it is greater than 0
    private static int getValidatedInteger(Scanner userInput, String prompt){
        int value;
        while (true){
            System.out.print(prompt);
            try{
                value = Integer.parseInt(userInput.nextLine());
                if (value >0){
                    break; // Exits the loop if input is a valid integer greater than 0
                } else {
                    System.out.println("The value must be greater than 0. please try again.");
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid input. please enter a valid integer");
            }
        }
        return value;
    }

}