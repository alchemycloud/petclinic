package co.drytools.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = DataVersionIdDeserializer.class)
public class DataVersionId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public DataVersionId() {
        super();
    }

    public DataVersionId(Long id) {
        super(id);
    }
}

class DataVersionIdDeserializer extends StdDeserializer<DataVersionId> {
    private static final long serialVersionUID = 1L;

    public DataVersionIdDeserializer() {
        this(null);
    }

    public DataVersionIdDeserializer(Class<DataVersionId> t) {
        super(t);
    }

    @Override
    public DataVersionId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new DataVersionId(Long.valueOf(jsonparser.getText()));
    }
}
