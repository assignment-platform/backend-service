package assignment.platform;

import assignment.platform.domain.Compiler;
import assignment.platform.domain.Executor;
import assignment.platform.domain.Result;
import assignment.platform.domain.TestCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    CommandLineRunner init(Compiler compiler, Executor executor) {
        return args -> {
            Result result = compiler.compile("002");
            if (result.failed()) {
                System.out.println(result.getMessage());
                return;
            }


            List<TestCase> tcs = Files.lines(Paths.get("002", "tests.csv"))
                    .map(line -> {
                        String[] fields = line.split(",");
                        return new TestCase(fields[0], fields[1], Integer.valueOf(fields[2]));
                    }).collect(Collectors.toList());

            for (TestCase tc : tcs) {
                result = executor.execute("002", tc);
                if (result.failed()) {
                    System.out.println(result.getMessage());
                }
            }
        };
    }
}
