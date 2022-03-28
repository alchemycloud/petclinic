package co.aleksa.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = UserIdDeserializer.class)
public class UserId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public UserId() {
        super();
    }

    public UserId(Long id) {
        super(id);
    }
}

class UserIdDeserializer extends StdDeserializer<UserId> {
    private static final long serialVersionUID = 1L;

    public UserIdDeserializer() {
        this(null);
    }

    public UserIdDeserializer(Class<UserId> t) {
        super(t);
    }

    @Override
    public UserId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new UserId(Long.valueOf(jsonparser.getText()));
    }
}
