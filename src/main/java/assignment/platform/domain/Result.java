package assignment.platform.domain;

public class Result {
    private int code;
    private String message;

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result fail(String message) {
        return new Result(1, message);
    }

    public static final Result OK = new Result(0, "OK");

    public boolean succeed() {
        return this.code == 0;
    }

    public boolean failed() {
        return this.code == 1;
    }

    @Override
    public String toString() {
        return String.format("{code: %d, message: %s}", code, message);
    }

    public String getMessage() {
        return this.message;
    }
}
