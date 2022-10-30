package cn.gavin.springbucks.web;

import cn.gavin.springbucks.SpringBucksApplication;
import cn.gavin.springbucks.model.Coffee;
import cn.gavin.springbucks.repository.CoffeeRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpringBucksApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class CoffeeControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @BeforeEach
    public void before(){
        Coffee coffee = Coffee.builder().name("espresso").price(Money.ofMinor(CurrencyUnit.of("CNY"), 2000)).build();
        coffeeRepository.save(coffee);
    }

    @Test
    public void should_get_all_coffees() throws Exception {
        mvc.perform(get("/coffees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("espresso"));
    }
}
