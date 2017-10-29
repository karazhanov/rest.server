package ua.lg.karazhanov.generator;

import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.List;

public class TypeValidator {

    //    private static final String BOXED_VOID = "java.lang.Void";
    private static final String BOXED_BOOLEAN = "java.lang.Boolean";
    private static final String BOXED_BYTE = "java.lang.Byte";
    private static final String BOXED_SHORT = "java.lang.Short";
    private static final String BOXED_INT = "java.lang.Integer";
    private static final String BOXED_LONG = "java.lang.Long";
    private static final String BOXED_CHAR = "java.lang.Character";
    private static final String BOXED_FLOAT = "java.lang.Float";
    private static final String BOXED_DOUBLE = "java.lang.Double";
    private static final String STRING = "java.lang.String";

    private static final List<String> BOXED_TYPES = Arrays.asList(
            BOXED_BOOLEAN,
            BOXED_BYTE,
            BOXED_SHORT,
            BOXED_INT,
            BOXED_LONG,
            BOXED_CHAR,
            BOXED_FLOAT,
            BOXED_DOUBLE,
            STRING
    );

    static boolean isBaseOrBoxedType(VariableElement param) {
        return isPrimitive(param) || isBoxedPrimitive(param);
    }

    private static boolean isPrimitive(VariableElement param) {
        return param.asType().getKind().isPrimitive();
    }

    private static boolean isBoxedPrimitive(VariableElement param) {
        return BOXED_TYPES.contains(param.asType().toString());
    }
}
