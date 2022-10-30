package cn.gavin.springbucks.web;

import cn.gavin.springbucks.model.Coffee;
import cn.gavin.springbucks.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoffeeController {

    private final CoffeeService coffeeService;

    @GetMapping("/coffees")
    public List<Coffee> getAllCoffees() {
        return coffeeService.getAllCoffees();
    }


}
