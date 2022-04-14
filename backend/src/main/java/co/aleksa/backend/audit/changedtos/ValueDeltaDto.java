package co.aleksa.backend.audit.changedtos;

public class ValueDeltaDto {

    private Object original;
    private Object update;

    public ValueDeltaDto(Object original, Object update) {
        this.original = original;
        this.update = update;
    }

    public Object getOriginal() {
        return original;
    }

    public void setOriginal(Object original) {
        this.original = original;
    }

    public Object getUpdate() {
        return update;
    }

    public void setUpdate(Object update) {
        this.update = update;
    }
}
