package assignment.platform.domain;

public class TestCase {
    private String input;
    private String expected;
    private int point;

    public TestCase(String expected) {
        this(null, expected, 100);
    }

    public TestCase(String expected, int point) {
        this(null, expected, point);
    }

    public TestCase(String input, String expected, int point) {
        this.input = input;
        this.expected = expected;
        this.point = point;
    }

    public String getExpected() {
        return expected;
    }

    public String getInput() {
        return input;
    }
}
