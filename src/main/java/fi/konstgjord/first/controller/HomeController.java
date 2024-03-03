package fi.konstgjord.first.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String sayHello() {
        return "Hello world!";
    }

    @GetMapping("/name/{name}")
    public String sayHelloToName(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @PostMapping("/greet")
    public String greet(@RequestParam String greeting) {
        return greeting;
    }

    @PutMapping("/goodbye")
    public String sayGoodbye() {
        return "Goodbye!";
    }

    @DeleteMapping("/clear")
    public String clear() {
        return "Data cleared!";
    }
}
