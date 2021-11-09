package co.drytools.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = DataProcessorLogIdDeserializer.class)
public class DataProcessorLogId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public DataProcessorLogId() {
        super();
    }

    public DataProcessorLogId(Long id) {
        super(id);
    }
}

class DataProcessorLogIdDeserializer extends StdDeserializer<DataProcessorLogId> {
    private static final long serialVersionUID = 1L;

    public DataProcessorLogIdDeserializer() {
        this(null);
    }

    public DataProcessorLogIdDeserializer(Class<DataProcessorLogId> t) {
        super(t);
    }

    @Override
    public DataProcessorLogId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new DataProcessorLogId(Long.valueOf(jsonparser.getText()));
    }
}
