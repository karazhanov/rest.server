package ua.lg.karazhanov.types;

public class MethodInfo {
    private final REQUEST_METHOD methodType;
    private final String methodPath;

    public MethodInfo(REQUEST_METHOD methodType, String methodPath) {
        this.methodType = methodType;
        this.methodPath = methodPath;
    }

    public REQUEST_METHOD getMethodType() {
        return methodType;
    }

    public String getMethodPath() {
        return methodPath;
    }
}
