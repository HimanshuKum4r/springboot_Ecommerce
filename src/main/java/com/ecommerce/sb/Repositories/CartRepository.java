package com.ecommerce.sb.Repositories;

import com.ecommerce.sb.model.Cart;
import com.ecommerce.sb.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long > {

    @Query("SELECT c FROM Cart c WHERE c.user.email =?1")
    Cart findCartByEmail(String email);

    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1 AND c.id = ?2")
    Cart findCartByEmailAndCartId(String email, Long cartId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.products ci JOIN FETCH ci.product p WHERE p.id = ?1")
    List<Cart> findCartByProductId(Long productId);
}
