package co.aleksa.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = TenantIdDeserializer.class)
public class TenantId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public TenantId() {
        super();
    }

    public TenantId(Long id) {
        super(id);
    }
}

class TenantIdDeserializer extends StdDeserializer<TenantId> {
    private static final long serialVersionUID = 1L;

    public TenantIdDeserializer() {
        this(null);
    }

    public TenantIdDeserializer(Class<TenantId> t) {
        super(t);
    }

    @Override
    public TenantId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new TenantId(Long.valueOf(jsonparser.getText()));
    }
}
