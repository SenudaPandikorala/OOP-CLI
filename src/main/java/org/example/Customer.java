package org.example;

public class Customer extends AbstractTicketHandler implements Runnable {
    private TicketPool ticketPool;
    private int customerId;
    private int customerRetrievalRate;
    private int quantity;

    public Customer(int customerId,TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        super(ticketPool);
        this.ticketPool = ticketPool;
        this.customerId = customerId;
        this.customerRetrievalRate = customerRetrievalRate;// ticket removing frequency from the ticket pool.
        this.quantity=quantity; // number of tickets customer willing to purchase.

    }


    @Override
    public void run(){
        for (int i =0; i<quantity; i++){

            if(!Main.running){
                LoggerUtil.logInfo("Customer "+ customerId + " stopped");
                break;
            }
            Ticket ticket = ticketPool.buyTicket();

            //details of the ticket
            if (ticket !=null) {
                LoggerUtil.logInfo(" Tickets bought by : " + Thread.currentThread().getName() + "Ticket is = " + ticket);
            }else{
                LoggerUtil.logWarning("Customer " + customerId + " customer could not purchase a ticket because the ticket pool is empty");
            }

            //ticket removing delay
            try{
            Thread.sleep(customerRetrievalRate * 1000);

            }catch (InterruptedException e){
                LoggerUtil.logWarning("Customer " + customerId+ " interrupted" + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
        LoggerUtil.logInfo("Customer " + customerId + " has finished purchasing tickets. ");
    }


    @Override
    public void handleTickets() {
        run();
    }
}
