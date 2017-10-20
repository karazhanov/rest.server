package ua.lg.karazhanov.configuration.router.validators;

import ua.lg.karazhanov.annotations.methods.DELETE;
import ua.lg.karazhanov.annotations.methods.GET;
import ua.lg.karazhanov.annotations.methods.POST;
import ua.lg.karazhanov.annotations.methods.PUT;
import ua.lg.karazhanov.configuration.rest.RestController;
import ua.lg.karazhanov.configuration.router.exceptions.WrongRestMethodAnnotationException;
import ua.lg.karazhanov.configuration.router.exceptions.WrongRestPathException;

import java.lang.annotation.Annotation;

import static ua.lg.karazhanov.configuration.router.exceptions.WrongRestPathException.ERROR_TYPE.CONTAIN_PARAMS;
import static ua.lg.karazhanov.configuration.router.exceptions.WrongRestPathException.ERROR_TYPE.EMPTY_PATH;

public class RoutePathValidator {

    public static void validatePath(String basePath, Class<? extends RestController> aClass) throws WrongRestPathException {
        if(basePath == null || basePath.trim().isEmpty()) {
            throw new WrongRestPathException(aClass, EMPTY_PATH);
        }
        if(basePath.contains("?")) {
            throw new WrongRestPathException(aClass, CONTAIN_PARAMS);
        }

    }

    static void validateAnnotatedPath(Annotation annotation) throws WrongRestMethodAnnotationException {
        String path = getPath(annotation);

    }

    private static String getPath(Annotation annotation) throws WrongRestMethodAnnotationException {
        if (annotation instanceof GET) {
            return ((GET) annotation).value();
        } else
        if (annotation instanceof POST) {
            return ((POST) annotation).value();
        } else
        if (annotation instanceof PUT) {
            return ((PUT) annotation).value();
        } else
        if (annotation instanceof DELETE) {
            return ((DELETE) annotation).value();
        }
        throw new WrongRestMethodAnnotationException(annotation);
    }

}
