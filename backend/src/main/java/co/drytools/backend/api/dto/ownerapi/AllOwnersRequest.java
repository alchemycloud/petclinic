package co.drytools.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AllOwnersRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<Integer> PARAM = new PropertyPath<>("param");

    @NotNull
    @Min(0)
    private Integer param;

    private AllOwnersRequest() {}

    public AllOwnersRequest(Integer param) {
        this();
        this.param = param;
    }

    public final Integer getParam() {
        return param;
    }

    public final AllOwnersRequest setParam(Integer param) {
        this.param = param;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (AllOwnersRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.param == null) ? 0 : this.param.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "AllOwnersRequest[" + "this.param=" + this.param + "]";
    }
}
