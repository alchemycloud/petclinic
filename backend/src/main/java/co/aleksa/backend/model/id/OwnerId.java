package co.aleksa.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = OwnerIdDeserializer.class)
public class OwnerId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public OwnerId() {
        super();
    }

    public OwnerId(Long id) {
        super(id);
    }
}

class OwnerIdDeserializer extends StdDeserializer<OwnerId> {
    private static final long serialVersionUID = 1L;

    public OwnerIdDeserializer() {
        this(null);
    }

    public OwnerIdDeserializer(Class<OwnerId> t) {
        super(t);
    }

    @Override
    public OwnerId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new OwnerId(Long.valueOf(jsonparser.getText()));
    }
}
