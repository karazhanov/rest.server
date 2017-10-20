package ua.lg.karazhanov;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeSpec;

/**
 * @author karazhanov on 19.10.17.
 */
public class ComponentBuilder {

    //    private final TypeElement mOriginElement;
    private String baseClassName;
    private String basePath;
    private String pathUrl;
    private REQUEST_METHOD requestMethodType;
    private String methodName;
    private CodeBlock.Builder methodCodeBlock;

    public void setBaseClassName(String baseClassName) {
        this.baseClassName = baseClassName;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public void setRequestMethodType(REQUEST_METHOD requestMethodType) {
        this.requestMethodType = requestMethodType;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setMethodCodeBlock(CodeBlock.Builder methodCodeBlock) {
        this.methodCodeBlock = methodCodeBlock;
    }

    public TypeSpec build() {
//        TypeSpec.Builder clazz = TypeSpec.classBuilder(mOriginElement.getSimpleName().toString()
//                + "$Generated"
//                + methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
//        clazz.addModifiers(Modifier.PUBLIC)
//                .superclass(VertxController.class)
//                .addAnnotation(Component.class);
//
//        FieldSpec fieldPath = FieldSpec.builder(String.class, "pathUrl")
//                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
//                .initializer("$S", pathUrl)
//                .build();
//
//        MethodSpec handle = MethodSpec.methodBuilder("handle")
//                .addAnnotation(Override.class)
//                .addModifiers(Modifier.PUBLIC)
//                .addParameter(RoutingContext.class, "routingContext")
//                .addCode(astCodeBlock.build())
//                .build();
//
//        TypeSpec buildedClass = clazz
//                .addField(fieldPath)
//                .addMethod(handle)
//                .build();
//
//        mLogger.printMessage(Diagnostic.Kind.NOTE, buildedClass.toString());
//        return buildedClass;
//
        return null;
    }

    enum REQUEST_METHOD {
        GET, POST, PUT, DELETE
    }
}
