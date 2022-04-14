package co.aleksa.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ForAddressRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> ADDRESS = new PropertyPath<>("address");
    public static final PropertyPath<Integer> DROP = new PropertyPath<>("drop");
    public static final PropertyPath<Integer> TAKE = new PropertyPath<>("take");

    @Size(min = 5, max = 100)
    private String address;

    @NotNull
    @Min(0)
    private Integer drop;

    @NotNull
    @Min(0)
    private Integer take;

    private ForAddressRequest() {}

    public ForAddressRequest(String address, Integer drop, Integer take) {
        this();
        this.address = address;
        this.drop = drop;
        this.take = take;
    }

    public final String getAddress() {
        return address;
    }

    public final ForAddressRequest setAddress(String address) {
        this.address = address;
        return this;
    }

    public final Integer getDrop() {
        return drop;
    }

    public final ForAddressRequest setDrop(Integer drop) {
        this.drop = drop;
        return this;
    }

    public final Integer getTake() {
        return take;
    }

    public final ForAddressRequest setTake(Integer take) {
        this.take = take;
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
        if (ForAddressRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.address == null) ? 0 : this.address.hashCode());
        result = PRIME * result + ((this.drop == null) ? 0 : this.drop.hashCode());
        result = PRIME * result + ((this.take == null) ? 0 : this.take.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ForAddressRequest[" + "this.address=" + this.address + ", this.drop=" + this.drop + ", this.take=" + this.take + "]";
    }
}
