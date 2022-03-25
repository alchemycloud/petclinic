package co.aleksa.backend.model.id;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

@JsonSerialize(using = AbstractIdSerializer.class)
public abstract class AbstractId extends Number implements Comparable<AbstractId> {
    private static final long serialVersionUID = 1L;

    private Long value;

    public AbstractId() {}

    public AbstractId(Long id) {
        this.value = id;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public int compareTo(AbstractId o) {
        return this.getValue().compareTo(o.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME + getClass().hashCode();

        if (this.value != null) {
            return PRIME * result + this.value.hashCode();
        }

        return PRIME * result + super.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

class AbstractIdSerializer extends StdSerializer<AbstractId> {
    private static final long serialVersionUID = 1L;

    public AbstractIdSerializer() {
        this(null);
    }

    public AbstractIdSerializer(Class<AbstractId> t) {
        super(t);
    }

    @Override
    public void serialize(AbstractId value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeNumber(value.getValue());
    }
}
