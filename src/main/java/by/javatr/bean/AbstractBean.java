package by.javatr.ibank.bean;

import java.io.Serializable;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public abstract class AbstractBean implements Serializable {
    private static final long serialVersionUID = 136853938709211309L;

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Long.hashCode(id);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractBean other = (AbstractBean) obj;
        return (id == other.id);
    }
}
