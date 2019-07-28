package assignment.platform.domain;

import java.io.IOException;

public interface Executor {
    Result execute(String workingDir, TestCase tc) throws IOException, InterruptedException;
}
