package co.aleksa.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = VetSpecialtyIdDeserializer.class)
public class VetSpecialtyId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public VetSpecialtyId() {
        super();
    }

    public VetSpecialtyId(Long id) {
        super(id);
    }
}

class VetSpecialtyIdDeserializer extends StdDeserializer<VetSpecialtyId> {
    private static final long serialVersionUID = 1L;

    public VetSpecialtyIdDeserializer() {
        this(null);
    }

    public VetSpecialtyIdDeserializer(Class<VetSpecialtyId> t) {
        super(t);
    }

    @Override
    public VetSpecialtyId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new VetSpecialtyId(Long.valueOf(jsonparser.getText()));
    }
}
