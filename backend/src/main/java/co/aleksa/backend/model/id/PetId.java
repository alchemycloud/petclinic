package co.aleksa.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = PetIdDeserializer.class)
public class PetId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public PetId() {
        super();
    }

    public PetId(Long id) {
        super(id);
    }
}

class PetIdDeserializer extends StdDeserializer<PetId> {
    private static final long serialVersionUID = 1L;

    public PetIdDeserializer() {
        this(null);
    }

    public PetIdDeserializer(Class<PetId> t) {
        super(t);
    }

    @Override
    public PetId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new PetId(Long.valueOf(jsonparser.getText()));
    }
}
