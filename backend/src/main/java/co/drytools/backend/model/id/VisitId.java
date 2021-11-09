package co.drytools.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = VisitIdDeserializer.class)
public class VisitId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public VisitId() {
        super();
    }

    public VisitId(Long id) {
        super(id);
    }
}

class VisitIdDeserializer extends StdDeserializer<VisitId> {
    private static final long serialVersionUID = 1L;

    public VisitIdDeserializer() {
        this(null);
    }

    public VisitIdDeserializer(Class<VisitId> t) {
        super(t);
    }

    @Override
    public VisitId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new VisitId(Long.valueOf(jsonparser.getText()));
    }
}
