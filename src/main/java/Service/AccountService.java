package Service;
import Model.Account;
import DAO.AccountDAO;

import java.sql.SQLException;
import java.util.List;


/*
 The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
 persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
 SQL. Programming tasks like checking that the input is valid, conducting additional security checks, or saving the
 actions undertaken by the API to a logging file.
 */
public class AccountService {
    public AccountDAO accountDAO;

    //constructor for accountservice which creates an AccountDAO
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    //constructor for AccountDAO mock in test cases
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    // login to getAccount method
    public Account login(Account account){
        return accountDAO.getAccount(account.getUsername(), account.getPassword());
    }
    // check if username taken for user regristration
    public Account registerAccount(Account account) throws SQLException {
        if (account.getUsername() == null
                || account.getUsername().isEmpty()
                || account.getPassword() == null
                || account.getPassword().length() < 4) {
            //throw new IllegalArgumentException("Invalid username or password, try again: ");
            return null;
        }
        if (accountDAO.isUserUsed(account.getUsername())) {
            //throw new IllegalArgumentException("Sorry, the Username is already taken");
            return null;
        }
        return accountDAO.createAccount(account);
    }






}
