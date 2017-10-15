package ua.lg.karazhanov.configuration.router.routes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import rx.Observable;
import ua.lg.karazhanov.configuration.annotations.DELETE;
import ua.lg.karazhanov.configuration.annotations.GET;
import ua.lg.karazhanov.configuration.annotations.POST;
import ua.lg.karazhanov.configuration.annotations.PUT;
import ua.lg.karazhanov.configuration.router.exceptions.MultiplyRestMethodsException;
import ua.lg.karazhanov.configuration.router.exceptions.WrongReturnTypeException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class RouteValidator {

    public static Annotation isAnnotated(Method method) throws MultiplyRestMethodsException {
        GET _get = AnnotationUtils.getAnnotation(method, GET.class);
        POST _post = AnnotationUtils.getAnnotation(method, POST.class);
        PUT _put = AnnotationUtils.getAnnotation(method, PUT.class);
        DELETE _delete = AnnotationUtils.getAnnotation(method, DELETE.class);
        List<Annotation> annotations = Stream.of(_get, _post, _put, _delete).filter(Objects::nonNull).collect(toList());
        if (annotations.size() > 1) {
            throw new MultiplyRestMethodsException(method);
        }
        if (annotations.size() == 1) {
            return annotations.get(0);
        }
        return null;
    }

    public static void validateSignature(Method method) throws WrongReturnTypeException {
        Class<?> returnType = method.getReturnType();
        if (!returnType.equals(Observable.class)) {
            throw new WrongReturnTypeException(method);
        }
    }
}
