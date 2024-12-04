package org.example;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customerId;
    private int customerRetrievalRate;
    private int quantity;

    public Customer(int customerId,TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        this.ticketPool = ticketPool;
        this.customerId = customerId;
        this.customerRetrievalRate = customerRetrievalRate;// ticket removing frequency from the ticket pool.
        this.quantity=quantity; // number of tickets customer willing to purchase.

    }


    @Override

    public void run(){
        for (int i =0; i<quantity; i++){
            Ticket ticket = ticketPool.buyTicket();
            //details of the ticket
            System.out.println("Tickets bought by"+ Thread.currentThread().getName()+ "Ticket is" + ticket);

            //ticket removing delay
            try{
            Thread.sleep(customerRetrievalRate * 1000);

            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }


}
