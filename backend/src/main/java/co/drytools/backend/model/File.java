package co.drytools.backend.model;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class File implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final PropertyPath<String> PATH = new PropertyPath<>("path");

    @NotNull
    @Size(max = 255)
    private String path;

    public File() {}

    public File(String path) {
        setPath(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "File[" + "this.path=" + this.path + "]";
    }
}
