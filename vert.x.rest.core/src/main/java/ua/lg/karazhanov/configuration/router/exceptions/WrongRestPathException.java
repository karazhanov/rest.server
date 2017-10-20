package ua.lg.karazhanov.configuration.router.exceptions;

import ua.lg.karazhanov.configuration.rest.RestController;

public class WrongRestPathException extends Exception {

    private final ERROR_TYPE errorType;
    private final Class<? extends RestController> controller;

    public WrongRestPathException(Class<? extends RestController> controller, ERROR_TYPE errorType) {
        this.controller = controller;
        this.errorType = errorType;
    }

    public ERROR_TYPE getErrorType() {
        return errorType;
    }

    public Class<? extends RestController> getController() {
        return controller;
    }

    @Override
    public String toString() {
        return "WrongRestPathException{" +
                "errorType=" + errorType +
                ", controller=" + controller +
                '}';
    }

    public enum ERROR_TYPE {
        EMPTY_PATH,
        CONTAIN_PARAMS
    }
}
