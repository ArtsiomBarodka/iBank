package by.javatr.ibank.bean;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class User extends AbstractBean {
    private static final long serialVersionUID = -8974389076808990635L;

    private String name;
    private double income;
    private double dayExpense;
    private double monthExpense;
    private Timestamp timestamp;
    private List<Category> categories;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getDayExpense() {
        return dayExpense;
    }

    public void setDayExpense(double dayExpense) {
        this.dayExpense = dayExpense;
    }

    public double getMonthExpense() {
        return monthExpense;
    }

    public void setMonthExpense(double monthExpense) {
        this.monthExpense = monthExpense;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        String result = getClass().getName() + "@" +
                "name='" + name + '\'' +
                ", income=" + income +
                ", dayExpense=" + dayExpense +
                ", monthExpense=" + monthExpense +
                ", timestamp=" + timestamp +
                ", categories={";

        for (Category c: categories) {
            result = result + c + "; ";
        }

        result += "}";

        return result;
    }
}
