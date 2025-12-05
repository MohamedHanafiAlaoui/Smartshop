package com.example.SmartShop.service;

import com.example.SmartShop.dto.OrderDto;
import com.example.SmartShop.dto.OrderItemDto;
import com.example.SmartShop.mapper.OrderItemMapper;
import com.example.SmartShop.mapper.OrderMapper;
import com.example.SmartShop.model.entitie.*;
import com.example.SmartShop.model.enums.OrderStatus;
import com.example.SmartShop.repository.AdminRepository;
import com.example.SmartShop.repository.ClientRepository;
import com.example.SmartShop.repository.OrderRepository;
import com.example.SmartShop.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final AdminRepository adminRepository;
    private final LoyaltyService loyaltyService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ClientRepository clientRepository,
                            ProductRepository productRepository,
                            OrderMapper orderMapper,
                            OrderItemMapper orderItemMapper,
                            AdminRepository adminRepository,
                            LoyaltyService loyaltyService) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.adminRepository = adminRepository;
        this.loyaltyService = loyaltyService;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto, Admin admin) {
        if (!adminRepository.existsById(admin.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin Not Found");

        Client client = clientRepository.findById(orderDto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        Order order = Order.create(client);
        boolean refused = false;

        BigDecimal sousTotalHT = BigDecimal.ZERO;

        for (OrderItemDto itemDto : orderDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            if (itemDto.getQuantite() > product.getStock()) {
                refused = true;
                continue;
            }

            OrderItem item = OrderItem.create(product, itemDto.getQuantite());
            order.addItem(item);

            sousTotalHT = sousTotalHT.add(item.getTotalLigneHT());

            product.setStock(product.getStock() - itemDto.getQuantite());
        }

        order.setSousTotalHT(sousTotalHT);

        BigDecimal remiseCodePromo = BigDecimal.ZERO;
        if ("PROMO10".equalsIgnoreCase(orderDto.getCodePromo())) {
            remiseCodePromo = sousTotalHT.multiply(BigDecimal.valueOf(0.10));
        }

        loyaltyService.updateLoyaltyLevel(client);
        BigDecimal remiseFidelite = loyaltyService.calculateDiscountAmount(
                sousTotalHT, loyaltyService.getFidelityDiscount(client, sousTotalHT));

        BigDecimal totalRemise = remiseCodePromo.add(remiseFidelite);

        order.setMontantRemise(totalRemise);
        order.setMontantHTApresRemise(sousTotalHT.subtract(totalRemise));

        BigDecimal tva = order.getMontantHTApresRemise().multiply(BigDecimal.valueOf(0.2));
        order.setTva(tva);
        order.setTotalTTC(order.getMontantHTApresRemise().add(tva));
        order.setMontantRestant(order.getTotalTTC());

        if (refused) {
            order.setStatus(OrderStatus.REFUSED);
        } else {
            order.setStatus(OrderStatus.PENDING);
        }

        Order savedOrder = orderRepository.save(order);
        clientRepository.save(client); // تحديث مستوى العميل بعد الطلب

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto findOrderById(Long id, Admin admin) {
        if (!adminRepository.existsById(admin.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin Not Found");

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders(Admin admin) {
        if (!adminRepository.existsById(admin.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin Not Found");

        List<Order> orders = orderRepository.findAll();
        return orderMapper.toDtoList(orders);
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Admin admin) {
        if (!adminRepository.existsById(admin.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin Not Found");

        Order order = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (orderDto.getStatus() != null) {
            order.setStatus(orderDto.getStatus());
        }

        if (orderDto.getCodePromo() != null) {
            order.setCodePromo(orderDto.getCodePromo());

            BigDecimal remiseCodePromo = BigDecimal.ZERO;
            if ("PROMO10".equalsIgnoreCase(orderDto.getCodePromo())) {
                remiseCodePromo = order.getSousTotalHT().multiply(BigDecimal.valueOf(0.10));
            }

            BigDecimal remiseFidelite = loyaltyService.calculateDiscountAmount(
                    order.getSousTotalHT(), loyaltyService.getFidelityDiscount(order.getClient(), order.getSousTotalHT()));

            BigDecimal totalRemise = remiseCodePromo.add(remiseFidelite);
            order.setMontantRemise(totalRemise);
            order.setMontantHTApresRemise(order.getSousTotalHT().subtract(totalRemise));

            BigDecimal tva = order.getMontantHTApresRemise().multiply(BigDecimal.valueOf(0.2));
            order.setTva(tva);
            order.setTotalTTC(order.getMontantHTApresRemise().add(tva));
            order.setMontantRestant(order.getTotalTTC());
        }

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }
}
