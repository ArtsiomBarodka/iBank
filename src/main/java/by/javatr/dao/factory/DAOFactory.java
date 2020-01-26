package by.javatr.ibank.dao.factory;

import by.javatr.ibank.Constants;
import by.javatr.ibank.dao.AccountDAO;
import by.javatr.ibank.dao.CategoryDAO;
import by.javatr.ibank.dao.UserDAO;
import by.javatr.ibank.dao.impl.FileAccountDAO;
import by.javatr.ibank.dao.impl.FileCategoryDAO;
import by.javatr.ibank.dao.impl.FileUserDAO;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public final class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final AccountDAO fileAccountDAO =
                new FileAccountDAO(Constants.ACCOUNT_BASE_FILE_PATH);
    private final CategoryDAO fileCategoryDAO =
                new FileCategoryDAO(Constants.CATEGORY_BASE_FILE_PATH);
    private final UserDAO fileUserDAO =
                new FileUserDAO(Constants.USER_BASE_FILE_PATH);

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public AccountDAO getFileAccountDAO() {
        return fileAccountDAO;
    }

    public CategoryDAO getFileCategoryDAO() {
        return fileCategoryDAO;
    }

    public UserDAO getFileUserDAO() {
        return fileUserDAO;
    }
}
