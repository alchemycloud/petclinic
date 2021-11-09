package co.drytools.backend.service;

import co.drytools.backend.model.DataVersion;
import co.drytools.backend.repository.DataVersionRepository;
import co.drytools.backend.service.data.DataProcessor;
import co.drytools.backend.util.TimeUtil;
import java.util.Optional;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataService.class);
    private static final Integer INITIAL_VERSION = 0;

    @Inject private DataVersionRepository dataVersionRepository;

    public Optional<DataVersion> getCurrentVersion() {
        return dataVersionRepository.findDataVersion();
    }

    public DataVersion createFirstVersion() {
        LOG.debug("Create first version");
        return dataVersionRepository.create(INITIAL_VERSION, INITIAL_VERSION, INITIAL_VERSION, INITIAL_VERSION, TimeUtil.now(), false, TimeUtil.now());
    }

    public void increaseVersion(DataProcessor dataProcessor) {

        final DataVersion currentVersion = getCurrentVersion().get();
        final String versionString =
                getVersionString(currentVersion.getMajor(), currentVersion.getMinor(), currentVersion.getRevision(), currentVersion.getNumber());

        currentVersion.setMajor(dataProcessor.major());
        currentVersion.setMinor(dataProcessor.minor());
        currentVersion.setRevision(dataProcessor.revision());
        currentVersion.setNumber(dataProcessor.number() + 1);

        final String newVersion =
                getVersionString(currentVersion.getMajor(), currentVersion.getMinor(), currentVersion.getRevision(), currentVersion.getNumber());

        LOG.debug("Increase tenant version from {} to {}", versionString, newVersion);

        dataVersionRepository.update(currentVersion);
    }

    private String getVersionString(Integer major, Integer minor, Integer revision, Integer number) {
        return major + "." + minor + "." + revision + "." + number;
    }

    public void unlockVersion() {
        LOG.debug("Unlock data version");
        final DataVersion currentVersion = getCurrentVersion().get();
        currentVersion.setLock(false);
        currentVersion.setTime(TimeUtil.now());
        dataVersionRepository.update(currentVersion);
    }

    public Boolean lockVersion() {
        LOG.debug("Lock data version");
        final DataVersion currentVersion = getCurrentVersion().get();
        if (currentVersion.getLock()) {
            LOG.error("Data version is already locked");
            return false;
        } else {
            currentVersion.setLockTime(TimeUtil.now());
            currentVersion.setLock(true);
            dataVersionRepository.update(currentVersion);
            LOG.debug("Data version locked successfully");
            return true;
        }
    }
}
