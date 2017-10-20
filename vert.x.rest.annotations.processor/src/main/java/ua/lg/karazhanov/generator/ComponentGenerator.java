package ua.lg.karazhanov.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.sun.source.util.Trees;
import com.sun.tools.javac.tree.JCTree;
import ua.lg.karazhanov.ComponentBuilder;
import ua.lg.karazhanov.annotations.VertXRestController;
import ua.lg.karazhanov.annotations.params.Path;
import ua.lg.karazhanov.annotations.params.Query;
import ua.lg.karazhanov.ast.AstBodyCode;
import ua.lg.karazhanov.ast.AstWalker;

import javax.annotation.processing.Filer;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

/**
 * @author karazhanov on 18.10.17.
 */
public class ComponentGenerator {

    public static ComponentBuilder generateComponent(ExecutableElement method, TypeElement mOriginElement, Trees mTrees) {
//        methodDecl,
//                path +
//                        "PATH/TODO/CHANGE",
//                methodDecl.getName(),
//                astCodeBlock
        VertXRestController vertXRestController = mOriginElement.getAnnotation(VertXRestController.class);
        ComponentBuilder component = new ComponentBuilder();
        component.setBasePath(vertXRestController.value());
        component.setBaseClassName(mOriginElement.getSimpleName().toString());
        component.setMethodName(method.getSimpleName().toString());
        component.setMethodCodeBlock(getMethodCodeBody(method, mTrees));
//        component.
        return component;
    }

    private static CodeBlock.Builder getMethodCodeBody(ExecutableElement method, Trees mTrees) {
        CodeBlock.Builder astCodeBlock = CodeBlock.builder();
        generateParameters(astCodeBlock, method);
        AstBodyCode astBodyCode = new AstBodyCode(astCodeBlock);
        ((JCTree) mTrees.getTree(method)).accept(astBodyCode);
        return astBodyCode.getCodeBlock();
    }

    // TODO validate params type
    private static void generateParameters(CodeBlock.Builder astBodyCode, ExecutableElement method) {
        List<? extends VariableElement> parameters = method.getParameters();
        for (VariableElement param : parameters) {
            try {
                generatePathParam(param, astBodyCode);
                generateQueryParam(param, astBodyCode);
                generateBodyParam(param, astBodyCode);
            } catch (RuntimeException ignore) {
            }
        }
    }

    private static void generatePathParam(VariableElement param, CodeBlock.Builder astBodyCode) {
        Path path = param.getAnnotation(Path.class);
        if (path != null) {
            generateParam(param, astBodyCode);
        }
    }

    private static void generateQueryParam(VariableElement param, CodeBlock.Builder astBodyCode) {
        Query query = param.getAnnotation(Query.class);
        if (query != null) {
            generateParam(param, astBodyCode);
        }
    }

    private static void generateParam(VariableElement param, CodeBlock.Builder astBodyCode) {
        ComponentValidator.validateType(param);
        ComponentValidator.validatePathContainParam(param);
        astBodyCode.addStatement(
                "$T $L = ($T) routingContext.request().getParam(\"$L\")",
                ClassName.get(param.asType()),
                param.getSimpleName(),
                ClassName.get(param.asType()),
                param.getSimpleName());
    }

    private static void generateBodyParam(VariableElement param, CodeBlock.Builder astBodyCode) {
//        Body body = o.getAnnotation(Body.class);
    }



}
