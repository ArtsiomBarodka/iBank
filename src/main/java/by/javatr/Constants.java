package by.javatr.ibank;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class Constants {
    public static final String USER_BASE_FILE_PATH = "user.txt";
    public static final String CATEGORY_BASE_FILE_PATH = "category.txt";
    public static final String ACCOUNT_BASE_FILE_PATH = "account.txt";
    public static final String TEMP_FILE = "temp.txt";

    public static final String REQUEST_DELIMITER = " ";
    public static final String RESPONSE_DELIMITER = "/";

    public enum Role{
        ADMIN,
        CLIENT
    }

    public enum StandardCategorise{
        FOOD("Еда"),
        HOUSING("Жилье"),
        TRANSPORT("Транспорт"),
        ENTERTAINMENT("Развлечение"),
        CLOTHING("Одежда"),
        TAXI("Такси"),
        HYGIENE("Гигиена"),
        CAFE("Кафе"),
        HEALTH("Здоровье"),
        OTHER("Остальное");

        private String name;

        StandardCategorise(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum CommandName {
        WRONG_REQUEST,
        ACCOUNT_HELP,
        ACCOUNT_CREATE,
        ACCOUNT_DELETE,
        ACCOUNT_ENTER,
        ACCOUNT_EXIT,
        USER_HELP,
        USER_ADD,
        USER_BANK,
        USER_MONTH,
        USER_DAY,
        USER_DELETE,
        USER_CREATE,
        USER_EXIT,
        ADMIN_HELP,
        ADMIN_COUNT,
        ADMIN_SEND,
        ADMIN_EXIT
    }
}
