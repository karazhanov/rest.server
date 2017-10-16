package ua.lg.karazhanov.configuration.router.exceptions;

import java.lang.annotation.Annotation;

public class WrongRestMethodAnnotationException extends Exception {

    private Annotation annotation;

    public WrongRestMethodAnnotationException(Annotation annotation) {
        this.annotation = annotation;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}
