package com.ecommerce.sb.service;

import com.ecommerce.sb.Repositories.*;
import com.ecommerce.sb.exceptions.APIException;
import com.ecommerce.sb.exceptions.ResourceNotFoundException;
import com.ecommerce.sb.model.*;
import com.ecommerce.sb.payload.OrderDTO;
import com.ecommerce.sb.payload.OrderItemDTO;
import com.ecommerce.sb.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  CartService cartService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(String email, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage) {
        Cart cart = cartRepository.findCartByEmail(email);
        if (cart == null){
            throw new ResourceNotFoundException("cart not found with emailid" + email);
        }
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("address with addressId " + addressId + " not found"));

        Order order = new Order();
        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted");
        order.setAddress(address);

        Payment payment  = new Payment(paymentMethod,pgPaymentId,pgResponseMessage,pgName,pgStatus);
        payment.setOrder(order);

        payment = paymentRepository.save(payment);

        order.setPayment(payment);

        Order savedorder = orderRepository.save(order);

        List<CartItem> cartItems = cart.getProducts();
        if (cartItems == null){
            throw new APIException("cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem: cartItems){
            OrderItem  orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrice(cartItem.getProductprice());
            orderItem.setOrder(savedorder);
            orderItems.add(orderItem);
        }
        orderItems = orderItemRepository.saveAll(orderItems);

        cart.getProducts().forEach( item -> {
            int quantity = item.getQuantity();
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity()-quantity);
            productRepository.save(product);

            cartService.deleteProductFromCart(cart.getCartId(),item.getProduct().getProductId());

        });
        OrderDTO orderDTO = modelMapper.map(savedorder,OrderDTO.class);
        orderItems.forEach(orderItem -> {
            orderDTO.getOrderItems().add(modelMapper.map(orderItem, OrderItemDTO.class));
        });
        orderDTO.setAddressId(addressId);

        return orderDTO;
    }
}
