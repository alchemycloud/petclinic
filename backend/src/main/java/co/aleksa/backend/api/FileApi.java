package co.aleksa.backend.api;

import co.aleksa.backend.api.dto.fileapi.FindFileRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FileApi {
    private static final Logger LOG = LoggerFactory.getLogger(FileApi.class);

    public void findFile(FindFileRequest dto) {
        LOG.trace("findFile");

        throw new UnsupportedOperationException();
    }
}
