package com.paypal.desk;

/**
 * Created by user on 12/1/18.
 */
public class Transaction {
    private int id;
    private int user_from;
    private int user_to;
    private double transaction_amount;

    public Transaction(int id, int user_from, int user_to, double transaction_amount) {
        this.id = id;
        this.user_from = user_from;
        this.user_to = user_to;
        this.transaction_amount = transaction_amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getUser_from() {

        return user_from;
    }

    public void setUser_from(int user_from) {
        this.user_from = user_from;
    }

    public int getUser_to() {
        return user_to;
    }

    public void setUser_to(int user_to) {
        this.user_to = user_to;
    }

    public double getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(double transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", Which User Id='" + user_from + '\'' +
                ", Target User Id='" + user_to + '\'' +
                ", Transaction amount=" + transaction_amount +
                '}';
    }
}
