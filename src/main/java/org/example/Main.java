package org.example;

public class Main {
    public static void main(String[] args) {
        TicketPool ticketPool =new TicketPool(10);


        Vendor[] vendors = new Vendor[10];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = new Vendor(245,ticketPool,5,20);
            Thread vendorThread = new Thread(vendors[i], "Vendor - "+ i);
            vendorThread.start();
        }

        Customer[] customers = new Customer[10];
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(5,ticketPool,5,5);
            Thread customerThread = new Thread(customers[i], "customer - "+ i);
            customerThread.start();
        }

    }
}