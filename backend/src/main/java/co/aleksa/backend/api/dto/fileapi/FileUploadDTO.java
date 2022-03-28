package co.aleksa.backend.api.dto.fileapi;

import cloud.alchemy.fabut.property.PropertyPath;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FileUploadDTO implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<String> FILE_NAME = new PropertyPath<>("fileName");
    public static final PropertyPath<String> BASE_64 = new PropertyPath<>("base64");

    @NotNull
    @Size(max = 255)
    private String fileName;

    @NotNull
    @Size(max = 255)
    private String base64;

    private FileUploadDTO() {}

    public FileUploadDTO(String fileName, String base64) {
        this();
        this.fileName = fileName;
        this.base64 = base64;
    }

    public final String getFileName() {
        return fileName;
    }

    public final FileUploadDTO setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public final String getBase64() {
        return base64;
    }

    public final FileUploadDTO setBase64(String base64) {
        this.base64 = base64;
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
        if (FileUploadDTO.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.fileName == null) ? 0 : this.fileName.hashCode());
        result = PRIME * result + ((this.base64 == null) ? 0 : this.base64.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "FileUploadDTO[" + "this.fileName=" + this.fileName + "]";
    }
}
