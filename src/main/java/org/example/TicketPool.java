package org.example;

import java.util.*;

public class TicketPool {
    private final int maxCapacity;
    private Queue<Ticket> ticketQueue;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.ticketQueue = new LinkedList<>();
    }


    //Method which is used to add tickets by a vendor
    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maxCapacity){
            try {
                wait();
            } catch (InterruptedException e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        this.ticketQueue.add(ticket);
        notifyAll(); //calling this method to notify all the threads that a ticket is available
        System.out.println(Thread.currentThread().getName()+ "has added a ticket to the ticket pool. Current size is" + ticketQueue.size());

//        if (tickets.size()+ count <= maxCapacity) {
//            for (int i = 0; i < count; i++) {
//                tickets.add(1);
//            }
//            System.out.println(count + " tickets added. Total tickets: " + tickets.size());
//        }else{
//            System.out.println("Maximum capacity reached. Cannot add any more tickets.");
//        }
    }

    //Method which is used to purchase tickets by a customer.

    public synchronized Ticket buyTicket(){
        while (ticketQueue.isEmpty()){
            try {
                wait(); //waiting if the queue is empty
            }catch (InterruptedException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        Ticket ticket = ticketQueue.poll(); //To remove the ticket from the front of the queue
        System.out.println(Thread.currentThread().getName()+ "has purchased a ticket from the ticket pool. Current size is"+ ticketQueue.size());
        return ticket;

//        if(!tickets.isEmpty()){
//            tickets.remove(tickets.size()-1);
//            System.out.println("1 ticket removed. Total tickets: " + tickets.size());
//        }
//        return false;
    }
}