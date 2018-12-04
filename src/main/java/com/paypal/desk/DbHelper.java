package com.paypal.desk;

import com.mysql.jdbc.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {

    private static final Connection connection = getConnection();
    private static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:8889/paypal",
                    "Tigran",
                    "tigran94"
            );

            System.out.println("Connection successful");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static int createUser(String firstName, String lastName) {
        String sql = "insert into users (first_name, last_name) values (?,?);";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.execute();

            String idSql = "select max(id) from users";
            Statement idStatement = connection.createStatement();
            ResultSet resultSet = idStatement.executeQuery(idSql);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates the user balance in database
     * Sets balance = balance + amount
     *
     * @param userId id of the user in users table
     * @param amount double value of the amount to insert
     */
    static void cashFlow(int userId, double amount) {
        String sql = "update users set balance = balance + ? where id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1,amount);
            statement.setInt(2,userId);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error while changing the balance");
        }
    }

    /**
     * Emulates a transaction between 2 users
     * Takes money from one account and adds to another account
     *
     * @param userFrom source user id
     * @param userTo   target user id
     * @param amount   transaction amount
     */
    static void transaction(int userFrom, int userTo, double amount) {
        String sql = "insert into transactions (user_from, user_to,transaction_amount) values (?,?,?)";
        cashFlow(userFrom, -amount);
        cashFlow(userTo,amount);
        try {
            PreparedStatement statement  = connection.prepareStatement(sql);
            statement.setInt(1,userFrom);
            statement.setInt(2,userTo);
            statement.setDouble(3,amount);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static List<User> listUsers() {
        String sql = "select * from users";

        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            List<User> userList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                double balance = resultSet.getDouble("balance");

                userList.add(new User(
                        id, firstName, lastName, balance
                ));
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Transaction> listTransactions() {
        String sql = "select * from transactions";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<Transaction> transactionList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user_from= resultSet.getInt("user_from");
                int user_to = resultSet.getInt("user_to");
                double transaction_amount = resultSet.getDouble("transaction_amount");

                transactionList.add(new Transaction(
                        id, user_from, user_to, transaction_amount
                ));
            }
            return transactionList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
