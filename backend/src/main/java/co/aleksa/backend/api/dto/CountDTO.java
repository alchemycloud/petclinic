package co.aleksa.backend.api.dto;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class CountDTO implements Serializable {
    private static final long serialVersionUID = 1;

    public static final PropertyPath<Long> COUNT = new PropertyPath<>("count");
    @NotNull private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final CountDTO other = (CountDTO) obj;
        if (count == null) {
            return other.count == null;
        } else {
            return count.equals(other.count);
        }
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((count == null) ? 0 : count.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format("CountDTO [count=%s]", count);
    }
}
