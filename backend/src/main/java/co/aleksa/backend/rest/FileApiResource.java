package co.aleksa.backend.rest;

import co.aleksa.backend.api.FileApiCaller;
import co.aleksa.backend.service.FileStorageService;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Optional;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class FileApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(FileApiResource.class);

    @Inject private FileApiCaller fileApiCaller;

    @Inject private FileStorageService fileStorageService;

    @GetMapping(value = "/file/{key}/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> findFile(HttpServletResponse response, @PathVariable String key, @PathVariable String fileName) throws IOException {
        LOG.debug("GET /file/{}/{}", key, fileName);

        final Optional<BufferedInputStream> result = fileStorageService.retrieve(key, fileName);

        if (result.isPresent()) {
            final String mimeType = URLConnection.guessContentTypeFromStream(result.get());
            final HttpHeaders headers = new HttpHeaders();
            final int oneWeek = 7 * 24 * 60 * 60;
            response.setHeader("Cache-Control", "no-cache, no-store, max-age=" + oneWeek + ", must-revalidate");
            response.setContentType(mimeType);

            IOUtils.copy(result.get(), response.getOutputStream());
            return ResponseEntity.ok().headers(headers).build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
