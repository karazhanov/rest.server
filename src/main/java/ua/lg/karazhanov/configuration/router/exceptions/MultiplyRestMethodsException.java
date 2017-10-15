package ua.lg.karazhanov.configuration.router.exceptions;

import java.lang.reflect.Method;

public class MultiplyRestMethodsException extends Exception {

    private Method controllerMethod;

    public MultiplyRestMethodsException(Method controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
