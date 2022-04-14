package co.aleksa.backend.api;

import co.aleksa.backend.api.dto.fileapi.FindFileRequest;
import co.aleksa.backend.audit.AuditFacade;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileApiTransactionCaller {
    private static final Logger LOG = LoggerFactory.getLogger(FileApiTransactionCaller.class);

    @Inject private AuditFacade auditFacade;

    @Inject private FileApi fileApi;

    @Transactional
    public void findFile(FindFileRequest dto) {
        LOG.trace("findFile");

        fileApi.findFile(dto);
        auditFacade.flushInTransaction();
    }
}
