package co.istad.menglong.ecommerce.features.order;

import co.istad.menglong.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.menglong.ecommerce.features.order.dto.OrderLineDto;
import co.istad.menglong.ecommerce.features.order.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order mapCreateOrderRequestToOrder(CreateOrderRequest createOrderRequest);

    OrderResponse mapOrderToOrderResponse(Order order);

    default List<OrderLineDto> mapOrderLineToOrderLineDto(List<OrderLine> orderLines) {
        return orderLines.stream().map(
                        orderLine -> OrderLineDto.builder()
                                .productCode(orderLine.getProduct().getCode())
                                .qty(orderLine.getQty())
                                .discount(orderLine.getDiscount())
                                .build())
                .collect(Collectors.toList());
    }

    void updateOrderFromRequest(@MappingTarget Order order, CreateOrderRequest createOrderRequest);
}
