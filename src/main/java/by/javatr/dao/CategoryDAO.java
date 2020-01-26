package by.javatr.ibank.dao;

import by.javatr.ibank.bean.Category;
import by.javatr.ibank.dao.exception.DAOException;

import java.util.List;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public interface CategoryDAO {
    void createCategory(Category category) throws DAOException;
    List<Category> getAllByUserId(long userId) throws DAOException;
    void updateCategory(Category category) throws DAOException;
    boolean deleteCategory(long userId, String categoryName) throws DAOException;
    void deleteAllByUserId(long userId) throws DAOException;
    Category getCategory(long userId, String categoryName) throws DAOException;
}
