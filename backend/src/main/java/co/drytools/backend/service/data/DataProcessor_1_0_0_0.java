package co.drytools.backend.service.data;

import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DataProcessor_1_0_0_0 extends DataProcessor {
    protected static final Logger LOG = LoggerFactory.getLogger(DataProcessor_1_0_0_0.class);

    @Override
    public Integer major() {
        return 1;
    }

    @Override
    public Integer minor() {
        return 0;
    }

    @Override
    public Integer revision() {
        return 0;
    }

    @Override
    public Integer number() {
        return 0;
    }

    @Override
    public Optional<String> description() {
        return Optional.empty();
    }

    @Override
    protected void processing() {
        // TODO implement it
    }
}
