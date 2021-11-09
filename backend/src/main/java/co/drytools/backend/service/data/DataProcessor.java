package co.drytools.backend.service.data;

import co.drytools.backend.audit.AuditFacade;
import co.drytools.backend.model.DataProcessorLog;
import co.drytools.backend.repository.DataProcessorLogRepository;
import co.drytools.backend.service.DataService;
import co.drytools.backend.util.TimeUtil;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public abstract class DataProcessor implements Comparable<DataProcessor> {
    private static final Logger LOG = LoggerFactory.getLogger(DataProcessor.class);

    @Inject private DataService dataService;

    @Inject private AuditFacade auditFacade;

    @Inject private DataProcessorLogRepository dataProcessorLogRepository;

    public abstract Integer major();

    public abstract Integer minor();

    public abstract Integer revision();

    public abstract Integer number();

    public abstract Optional<String> description();

    @Transactional
    public void process() {
        LOG.info("START data processor: {}", this);
        if (description().isPresent()) LOG.info(description().get());

        final DataProcessorLog log =
                dataProcessorLogRepository.create(major(), minor(), revision(), number(), description().orElse(""), TimeUtil.now(), Optional.empty());

        processing();

        log.setCompleted(Optional.of(TimeUtil.now()));
        dataProcessorLogRepository.update(log);
        LOG.info("COMPLETE data processor: {}", this);

        dataService.increaseVersion(this);

        auditFacade.flushInTransaction();
        auditFacade.flushAfterTransaction();
    }

    protected abstract void processing();

    @Override
    public int compareTo(DataProcessor processor) {
        return this.compare(processor.major(), processor.minor(), processor.revision(), processor.number());
    }

    public int compare(Integer major, Integer minor, Integer revision, Integer number) {
        if (major().equals(major)) {
            if (minor().equals(minor)) {
                if (revision().equals(revision)) {
                    return number().compareTo(number);
                } else {
                    return revision().compareTo(revision);
                }
            } else {
                return minor().compareTo(minor);
            }
        } else {
            return major().compareTo(major);
        }
    }
}
