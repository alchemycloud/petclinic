package co.drytools.backend.repository.util;

import com.querydsl.core.types.Order;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderableField<E extends Enum> {

    private E field;

    private Order direction;

    public E getField() {
        return field;
    }

    public void setField(E field) {
        this.field = field;
    }

    public Order getDirection() {
        return direction;
    }

    public void setDirection(Order direction) {
        this.direction = direction;
    }

    public static <E extends Enum> List<OrderableField<E>> createOrderableFields(List<E> fields, List<Order> directions) {

        if (fields == null || directions == null) {
            return Collections.emptyList();
        }

        return IntStream.range(0, fields.size())
                .mapToObj(
                        i -> {
                            final OrderableField<E> orderableField = new OrderableField<>();
                            orderableField.setField(fields.get(i));
                            orderableField.setDirection(directions.get(i));
                            return orderableField;
                        })
                .collect(Collectors.toList());
    }
}
