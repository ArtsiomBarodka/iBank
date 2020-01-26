package by.javatr.ibank.dao;

import by.javatr.ibank.bean.User;
import by.javatr.ibank.dao.exception.DAOException;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public interface UserDAO {
    void saveUser(User user) throws DAOException;
    User getUserByName(String name) throws DAOException;
    User getUserById(long id) throws DAOException;
    void updateUser(User user) throws DAOException;
    void deleteUser(long id) throws DAOException;
}
