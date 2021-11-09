package co.drytools.backend.api.dto;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PagedDTO<T> implements Serializable {
    private static final long serialVersionUID = 1;

    public static final PropertyPath<Long> TOTAL_COUNT = new PropertyPath<>("totalCount");
    public static final PropertyPath<List<?>> RESULTS = new PropertyPath<>("results");

    @NotNull private List<T> results;

    @NotNull
    @Min(0)
    private Long totalCount;

    public PagedDTO() {}

    public PagedDTO(List<T> results, Long totalCount) {
        this.results = results;
        this.totalCount = totalCount;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final PagedDTO other = (PagedDTO) obj;
        if (results == null) {
            if (other.results != null) return false;
        } else if (!results.equals(other.results)) return false;
        if (totalCount == null) {
            return other.totalCount == null;
        } else {
            return totalCount.equals(other.totalCount);
        }
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((results == null) ? 0 : results.hashCode());
        result = PRIME * result + ((totalCount == null) ? 0 : totalCount.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "PagedDTO [results=" + results + ", totalCount=" + totalCount + "]";
    }
}
