package assignment.platform.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 单个源文件的编译，编译结果产生单个可执行文件
 *
 **/

@Component
public class SimpleCompiler implements Compiler {
    @Value("${assignment.compiler.command}")  // sPEL表达式
    private String command;
    @Value("${assignment.compiler.path}")
    private String path;
    @Value("${assignment.compiler.infile}")
    private String infile;
    @Value("${assignment.compiler.outfile}")
    private String outfile;


    @Override
    public Result compile(String workingDir) throws IOException, InterruptedException {
        // 检查源代码文件是否存在
        Path path = Paths.get(workingDir, this.infile);
        File sourceFile = path.toFile();
        if (!sourceFile.exists()) {
            throw new FileNotFoundException(String.format("源文件 %s 不存在", sourceFile.getPath()));
        }

        // 编译结果的文件名与源文件相同
        command = String.format(command, this.outfile, this.infile);
        ProcessBuilder builder = new ProcessBuilder(command.trim().split(" ")).directory(new File(workingDir));
        Map<String, String> env = builder.environment();
        env.put("Path", this.path);

        // 启动gcc编译进程
        Process process = builder.start();
        process.waitFor();  // 等待编译进程结束

        // 收集错误信息
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorMessage = reader.lines().collect(Collectors.joining("\n"));

        return errorMessage.isEmpty() ? Result.OK : Result.fail(errorMessage);
    }
}
