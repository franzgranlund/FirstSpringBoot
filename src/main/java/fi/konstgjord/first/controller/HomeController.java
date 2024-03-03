package fi.konstgjord.first.controller;

import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/home")
public class HomeController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public String sayHello() {
        return "Hello world!";
    }

    @GetMapping("/name/{name}")
    public String sayHelloToName(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    public record Greeting(Long id, String name) {}

    @GetMapping("/greet")
    @ResponseBody
    public Greeting greet(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
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
