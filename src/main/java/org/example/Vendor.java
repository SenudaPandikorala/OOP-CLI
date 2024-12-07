package org.example;

import java.math.BigDecimal;


public class Vendor extends AbstractTicketHandler implements Runnable {

    private TicketPool ticketPool; //ticket pool which is shared by the customer and vendors.
    private int vendorId;
    private int ticketPerRelease;
    private int totalTickets; //number of tickets vendor willing to sell.

    public Vendor(int vendorId, TicketPool ticketPool, int ticketPerRelease, int totalTickets) {
        super(ticketPool);
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.ticketPerRelease = ticketPerRelease;
        this.totalTickets = totalTickets;
    }


    public int getVendorId() {
        return vendorId;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public int getTicketPerRelease() {
        return ticketPerRelease;
    }

    public int getTotalTickets() {
        return totalTickets;
    }


    @Override
    public void run() {

        for (int i = 1; i <= totalTickets; i++){

            Ticket ticket = new Ticket(i,"event agastra", new BigDecimal("1000"));
            ticketPool.addTicket(ticket);
        }

        try{
            Thread.sleep(ticketPerRelease * 1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e.getMessage());
        }


    }


    @Override
    public void handleTickets() {
        run();

    }
}
