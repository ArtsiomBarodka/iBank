package by.javatr.ibank.service.impl;

import by.javatr.ibank.Constants;
import by.javatr.ibank.bean.Account;
import by.javatr.ibank.dao.AccountDAO;
import by.javatr.ibank.dao.exception.DAOException;
import by.javatr.ibank.dao.factory.DAOFactory;
import by.javatr.ibank.service.AuthenticationService;
import by.javatr.ibank.service.exception.ServiceValidationException;
import by.javatr.ibank.service.exception.ServiceSourceException;
import by.javatr.ibank.service.validation.ServiceValidator;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public boolean hasAccount(String login)
                throws ServiceValidationException, ServiceSourceException {
        if(!ServiceValidator.isValidLogin(login)){
            throw new ServiceValidationException("Incorrect login!");
        }

        try {
            DAOFactory factory = DAOFactory.getInstance();
            AccountDAO accountDAO = factory.getFileAccountDAO();
            return accountDAO.hasAccount(login);
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }
    }

    @Override
    public void createAccount(String login, int password, String email)
                throws ServiceValidationException, ServiceSourceException {
        if(!ServiceValidator.isValidLogin(login)){
            throw new ServiceValidationException("Incorrect login!");
        }
        if(!ServiceValidator.isValidPassword(password)){
            throw new ServiceValidationException("Incorrect password!");
        }
        if(!ServiceValidator.isValidEmail(email)){
            throw new ServiceValidationException("Incorrect email!");
        }

        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        account.setEmail(email);
        account.setRole(Constants.Role.CLIENT);

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            AccountDAO accountDAO = daoFactory.getFileAccountDAO();
            accountDAO.registration(account);
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }
    }

    @Override
    public boolean singIn(String login, int password)
                throws ServiceValidationException, ServiceSourceException {
        if(!ServiceValidator.isValidLogin(login)){
            throw new ServiceValidationException("Incorrect login!");
        }
        if(!ServiceValidator.isValidPassword(password)){
            throw new ServiceValidationException("Incorrect password!");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            AccountDAO accountDAO = daoFactory.getFileAccountDAO();
            return accountDAO.singIn(login, password);
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }

    }

    @Override
    public boolean deleteAccount(String login, int password)
                throws ServiceSourceException, ServiceValidationException {
        if(!ServiceValidator.isValidLogin(login)){
            throw new ServiceValidationException("Incorrect login!");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            AccountDAO accountDAO = daoFactory.getFileAccountDAO();
            return accountDAO.deleteAccount(login, password);
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }
    }

    @Override
    public String getEmail(String login) throws ServiceSourceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            AccountDAO accountDAO = daoFactory.getFileAccountDAO();
            return accountDAO.getAccountByName(login).getEmail();
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }
    }

    @Override
    public Constants.Role getRole(String login)
                throws ServiceValidationException, ServiceSourceException {
        if(!ServiceValidator.isValidLogin(login)){
            throw new ServiceValidationException("Incorrect login!");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            AccountDAO accountDAO = daoFactory.getFileAccountDAO();
            return accountDAO.getAccountByName(login).getRole();
        } catch (DAOException e){
            throw new ServiceSourceException("Can`t load source!", e);
        }
    }
}
