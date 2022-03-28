package co.aleksa.administration.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = TenantUserIdDeserializer.class)
public class TenantUserId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public TenantUserId() {
        super();
    }

    public TenantUserId(Long id) {
        super(id);
    }
}

class TenantUserIdDeserializer extends StdDeserializer<TenantUserId> {
    private static final long serialVersionUID = 1L;

    public TenantUserIdDeserializer() {
        this(null);
    }

    public TenantUserIdDeserializer(Class<TenantUserId> t) {
        super(t);
    }

    @Override
    public TenantUserId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new TenantUserId(Long.valueOf(jsonparser.getText()));
    }
}
