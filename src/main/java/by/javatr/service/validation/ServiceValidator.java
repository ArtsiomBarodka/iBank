package by.javatr.ibank.service.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public final class ServiceValidator {
    private static final int MAX_LOGIN_SYMBOLS = 32;
    private static final int MAX_PASSWORD_NUMBERS = 8;
    private static final int MIN_PASSWORD_NUMBERS = 3;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final int MAX_CATEGORY_SYMBOLS = 20;
    private static final String NUMBER = "\\d+";

    public static boolean isValidLogin(String login){
        if(login == null || login.isEmpty()){
            return false;
        }
        if(login.length() > MAX_LOGIN_SYMBOLS){
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(int pass){
        if((String.valueOf(pass).length() > MAX_PASSWORD_NUMBERS) ||
                (String.valueOf(pass).length() < MIN_PASSWORD_NUMBERS)){
            return false;
        }

        return true;
    }

    public static boolean isValidEmail(String email){
        if(email == null && email.isEmpty()){
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean isValidCategory(String categoryName){
        if(categoryName == null || categoryName.isEmpty()){
            return false;
        }

        if(categoryName.length() > MAX_CATEGORY_SYMBOLS){
            return false;
        }

        return true;
    }

    public static boolean isNumber(String string) {
        if (string == null) return false;
        return string.matches(NUMBER);
    }
}
