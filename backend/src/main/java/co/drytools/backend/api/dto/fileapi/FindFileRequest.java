package co.drytools.backend.api.dto.fileapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FindFileRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> KEY = new PropertyPath<>("key");
    public static final PropertyPath<String> FILE_NAME = new PropertyPath<>("fileName");

    @NotNull
    @Size(max = 255)
    private String key;

    @NotNull
    @Size(max = 255)
    private String fileName;

    private FindFileRequest() {}

    public FindFileRequest(String key, String fileName) {
        this();
        this.key = key;
        this.fileName = fileName;
    }

    public final String getKey() {
        return key;
    }

    public final FindFileRequest setKey(String key) {
        this.key = key;
        return this;
    }

    public final String getFileName() {
        return fileName;
    }

    public final FindFileRequest setFileName(String fileName) {
        this.fileName = fileName;
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
        if (FindFileRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.key == null) ? 0 : this.key.hashCode());
        result = PRIME * result + ((this.fileName == null) ? 0 : this.fileName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "FindFileRequest[" + "this.key=" + this.key + ", this.fileName=" + this.fileName + "]";
    }
}
