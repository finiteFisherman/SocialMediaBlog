package DAO;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 A DAO is a class that mediates the transformation of data between the format of objects in Java to rows in a
 database. The database has been created with a table named 'account' under res/SocialMedia.sql.
 It contains similar values as the Account class:
 account_id int primary key auto_increment,
 username varchar(255) unique,
 password varchar(255)
 */

public class AccountDAO {

    // check if username is taken
    public boolean isUserUsed(String user) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT 1 FROM Account WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    // Create Account
    public Account createAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        //Write SQL logic here. account_id is auto increment
        String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());

            statement.executeUpdate();
            ResultSet pkeyResultSet = statement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getInt(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    // Get Account  by username and password
    public Account getAccount(String user, String pw){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString methods here.
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pw);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
