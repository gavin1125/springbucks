package cn.gavin.springbucks.repository;

import cn.gavin.springbucks.model.Coffee;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})public class CoffeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CoffeeRepository coffeeRepository;


    @Test
    public void should_find_coffee_by_name(){
        Coffee coffee = Coffee.builder().name("espresso").price(Money.ofMinor(CurrencyUnit.of("CNY"), 2000)).build();
        entityManager.persist(coffee);
        entityManager.flush();

        Optional<Coffee> found = coffeeRepository.findByName(coffee.getName());

        assertEquals(coffee.getName(),found.get().getName());
    }

    @Test
    @Sql("classpath:sql/data.sql")
    public void should_find_all_coffees(){
        List<Coffee> coffees = coffeeRepository.findAll();
        assertEquals(5,coffees.size());
    }
}
