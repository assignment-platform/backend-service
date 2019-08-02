package assignment.platform.api;

import assignment.platform.domain.Compiler;
import assignment.platform.domain.Executor;
import assignment.platform.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AssignmentApi {
    @Autowired
    Compiler compiler;
    @Autowired
    Executor executor;

    @PostMapping("/assignment")
    public Result submit() throws IOException {
        // 接收
//        file.transferTo(Path.of("D:\\source\\hello.c"));

        try {
            compiler.compile("D:\\source");
            executor.execute("D:\\source", null);
        } catch(Exception e) {
            return Result.fail(e.getMessage());
        }

        return Result.OK;
    }
}
