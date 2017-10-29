package ua.lg.karazhanov.generator;

import javax.lang.model.element.VariableElement;

/**
 * @author karazhanov on 18.10.17.
 */
class ComponentValidator {

    static boolean isValidPathContainParam(VariableElement param, String fullPath) {
        return fullPath.contains(":"+param.getSimpleName());
    }

    public static boolean isValidPathQueryType(VariableElement param) {
        return TypeValidator.isBaseOrBoxedType(param);
    }

    public static boolean isValidBodyType(VariableElement param) {
        return !TypeValidator.isBaseOrBoxedType(param);
    }
}
