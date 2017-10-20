package ua.lg.karazhanov.configuration.router.exceptions;

import java.lang.reflect.Method;

public class WrongArgumentTypeException extends Exception {

    private Method controllerMethod;

    public WrongArgumentTypeException(Method controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
