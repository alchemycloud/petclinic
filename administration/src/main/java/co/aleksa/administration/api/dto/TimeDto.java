package co.aleksa.administration.api.dto;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class TimeDto implements Serializable {
    private static final long serialVersionUID = 1;

    public static final PropertyPath<Integer> DAYS = new PropertyPath<>("days");
    public static final PropertyPath<Integer> HOURS = new PropertyPath<>("hours");
    public static final PropertyPath<Integer> MINUTES = new PropertyPath<>("minutes");
    public static final PropertyPath<Long> TOTAL_MILLISECONDS = new PropertyPath<>("totalMilliseconds");

    @NotNull private Integer days;

    @NotNull private Integer hours;

    @NotNull private Integer minutes;

    @NotNull private Long totalMilliseconds;

    private TimeDto() {}

    public TimeDto(Integer days, Integer hours, Integer minutes, Long totalMilliseconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.totalMilliseconds = totalMilliseconds;
    }

    public Integer getDays() {
        return days;
    }

    public Integer getHours() {
        return hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public Long getTotalMilliseconds() {
        return totalMilliseconds;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final TimeDto other = (TimeDto) obj;
        if (this.days != null && other.days != null && !this.days.equals(other.days)) return false;
        if (this.hours != null && other.hours != null && !this.hours.equals(other.hours)) return false;
        if (this.minutes != null && other.minutes != null && !this.minutes.equals(other.minutes)) return false;
        return !(this.totalMilliseconds != null && other.totalMilliseconds != null && !this.totalMilliseconds.equals(other.totalMilliseconds));
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.days == null) ? 0 : this.days.hashCode());
        result = PRIME * result + ((this.hours == null) ? 0 : this.hours.hashCode());
        result = PRIME * result + ((this.minutes == null) ? 0 : this.minutes.hashCode());
        result = PRIME * result + ((this.totalMilliseconds == null) ? 0 : this.totalMilliseconds.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "TimeDto["
                + "this.days="
                + this.days
                + ", this.hours="
                + this.hours
                + ", this.minutes="
                + this.minutes
                + ", this.totalMilliseconds="
                + this.totalMilliseconds
                + "]";
    }
}
