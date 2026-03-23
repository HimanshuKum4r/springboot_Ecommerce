package com.ecommerce.sb.service;

import com.ecommerce.sb.Repositories.CartItemRepository;
import com.ecommerce.sb.Repositories.CartRepository;
import com.ecommerce.sb.Repositories.ProductRepository;
import com.ecommerce.sb.exceptions.APIException;
import com.ecommerce.sb.exceptions.ResourceNotFoundException;
import com.ecommerce.sb.model.Cart;
import com.ecommerce.sb.model.CartItem;
import com.ecommerce.sb.model.Product;
import com.ecommerce.sb.payload.CartDTO;
import com.ecommerce.sb.payload.ProductDTO;
import com.ecommerce.sb.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public CartDTO addProduct(Long productId, Integer quantity) {
        Cart cart = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("product not found with productId" + productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(),productId);

        if (cartItem!=null){
            throw new APIException(product.getProductName(), HttpStatus.BAD_REQUEST,"already exists");
        }
        if (product.getQuantity()==0){
            throw new APIException(product.getProductName(),HttpStatus.BAD_REQUEST,"not available");

        }
        if (product.getQuantity()< quantity){
            throw new APIException("please make an order of space or less than it",HttpStatus.BAD_REQUEST,"="+product.getQuantity());

        }
        CartItem newCartItem  = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setCart(cart);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductprice(product.getSpecialPrice());

        cartItemRepository.save(newCartItem);

        cart.setTotalPrice(cart.getTotalPrice()+ (product.getSpecialPrice()*quantity));
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        List<CartItem> cartItems = cart.getProducts();

        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item-> {
            ProductDTO map = modelMapper.map(item.getProduct(),ProductDTO.class);
            map.setQuantity(item.getQuantity() );
            return map;
        });
        cartDTO.setProducts(productDTOStream.toList());

        return cartDTO;


    }

    @Override
    public List<CartDTO> getALlCarts() {
        List<Cart> carts = cartRepository.findAll();
        if(carts.size()==0){
            throw new APIException("no cart exist" ,HttpStatus.NOT_FOUND);
        }

        List<CartDTO> cartDTOS = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
            List<ProductDTO> products = cart.getProducts().stream().map(p-> modelMapper.map(p.getProduct(),ProductDTO.class)).toList();
            cartDTO.setProducts(products);
            return cartDTO;
        }).toList();



        return cartDTOS;
    }

    @Override
    public CartDTO getCart(String email, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(email,cartId);
        if (cart==null){
            throw new ResourceNotFoundException("cart is empty " ,cartId);
        }
        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        List<ProductDTO> productDTOList  = cart.getProducts().stream().map(p-> modelMapper.map(p.getProduct(),ProductDTO.class)).toList();
        cartDTO.setProducts(productDTOList);
        return cartDTO;
    }

    @Override
    public CartDTO updateProductQuantityInCart(Long productId, Integer  quantity) {

        String email = authUtil.loggedInEmail();
        Cart userCart = cartRepository.findCartByEmail(email);
        Long cartId = userCart.getCartId();


        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new ResourceNotFoundException("cart not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("product not found with productid " ,productId));

        if (product.getQuantity()==0){
            throw new APIException(product.getProductName() + "is not available");
        }
        if (product.getQuantity() < quantity){
            throw new APIException("please make an order of the space " + product.getQuantity() + "less than or equal to it ");
        }
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId,cartId);
        if (cartItem==null){
            throw new APIException("product is not in the cart");
        }

        cartItem.setProductprice(product.getSpecialPrice());
        cartItem.setQuantity(cartItem.getQuantity()+quantity);
        cartItem.setDiscount(product.getDiscount());
        cart.setTotalPrice(cart.getTotalPrice()+(cartItem.getProductprice()*quantity));
        cartRepository.save(cart);
        CartItem  updateditem = cartItemRepository.save(cartItem);

        if (updateditem.getQuantity()==0){
            cartItemRepository.deleteById(updateditem.getCartItemId());
        }

        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        List<ProductDTO> productDTOList  = cart.getProducts().stream().map(p-> {
             ProductDTO prd = modelMapper.map(p.getProduct(),ProductDTO.class);
             prd.setQuantity(p.getQuantity());
             return prd;
        }).toList();

        cartDTO.setProducts(productDTOList);

        return cartDTO;



    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new ResourceNotFoundException("cart with cartId "+cartId+"does not exist"));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId,productId);

        if (cartItem==null){
            throw new ResourceNotFoundException("product not found with product id" , productId);
        }
        cart.setTotalPrice(cart.getTotalPrice()-(cartItem.getProductprice()*cartItem.getQuantity()));

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId,productId);

        return "product " + cartItem.getProduct().getProductName() + " removed from cart";



    }


    //it will find if there is an existing cart with logged  user
    private Cart createCart(){
        Cart usercart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if (usercart!=null){
            return usercart;
        }

        Cart cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        return  cartRepository.save(cart);

    }
}
