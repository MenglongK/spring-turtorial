package co.istad.menglong.ecommerce.features.order;

import co.istad.menglong.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.menglong.ecommerce.features.order.dto.OrderResponse;
import co.istad.menglong.ecommerce.features.product.Product;
import co.istad.menglong.ecommerce.features.product.ProductRepository;
import co.istad.menglong.ecommerce.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createNew(CreateOrderRequest createOrderRequest) {

        List<OrderLine> validOrderLine = new ArrayList<>();
        final Order order = orderMapper.mapCreateOrderRequestToOrder(createOrderRequest);
        // TODO: Implement this method
        // validate product code
        boolean isValid = createOrderRequest.orderLines().stream().allMatch(orderLineDto -> {
            boolean isExisting = productRepository.existsById(orderLineDto.productCode());
            if (isExisting) {
                Product validProduct = productRepository.findById(orderLineDto.productCode()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product code has not been found"));
                OrderLine orderLine = new OrderLine();
                orderLine.setProduct(validProduct);
                orderLine.setQty(orderLineDto.qty());
                orderLine.setDiscount(orderLineDto.discount());
                orderLine.setOrder(order);
                validOrderLine.add(orderLine);
            }
            return isExisting;
        });
        if (!isValid)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product code not found");

        // System data generation
        order.setOrderedAt(Instant.now());
        order.setIsDeleted(false);
        order.setOrderedBy(AuthUtils.extractUserId());
        order.setOrderLines(validOrderLine);

        Order saveOrder = orderRepository.save(order);

        return orderMapper.mapOrderToOrderResponse(saveOrder);
    }

    @Override
    public Page<OrderResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "orderedAt");
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(orderMapper::mapOrderToOrderResponse);
    }

    @Override
    public OrderResponse findById(UUID id) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return orderMapper.mapOrderToOrderResponse(order);
    }

    @Override
    public OrderResponse updateById(UUID id, CreateOrderRequest createOrderRequest) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        // Clear existing order lines and update with new ones
        order.getOrderLines().clear();

        List<OrderLine> validOrderLine = new ArrayList<>();
        Order finalOrder = order;
        boolean isValid = createOrderRequest.orderLines().stream().allMatch(orderLineDto -> {
            boolean isExisting = productRepository.existsById(orderLineDto.productCode());
            if (isExisting) {
                Product validProduct = productRepository.findById(orderLineDto.productCode()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product code has not been found"));
                OrderLine orderLine = new OrderLine();
                orderLine.setProduct(validProduct);
                orderLine.setQty(orderLineDto.qty());
                orderLine.setDiscount(orderLineDto.discount());
                orderLine.setOrder(finalOrder);
                validOrderLine.add(orderLine);
            }
            return isExisting;
        });

        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product code not found");
        }

        order.setOrderLines(validOrderLine);
        order.setRemark(createOrderRequest.remark());
        order.setOrderedBy(AuthUtils.extractUserId());
        order.setOrderLines(validOrderLine);

        order = orderRepository.save(order);

        return orderMapper.mapOrderToOrderResponse(order);
    }
}