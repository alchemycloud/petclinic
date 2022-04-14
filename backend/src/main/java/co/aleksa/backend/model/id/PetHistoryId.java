package co.aleksa.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = PetHistoryIdDeserializer.class)
public class PetHistoryId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public PetHistoryId() {
        super();
    }

    public PetHistoryId(Long id) {
        super(id);
    }
}

class PetHistoryIdDeserializer extends StdDeserializer<PetHistoryId> {
    private static final long serialVersionUID = 1L;

    public PetHistoryIdDeserializer() {
        this(null);
    }

    public PetHistoryIdDeserializer(Class<PetHistoryId> t) {
        super(t);
    }

    @Override
    public PetHistoryId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new PetHistoryId(Long.valueOf(jsonparser.getText()));
    }
}
