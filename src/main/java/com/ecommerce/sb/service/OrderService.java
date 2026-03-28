package com.ecommerce.sb.service;

import com.ecommerce.sb.payload.OrderDTO;

public interface OrderService {
    OrderDTO placeOrder(String email, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
