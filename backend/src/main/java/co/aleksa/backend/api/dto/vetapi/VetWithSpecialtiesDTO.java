package co.aleksa.backend.api.dto.vetapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VetWithSpecialtiesDTO implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> FIRST_NAME = new PropertyPath<>("firstName");
    public static final PropertyPath<String> LAST_NAME = new PropertyPath<>("lastName");
    public static final PropertyPath<List<String>> SPECIALTIES = new PropertyPath<>("specialties");

    @NotNull
    @Size(min = 1, max = 40)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String lastName;

    @Size(min = 3, max = 50)
    private List<String> specialties = new ArrayList<>();

    private VetWithSpecialtiesDTO() {}

    public VetWithSpecialtiesDTO(String firstName, String lastName, List<String> specialties) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialties = specialties;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final VetWithSpecialtiesDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public final String getLastName() {
        return lastName;
    }

    public final VetWithSpecialtiesDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public final List<String> getSpecialties() {
        return specialties;
    }

    public final VetWithSpecialtiesDTO setSpecialties(List<String> specialties) {
        this.specialties = specialties;
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
        if (VetWithSpecialtiesDTO.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = PRIME * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "VetWithSpecialtiesDTO["
                + "this.firstName="
                + this.firstName
                + ", this.lastName="
                + this.lastName
                + ", this.specialties="
                + this.specialties
                + "]";
    }
}
