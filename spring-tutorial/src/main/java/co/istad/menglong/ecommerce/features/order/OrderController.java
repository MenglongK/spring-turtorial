package co.istad.menglong.ecommerce.features.order;

import co.istad.menglong.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.menglong.ecommerce.features.order.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponse createNew(
            @Valid @RequestBody CreateOrderRequest createOrderRequest
    ) {
        return orderService.createNew(createOrderRequest);
    }

    @GetMapping
    public Page<OrderResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size) {
        return orderService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public OrderResponse findById(
            @PathVariable UUID id
    ) {
        return orderService.findById(id);
    }

    @PutMapping("/{id}")
    public OrderResponse updateById(
            @PathVariable UUID id,
            @Valid @RequestBody CreateOrderRequest createOrderRequest
    ) {
        return orderService.updateById(id, createOrderRequest);
    }
}
