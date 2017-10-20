package ua.lg.karazhanov;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import ua.lg.karazhanov.annotations.VertXRestController;
import ua.lg.karazhanov.ast.AstWalker;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author karazhanov on 17.10.17.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RestControllerProcessor extends AbstractProcessor {

    private static final String SRC = "src/main/java/";
    private static final String PACKAGE = "ua.lg.karazhanov.generated.routing";
    private final Map<TypeElement, AstWalker> mVisitors = new HashMap<>();

    public RestControllerProcessor() {
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.unmodifiableSet(
                Collections.singleton(
                        VertXRestController.class.getCanonicalName()
                ));
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
        for (final Element element : roundEnv.getElementsAnnotatedWith(VertXRestController.class)) {
            if (element instanceof TypeElement) {
                element.accept(mVisitors.computeIfAbsent((TypeElement) element, o -> new AstWalker(processingEnv, o)), null);
            }
            generateComponents(mVisitors.values());
        }
        return true;
    }

    private void generateComponents(Collection<AstWalker> visitors) {
        visitors.stream()
                .flatMap(astWalker -> astWalker.getComponents().stream())
                .forEach(this::saveFile);
    }

    private void saveFile(ComponentBuilder componentBuilder) {
        TypeSpec typeSpec = componentBuilder.build();
        File sourcePath = new File(SRC);
        JavaFile javaFile = JavaFile
                .builder(PACKAGE, typeSpec)
                .addFileComment("Generated by KARAZHANOV processor, do not modify")
                .build();
        try {
            javaFile.writeTo(sourcePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
