package org.example;

public class Main {
    public static void main(String[] args) {

        String fileName = "config.json";//Name of the file

        Configuration config = new Configuration(100,10,5,200);

        //saving configuration to json
        try {
            config.saveToFile(fileName);
            System.out.println("Configuration saved to" + fileName);
        }catch (Exception e){
            System.err.println("Failed to save configuration"+ e.getMessage());
            return;
        }


        Configuration loadedConfig;  //Load configuration from json

        try{
            loadedConfig = Configuration.loadFromFile(fileName);
            System.out.println("Configuration loaded: " + loadedConfig);
        }catch (Exception e){
            System.err.println("Failed to load configuration"+ e.getMessage());

            return;
        }

        TicketPool ticketPool =new TicketPool(loadedConfig.getTotalTickets());

        Vendor[] vendors = new Vendor[10];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = new Vendor(245,ticketPool, loadedConfig.getTicketReleaseRate(), 20);
            Thread vendorThread = new Thread(vendors[i], "Vendor - "+ i);
            vendorThread.start();
        }

        Customer[] customers = new Customer[10];
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(5,ticketPool,loadedConfig.getCustomerRetrievalRate(),5);
            Thread customerThread = new Thread(customers[i], "customer - "+ i);
            customerThread.start();
        }

    }
}