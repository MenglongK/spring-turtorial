package co.istad.menglong.ecommerce.features.order;

import co.istad.menglong.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.menglong.ecommerce.features.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public interface OrderService {

    OrderResponse createNew(CreateOrderRequest createOrderRequest);

    Page<OrderResponse> findAll(int page, int size);

    OrderResponse findById(UUID id);

    OrderResponse updateById(UUID id, CreateOrderRequest createOrderRequest);


}
