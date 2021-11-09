package co.drytools.backend.api;

import co.drytools.backend.api.dto.fileapi.FindFileRequest;
import co.drytools.backend.audit.AuditFacade;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class FileApiCaller {
    private static final Logger LOG = LoggerFactory.getLogger(FileApiCaller.class);

    @Lazy @Inject private AuditFacade auditFacade;

    @Inject private FileApiTransactionCaller fileApiTransactionCaller;

    public void findFile(FindFileRequest dto) {
        LOG.trace("findFile");

        fileApiTransactionCaller.findFile(dto);
        auditFacade.flushAfterTransaction();
    }
}
