package co.drytools.backend.service;

import co.drytools.backend.api.dto.fileapi.FileUploadDTO;
import co.drytools.backend.config.CustomProperties;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@Service
public class FileStorageService {
    private static final Logger LOG = LoggerFactory.getLogger(FileStorageService.class);

    @Inject private CustomProperties customProperties;

    public void store(Optional<FileUploadDTO> fileUploadDto, String relativeFilePath) throws IOException {
        if (fileUploadDto.isPresent()) {
            store(fileUploadDto.get(), relativeFilePath);
        }
    }

    public void store(FileUploadDTO fileUploadDto, String relativeFilePath) throws IOException {

        LOG.debug(".store(fileName: {}, relativeFilePath: {}", fileUploadDto.getFileName(), relativeFilePath);

        final byte[] content = Base64Utils.decodeFromString(fileUploadDto.getBase64());
        final String absoluteFilePath = customProperties.getStorageFolder() + "/" + relativeFilePath;
        final File file = new File(absoluteFilePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        Files.write(Paths.get(file.getPath()), content);
    }

    public void update(Optional<String> oldFilePath, Optional<FileUploadDTO> newFileUploadDto, String relativeFilePath) throws IOException {
        if (oldFilePath.isPresent() && newFileUploadDto.map(FileUploadDTO::getBase64).isPresent()) {
            update(oldFilePath.get(), newFileUploadDto.get());
        } else if (oldFilePath.isPresent() && newFileUploadDto.isEmpty()) {
            delete(oldFilePath.get());
        } else if (oldFilePath.isEmpty() && newFileUploadDto.isPresent()) {
            store(newFileUploadDto.get(), relativeFilePath);
        }
    }

    public void update(String oldFilePath, FileUploadDTO newFileUploadDto) throws IOException {

        LOG.debug(".update(oldFilePath: {}, newFileName: {}", oldFilePath, newFileUploadDto.getFileName());

        final byte[] content = Base64Utils.decodeFromString(newFileUploadDto.getBase64());
        final String[] splitOldFilePath = oldFilePath.split("/");
        final String key = splitOldFilePath[splitOldFilePath.length - 2];
        final String oldFileName = splitOldFilePath[splitOldFilePath.length - 1];

        final String filePath = customProperties.getStorageFolder() + "/" + key;

        final File oldFile = new File(filePath, oldFileName);
        final byte[] oldFileContent = IOUtils.toByteArray(new FileInputStream(oldFile));
        final File newFile = new File(filePath, newFileUploadDto.getFileName());

        if (Arrays.equals(oldFileContent, content) && oldFileName.equals(newFileUploadDto.getFileName())) {
            return;
        }
        if (oldFile.exists()) {
            oldFile.delete();
        }
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        Files.write(Paths.get(newFile.getPath()), content);
    }

    public Optional<BufferedInputStream> retrieve(String key, String fileName) throws IOException {

        LOG.debug(".retrieve(key: {}, fileName: {}", key, fileName);

        final String filePath = customProperties.getStorageFolder() + "/" + key + "/" + fileName;
        final File file = new File(filePath);

        return Optional.of(new BufferedInputStream(new FileInputStream(file)));
    }

    public boolean delete(Optional<co.drytools.backend.model.File> fileModel) {
        return fileModel.isPresent() && delete(fileModel.get().getPath());
    }

    public boolean delete(String filePath) {

        LOG.debug(".delete(filePath: {}", filePath);

        final String path = customProperties.getStorageFolder() + "/" + filePath;
        final File file = new File(path);

        if (!file.exists()) {
            return false;
        }
        final File parent = file.getParentFile();

        return file.delete() && parent.delete();
    }

    public String generateFilePath(Optional<FileUploadDTO> newFileUploadDto) {
        return newFileUploadDto.map(fileUploadDTO -> UUID.randomUUID().toString() + "/" + fileUploadDTO.getFileName()).orElse(null);
    }

    public String updateFilePath(String oldFilePath, FileUploadDTO newFileUploadDto) {
        if (oldFilePath.endsWith(newFileUploadDto.getFileName()) && newFileUploadDto.getBase64() == null) {
            return oldFilePath;
        }
        return updatePath(oldFilePath, newFileUploadDto.getFileName());
    }

    public String updateFilePath(Optional<String> oldFilePath, Optional<FileUploadDTO> newFileUploadDto) {
        if (oldFilePath.isEmpty() && newFileUploadDto.isPresent()) {
            return generateFilePath(newFileUploadDto);
        } else if (newFileUploadDto.map(FileUploadDTO::getBase64).isPresent()) {
            return updatePath(oldFilePath.get(), newFileUploadDto.get().getFileName());
        } else if (newFileUploadDto.isPresent() && oldFilePath.isPresent()) {
            return oldFilePath.get();
        }
        return null;
    }

    private String updatePath(String oldFilePath, String fileName) {
        return oldFilePath.split("/")[0] + "/" + fileName;
    }
}
