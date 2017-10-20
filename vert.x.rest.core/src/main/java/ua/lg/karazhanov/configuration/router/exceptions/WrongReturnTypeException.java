package ua.lg.karazhanov.configuration.router.exceptions;

import java.lang.reflect.Method;

public class WrongReturnTypeException extends Exception {

    private Method controllerMethod;

    public WrongReturnTypeException(Method controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
