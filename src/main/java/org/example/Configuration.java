package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Configuration {
    //initializing the configuration variables

    private  int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    //Default Constructor
    public Configuration() {

    }

    //Parameterized constructor
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    //getters
    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }


    //Setters

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }


    //Method to validate the parameters

    public boolean validate(){
        if(totalTickets <= 0){
            System.out.println("total tickets must be greater that 0");
            return false;
        }
        if(ticketReleaseRate <= 0){
            System.out.println("ticket release rate must be greater than 0");
            return false;
        }
        if(customerRetrievalRate <= 0){
            System.out.println("customer retrieval rate must be greater than 0");
            return false;
        }

        if(maxTicketCapacity <= 0){
            System.out.println("max ticket capacity must be greater than 0");
            return false;
        }
        if (totalTickets>maxTicketCapacity){
            System.out.println("total tickets must be less than max ticket capacity");
            return false;
        }
        return true;
    }

    //method to save configuration to a Json file

    public void saveToFile(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName), this);
    }

    //method to load configuration from a json file

    public static Configuration loadFromFile(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(fileName), Configuration.class);
    }

    //to string method to display the parameters

    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }



}
