package co.drytools.backend.api.dto.fileapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FileDTO implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> PATH = new PropertyPath<>("path");

    @NotNull
    @Size(max = 255)
    private String path;

    private FileDTO() {}

    public FileDTO(String path) {
        this();
        this.path = path;
    }

    public final String getPath() {
        return path;
    }

    public final FileDTO setPath(String path) {
        this.path = path;
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
        if (FileDTO.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.path == null) ? 0 : this.path.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "FileDTO[" + "this.path=" + this.path + "]";
    }
}
