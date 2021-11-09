package co.drytools.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = VetSpecialityIdDeserializer.class)
public class VetSpecialityId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public VetSpecialityId() {
        super();
    }

    public VetSpecialityId(Long id) {
        super(id);
    }
}

class VetSpecialityIdDeserializer extends StdDeserializer<VetSpecialityId> {
    private static final long serialVersionUID = 1L;

    public VetSpecialityIdDeserializer() {
        this(null);
    }

    public VetSpecialityIdDeserializer(Class<VetSpecialityId> t) {
        super(t);
    }

    @Override
    public VetSpecialityId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new VetSpecialityId(Long.valueOf(jsonparser.getText()));
    }
}
