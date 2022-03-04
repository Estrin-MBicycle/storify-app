package internship.mbicycle.storify.unit;

import internship.mbicycle.storify.model.Cart;
import internship.mbicycle.storify.model.Profile;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.repository.CartRepository;
import internship.mbicycle.storify.service.StorifyUserService;
import internship.mbicycle.storify.service.impl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    private static final String EMAIL = "Igor";
    private static final String ROLE = "role";
    private static final String PASSWORD = "password";
    private static Authentication auth;

    private static Profile profile = Profile.builder()
            .id(100L)
            .name("Igor")
            .surname("Smith")
            .town("Minsk")
            .address("Oil")
            .phone("202")
            .build();

    private static Cart cart = Cart.builder()
            .id(120L)
            .productsMap(new HashMap<>())
            .build();

    @Mock
    private StorifyUserService storifyUserService;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void testFindByCart() {
        given(storifyUserService.getUserByEmail(EMAIL)).willReturn(getUser());
        Cart actual = cartService.getCartByPrincipal(auth);
        assertEquals(cart, actual);
        then(storifyUserService).should(only()).getUserByEmail(EMAIL);
    }

    @Test
    void testAddProductByCart() {
        given(cartRepository.save(cart)).willReturn(cart);
        cartService.addProduct(cart, 100L, 5);
        Integer expectedCount = 5;
        Integer actualCount = cart.getProductsMap().get(100L);
        assertEquals(expectedCount, actualCount);
        then(cartRepository).should(only()).save(cart);
    }

    @Test
    void testChangeProductByCount() {
        given(cartRepository.save(cart)).willReturn(cart);
        cartService.addProduct(cart, 20L, 3);
        cartService.changeProductByCount(cart, 20L, 5);
        Integer expectedCount = 5;
        Integer actualCount = cart.getProductsMap().get(20L);
        assertEquals(expectedCount, actualCount);
    }

    private static StorifyUser getUser() {
        StorifyUser storifyUser = new StorifyUser();
        storifyUser.setEmail(EMAIL);
        storifyUser.setRole(ROLE);
        auth = new UsernamePasswordAuthenticationToken(storifyUser, PASSWORD, storifyUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        profile.setCart(cart);
        storifyUser.setProfile(profile);
        return storifyUser;
    }
}
