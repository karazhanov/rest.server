package ua.lg.karazhanov.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import com.sun.tools.javac.tree.JCTree;
import ua.lg.karazhanov.builders.ComponentBuilder;
import ua.lg.karazhanov.annotations.VertXRestController;
import ua.lg.karazhanov.annotations.methods.DELETE;
import ua.lg.karazhanov.annotations.methods.GET;
import ua.lg.karazhanov.annotations.methods.POST;
import ua.lg.karazhanov.annotations.methods.PUT;
import ua.lg.karazhanov.annotations.params.Path;
import ua.lg.karazhanov.annotations.params.Query;
import ua.lg.karazhanov.ast.AstBodyCode;
import ua.lg.karazhanov.types.MethodInfo;
import ua.lg.karazhanov.types.REQUEST_METHOD;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.util.List;

/**
 * @author karazhanov on 18.10.17.
 */
public class ComponentGenerator {

    public static ComponentBuilder generateComponent(ExecutableElement method, TypeElement mOriginElement, Trees trees, Messager messager) {
        MethodInfo methodInfo = getRequestMethodType(method);
        if (methodInfo == null) {
            return null;
        }
        VertXRestController vertXRestController = mOriginElement.getAnnotation(VertXRestController.class);
        ComponentBuilder component = new ComponentBuilder();
        JCTree.JCCompilationUnit jcCompilationUnit = toUnit(mOriginElement, trees);
        if(jcCompilationUnit != null) {
            component.setImport(jcCompilationUnit.getImports());
        }
        component.setBasePath(vertXRestController.value());
        component.setBaseClassName(mOriginElement.getSimpleName().toString());
        component.setMethodInfo(methodInfo);
        String fullPath = component.getFullPath();
        component.setMethodName(method.getSimpleName().toString());
        component.setMethodCodeBlock(getMethodCodeBody(method, trees, fullPath, messager));
        return component;
    }

    private static JCTree.JCCompilationUnit toUnit(Element element, Trees trees) {
        TreePath path = trees == null ? null : trees.getPath(element);
        if (path == null) return null;
        return (JCTree.JCCompilationUnit) path.getCompilationUnit();
    }

    private static MethodInfo getRequestMethodType(ExecutableElement method) {
        GET _get = method.getAnnotation(GET.class);
        if (_get != null) {
            return new MethodInfo(REQUEST_METHOD.GET, _get.value());
        }
        POST _post = method.getAnnotation(POST.class);
        if (_post != null) {
            return new MethodInfo(REQUEST_METHOD.POST, _post.value());
        }
        PUT _put = method.getAnnotation(PUT.class);
        if (_put != null) {
            return new MethodInfo(REQUEST_METHOD.PUT, _put.value());
        }
        DELETE _delete = method.getAnnotation(DELETE.class);
        if (_delete != null) {
            return new MethodInfo(REQUEST_METHOD.DELETE, _delete.value());
        }
        return null;
    }

    private static CodeBlock.Builder getMethodCodeBody(ExecutableElement method, Trees mTrees, String fullPath, Messager messager) {
        CodeBlock.Builder astCodeBlock = CodeBlock.builder();
        generateParameters(astCodeBlock, method, fullPath, messager);
        AstBodyCode astBodyCode = new AstBodyCode(astCodeBlock);
        ((JCTree) mTrees.getTree(method)).accept(astBodyCode);
        return astBodyCode.getCodeBlock();
    }

    // TODO validate params type
    private static void generateParameters(CodeBlock.Builder astBodyCode, ExecutableElement method, String fullPath, Messager messager) {
        List<? extends VariableElement> parameters = method.getParameters();
        for (VariableElement param : parameters) {
            try {
                generatePathParam(param, astBodyCode, fullPath, messager);
                generateQueryParam(param, astBodyCode, fullPath, messager);
                generateBodyParam(param, astBodyCode, messager);
            } catch (RuntimeException ignore) {
            }
        }
    }

    private static void generatePathParam(VariableElement param, CodeBlock.Builder astBodyCode, String fullPath, Messager messager) {
        Path path = param.getAnnotation(Path.class);
        if (path != null) {
            generateParam(param, astBodyCode, fullPath, messager);
        }
    }

    private static void generateQueryParam(VariableElement param, CodeBlock.Builder astBodyCode, String fullPath, Messager messager) {
        Query query = param.getAnnotation(Query.class);
        if (query != null) {
            generateParam(param, astBodyCode, fullPath, messager);
        }
    }

    private static void generateParam(VariableElement param, CodeBlock.Builder astBodyCode, String fullPath, Messager messager) {
        if(!ComponentValidator.isValidPathQueryType(param)) {
            // TODO make error
            messager.printMessage(Diagnostic.Kind.NOTE, param.asType() + " is invalid type. Can be only primitive or boxed type");
            return;
        }
        if(!ComponentValidator.isValidPathContainParam(param, fullPath)) {
            // TODO make error
            messager.printMessage(Diagnostic.Kind.NOTE, param.toString() + " is invalid");
            return;
        }
        astBodyCode.addStatement(
                "$T $L = ($T) routingContext.request().getParam(\"$L\")",
                ClassName.get(param.asType()),
                param.getSimpleName(),
                ClassName.get(param.asType()),
                param.getSimpleName());
    }

    private static void generateBodyParam(VariableElement param, CodeBlock.Builder astBodyCode, Messager messager) {

//        Body body = o.getAnnotation(Body.class);
    }


}
