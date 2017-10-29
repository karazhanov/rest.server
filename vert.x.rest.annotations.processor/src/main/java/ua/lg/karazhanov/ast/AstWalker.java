package ua.lg.karazhanov.ast;

import com.sun.source.util.Trees;
import ua.lg.karazhanov.builders.ComponentBuilder;
import ua.lg.karazhanov.generator.ComponentGenerator;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementScanner8;
import java.util.ArrayList;
import java.util.Collection;

import static javax.lang.model.element.ElementKind.METHOD;

/**
 * @author karazhanov on 19.10.17.
 */
public class AstWalker extends ElementScanner8<Void, Void> {

    private final Trees mTrees;
    private final TypeElement mOriginElement;
    private final Collection<ComponentBuilder> components = new ArrayList<>();
    private final Messager messager;

    public AstWalker(ProcessingEnvironment env, TypeElement element) {
        mTrees = Trees.instance(env);
        mOriginElement = element;
        messager = env.getMessager();
    }

    @Override
    public Void visitExecutable(ExecutableElement e, Void aVoid) {
        if (e.getKind() == METHOD) {
            ComponentBuilder componentBuilder = ComponentGenerator.generateComponent(e, mOriginElement, mTrees, messager);
            if (componentBuilder != null) {
                components.add(componentBuilder);
            }
        }
        return super.visitExecutable(e, aVoid);
    }

    public Collection<ComponentBuilder> getComponents() {
        return components;
    }
}
