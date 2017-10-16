package ua.lg.karazhanov.configuration.router.validators;

import ua.lg.karazhanov.configuration.rest.annotations.DELETE;
import ua.lg.karazhanov.configuration.rest.annotations.GET;
import ua.lg.karazhanov.configuration.rest.annotations.POST;
import ua.lg.karazhanov.configuration.rest.annotations.PUT;
import ua.lg.karazhanov.configuration.router.exceptions.WrongRestMethodAnnotationException;

import java.lang.annotation.Annotation;

class RoutePathValidator {

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
