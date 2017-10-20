package ua.lg.karazhanov.configuration.router.exceptions;

import java.lang.reflect.Method;

public class WrongArgumentCountException extends Exception {

    private Method controllerMethod;

    public WrongArgumentCountException(Method controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
