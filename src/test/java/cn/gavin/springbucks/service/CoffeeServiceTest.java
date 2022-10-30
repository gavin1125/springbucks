package cn.gavin.springbucks.service;

import cn.gavin.springbucks.model.Coffee;
import cn.gavin.springbucks.repository.CoffeeRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CoffeeServiceTest {

    @Mock
    private CoffeeRepository coffeeRepository;

    @InjectMocks
    private CoffeeService coffeeService;

    @BeforeEach
    public void before(){
        Coffee coffee = Coffee.builder().name("espresso").price(Money.ofMinor(CurrencyUnit.of("CNY"), 2000)).build();
        Mockito.when(coffeeRepository.findAll()).thenReturn(Arrays.asList(coffee));
    }

    @Test
    public void should_get_all_coffees(){
        List<Coffee> coffees = coffeeService.getAllCoffees();
        assertEquals(1,coffees.size());
    }
}
