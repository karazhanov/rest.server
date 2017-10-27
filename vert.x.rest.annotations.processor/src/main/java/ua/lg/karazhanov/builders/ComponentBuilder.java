package ua.lg.karazhanov.builders;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;
import ua.lg.karazhanov.VertxController;

import javax.lang.model.element.Modifier;

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
    private List<JCTree.JCImport> imports;

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

    public void setImport(List<JCTree.JCImport> imports) {
        this.imports = imports;
    }

    public List<JCTree.JCImport> getImports() {
        return imports;
    }

    public TypeSpec build() {
        TypeSpec.Builder clazz = TypeSpec.classBuilder(baseClassName
                + "$" + requestMethodType + "_"
                + methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
        clazz.addModifiers(Modifier.PUBLIC)
                .superclass(VertxController.class)
                .addAnnotation(Component.class);

        FieldSpec fieldPath = FieldSpec.builder(String.class, "pathUrl")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("$S", basePath + pathUrl)
                .build();

        MethodSpec handle = MethodSpec.methodBuilder("handle")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(RoutingContext.class, "routingContext")
                .addCode(methodCodeBlock.build())
                .build();

        return clazz
                .addField(fieldPath)
                .addMethod(handle)
                .build();
    }

    public enum REQUEST_METHOD {
        GET, POST, PUT, DELETE
    }
}
