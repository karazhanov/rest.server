package ua.lg.karazhanov.configuration.router.validators;

import ua.lg.karazhanov.annotations.methods.DELETE;
import ua.lg.karazhanov.annotations.methods.GET;
import ua.lg.karazhanov.annotations.methods.POST;
import ua.lg.karazhanov.annotations.methods.PUT;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import rx.Observable;
import ua.lg.karazhanov.configuration.router.exceptions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class RouteValidator {

    public static Annotation isAnnotated(Method method) throws MultiplyRestMethodsException, WrongRestMethodAnnotationException {
        GET _get = AnnotationUtils.getAnnotation(method, GET.class);
        POST _post = AnnotationUtils.getAnnotation(method, POST.class);
        PUT _put = AnnotationUtils.getAnnotation(method, PUT.class);
        DELETE _delete = AnnotationUtils.getAnnotation(method, DELETE.class);
        List<Annotation> annotations = Stream.of(_get, _post, _put, _delete).filter(Objects::nonNull).collect(toList());
        if (annotations.size() > 1) {
            throw new MultiplyRestMethodsException(method);
        }
        if (annotations.size() == 1) {
            RoutePathValidator.validateAnnotatedPath(annotations.get(0));
            return annotations.get(0);
        }
        return null;
    }

    public static void validateSignature(Method method) throws WrongReturnTypeException, WrongArgumentCountException, WrongArgumentTypeException {
        Class<?> returnType = method.getReturnType();
        if (!returnType.equals(Observable.class)) {
            throw new WrongReturnTypeException(method);
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if(parameterTypes.length != 1) {
            throw new WrongArgumentCountException(method);
        }
        if(!parameterTypes[0].equals(RoutingContext.class)) {
            throw new WrongArgumentTypeException(method);
        }
    }
}
