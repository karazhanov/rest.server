package ua.lg.karazhanov.builders;

import com.squareup.javapoet.*;
import io.vertx.ext.web.Router;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author karazhanov on 27.10.17.
 */
public class MainRouteBuilder {

    private static final String SRC = "src/main/java/";
    private static final String BASE_PACKAGE = "ua.lg.karazhanov.generated";
    private static final String COMPONENTS_PACKAGE = BASE_PACKAGE + ".routes";

    private static final String ROUTER = "router";

    private final Messager messager;
    private List<ComponentBuilder> components;

    public MainRouteBuilder(ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
        components = new ArrayList<>();
    }

    public void addRoute(ComponentBuilder componentBuilder) {
        components.add(componentBuilder);
    }

    public void build() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Router.class, ROUTER);
        TypeSpec.Builder clazz = TypeSpec.classBuilder("VertXRouterInitiator")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Component.class);
        MethodSpec init = builder
                .addCode(buildComponents())
                .build();
        TypeSpec buildedClass = clazz
                .addMethod(init)
                .build();
        saveFile(buildedClass, BASE_PACKAGE);
    }

    private CodeBlock buildComponents() {
        CodeBlock.Builder codeBlock = CodeBlock.builder();
        int[] count = new int[1];
        components.forEach(componentBuilder -> {
            try {
                TypeSpec build = componentBuilder.build();
                saveFile(build, COMPONENTS_PACKAGE);

                List<TypeSpec> typeSpecs = build.typeSpecs;
                typeSpecs.forEach(typeSpec -> {
                    messager.printMessage(Diagnostic.Kind.NOTE, typeSpec.toString());
                });

//                ClassName className = ClassName.get();
//              router.get(r.getPath()).handler(r.getHandler()::apply)
//                codeBlock.addStatement("$T $N", className, "route"+count[0]);
                codeBlock.addStatement("$L.get()", ROUTER);
                count[0]++;
            } catch (Exception e) {
                messager.printMessage(Diagnostic.Kind.NOTE, e.getMessage());
            }
        });
        return codeBlock.build();
    }

    private void saveFile(TypeSpec typeSpec, String packageName) {
        File sourcePath = new File(SRC);
        JavaFile javaFile = JavaFile
                .builder(packageName, typeSpec)
                .addFileComment("Generated by KARAZHANOV processor, do not modify")
                .build();
        try {
            javaFile.writeTo(sourcePath);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
            e.printStackTrace();
        }
    }


//    public String injectImports(JavaFile javaFile, com.sun.tools.javac.util.List<JCTree.JCImport> imports) {
//        String rawSource = javaFile.toString();
//        List<String> result = new ArrayList<>();
//        for (String s : rawSource.split("\n", -1)) {
//            result.add(s);
//            if (s.startsWith("package ")) {
//                result.add("");
//                for (JCTree.JCImport i : imports) {
//                    result.add("import " + i.toString());
//                }
//            }
//        }
//        return String.join("\n", result);
//    }
}
