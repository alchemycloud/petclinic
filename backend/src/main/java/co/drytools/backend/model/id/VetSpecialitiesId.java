package co.drytools.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = VetSpecialitiesIdDeserializer.class)
public class VetSpecialitiesId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public VetSpecialitiesId() {
        super();
    }

    public VetSpecialitiesId(Long id) {
        super(id);
    }
}

class VetSpecialitiesIdDeserializer extends StdDeserializer<VetSpecialitiesId> {
    private static final long serialVersionUID = 1L;

    public VetSpecialitiesIdDeserializer() {
        this(null);
    }

    public VetSpecialitiesIdDeserializer(Class<VetSpecialitiesId> t) {
        super(t);
    }

    @Override
    public VetSpecialitiesId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new VetSpecialitiesId(Long.valueOf(jsonparser.getText()));
    }
}
