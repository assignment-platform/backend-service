package assignment.platform.domain;

import java.io.IOException;

public interface Compiler {
    Result compile(String workingDir) throws IOException, InterruptedException;
}
