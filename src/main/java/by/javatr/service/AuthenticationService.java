package by.javatr.ibank.service;

import by.javatr.ibank.Constants;
import by.javatr.ibank.service.exception.ServiceValidationException;
import by.javatr.ibank.service.exception.ServiceSourceException;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public interface AuthenticationService {
    boolean hasAccount(String login)
                throws ServiceValidationException, ServiceSourceException;

    void createAccount(String login, int password, String email)
                throws ServiceValidationException, ServiceSourceException;

    boolean singIn(String login, int password)
                throws ServiceValidationException, ServiceSourceException;

    boolean deleteAccount(String login, int password)
                throws ServiceSourceException, ServiceValidationException;

    String getEmail(String login) throws ServiceSourceException;

    Constants.Role getRole(String login)
                throws ServiceValidationException, ServiceSourceException;
}
