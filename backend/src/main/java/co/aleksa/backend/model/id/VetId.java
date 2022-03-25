package co.aleksa.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = VetIdDeserializer.class)
public class VetId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public VetId() {
        super();
    }

    public VetId(Long id) {
        super(id);
    }
}

class VetIdDeserializer extends StdDeserializer<VetId> {
    private static final long serialVersionUID = 1L;

    public VetIdDeserializer() {
        this(null);
    }

    public VetIdDeserializer(Class<VetId> t) {
        super(t);
    }

    @Override
    public VetId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new VetId(Long.valueOf(jsonparser.getText()));
    }
}
