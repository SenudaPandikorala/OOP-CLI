package org.example;

import java.util.*;

public class TicketPool implements TicketOperation {
    private final int maxCapacity;
    private Queue<Ticket> ticketQueue;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.ticketQueue = new LinkedList<>();
    }


    //Method which is used to add tickets by a vendor
    @Override
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
        LoggerUtil.logInfo(Thread.currentThread().getName()+ " has added a ticket to the ticket pool. Current size is = " + ticketQueue.size());

    }

    //Method which is used to purchase tickets by a customer.
    @Override
    public synchronized Ticket buyTicket(){
        while (ticketQueue.isEmpty()){
            try {
                wait(); //waiting if the queue is empty
            }catch (InterruptedException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        Ticket ticket = ticketQueue.poll(); //To remove the ticket from the front of the queue
        LoggerUtil.logInfo(Thread.currentThread().getName()+"  has purchased a ticket from the ticket pool. Current size is = "+ ticketQueue.size());
        return ticket;


    }
}