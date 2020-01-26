package by.javatr.ibank.dao;

import by.javatr.ibank.bean.Account;
import by.javatr.ibank.dao.exception.DAOException;

import java.util.List;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public interface AccountDAO {
    boolean hasAccount(String login) throws DAOException;
    void registration(Account account) throws DAOException;
    boolean singIn(String login, int password) throws DAOException;
    Account getAccountByName(String login) throws DAOException;
    boolean deleteAccount(String login, int password) throws DAOException;
    List<String> getAllUsersEmails() throws DAOException;
}
