package ilya.project.telegrambot.bot.service;

import ilya.project.telegrambot.bot.model.Product;
import ilya.project.telegrambot.utils.FileUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StoreService {
    public final static Map<Long, Map<Product, Integer>> cartMap = new ConcurrentHashMap<>();
    public final static Map<Long, Product> productsMap = new ConcurrentHashMap<>() {{
        put(1L, new Product("Кепка", new BigDecimal(500), FileUtils.getInputFile("products/cap.png")));
        put(2L, new Product("Фляжка", new BigDecimal(1600), FileUtils.getInputFile("products/flask.png")));
        put(3L, new Product("Стельки", new BigDecimal(800), FileUtils.getInputFile("products/insoles.png")));
        put(4L, new Product("Лапти", new BigDecimal(900), FileUtils.getInputFile("products/lapti.png")));
        put(5L, new Product("Пестр", new BigDecimal(1200), FileUtils.getInputFile("products/pester.png")));
        put(6L, new Product("Туесок", new BigDecimal(600), FileUtils.getInputFile("products/tuesok.png")));
    }};

    public void addToCart(Long chatId, Long productId) {
        Product product = productsMap.get(productId);
        var cart = Optional.of(cartMap.get(chatId))
                .orElseGet(HashMap::new);
        var count = Optional.of(cart.get(product))
                .orElse(0);
        cart.put(product, count + 1);
        cartMap.put(chatId, cart);
    }

    public void buy(Long chatId) {
        var store = cartMap.put(chatId, new HashMap<>());
    }

    public List<Product> getStoreCard() {
        return productsMap.values()
                .stream().toList();
    }

    public Map<Product, Integer> getCard(Long chatId) {
        return cartMap.get(chatId);
    }
}
