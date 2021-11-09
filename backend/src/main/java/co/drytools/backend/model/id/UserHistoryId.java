package co.drytools.backend.model.id;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@JsonDeserialize(using = UserHistoryIdDeserializer.class)
public class UserHistoryId extends AbstractId {
    private static final long serialVersionUID = 1L;

    public UserHistoryId() {
        super();
    }

    public UserHistoryId(Long id) {
        super(id);
    }
}

class UserHistoryIdDeserializer extends StdDeserializer<UserHistoryId> {
    private static final long serialVersionUID = 1L;

    public UserHistoryIdDeserializer() {
        this(null);
    }

    public UserHistoryIdDeserializer(Class<UserHistoryId> t) {
        super(t);
    }

    @Override
    public UserHistoryId deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        return new UserHistoryId(Long.valueOf(jsonparser.getText()));
    }
}
