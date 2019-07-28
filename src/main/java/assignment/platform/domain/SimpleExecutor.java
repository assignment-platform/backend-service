package assignment.platform.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 只能执行通过标准输入和标准输出的程序
 */

@Component
public class SimpleExecutor implements Executor {
    @Value("${assignment.compiler.outfile}")
    private String executable;
    @Value("${assignment.executor.timeout}")
    private int timeout;

    @Override
    public Result execute(String workingDir, TestCase tc) throws IOException, InterruptedException {
        // 判断可执行文件是否存在
        Path path = Paths.get(workingDir, executable.concat(".exe"));
        if (!Files.exists(path)) {
            throw new IllegalArgumentException(String.format("无法找到目标文件 %s", path.toString()));
        }

        // 开始执行
        Process process = new ProcessBuilder(workingDir + "\\" + executable).directory(null).start();

        // 准备输入
        if(tc.getInput() != null) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write(tc.getInput());
            writer.newLine();
            writer.close();
        }

        // 收集输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String actual = reader.lines().collect(Collectors.joining("\n"));

        process.waitFor(timeout, TimeUnit.SECONDS);

        return actual.endsWith(tc.getExpected()) ? Result.OK :
                Result.fail(String.format("expected:\n %s\nactual:\n %s", tc.getExpected(), actual));
    }
}
