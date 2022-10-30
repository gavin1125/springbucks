package cn.gavin.springbucks.web;


import cn.gavin.springbucks.model.Coffee;
import cn.gavin.springbucks.service.CoffeeService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CoffeeController.class)
class CoffeeControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CoffeeService coffeeService;

    @Test
    public void should_get_all_coffees() throws Exception {
        Coffee coffee = Coffee.builder().name("espresso").price(Money.ofMinor(CurrencyUnit.of("CNY"), 2000)).build();
        when(coffeeService.getAllCoffees()).thenReturn(Arrays.asList(coffee));

        mvc.perform(get("/coffees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("espresso"));
    }
}
