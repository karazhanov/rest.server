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

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

/**
 * @author karazhanov on 18.10.17.
 */
public class ComponentGenerator {

    public static ComponentBuilder generateComponent(ExecutableElement method, TypeElement mOriginElement, Trees trees) {
        ComponentBuilder.REQUEST_METHOD methodType = getRequestMethodType(method);
        if (methodType == null) {
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
        component.setMethodName(method.getSimpleName().toString());
        component.setMethodCodeBlock(getMethodCodeBody(method, trees));
        component.setRequestMethodType(methodType);
        return component;
    }


    private static JCTree.JCCompilationUnit toUnit(Element element, Trees trees) {
        TreePath path = trees == null ? null : trees.getPath(element);
        if (path == null) return null;
        return (JCTree.JCCompilationUnit) path.getCompilationUnit();
    }

    private static ComponentBuilder.REQUEST_METHOD getRequestMethodType(ExecutableElement method) {
        GET _get = method.getAnnotation(GET.class);
        if (_get != null) {
            return ComponentBuilder.REQUEST_METHOD.GET;
        }
        POST _post = method.getAnnotation(POST.class);
        if (_post != null) {
            return ComponentBuilder.REQUEST_METHOD.POST;
        }
        PUT _put = method.getAnnotation(PUT.class);
        if (_put != null) {
            return ComponentBuilder.REQUEST_METHOD.PUT;
        }
        DELETE _delete = method.getAnnotation(DELETE.class);
        if (_delete != null) {
            return ComponentBuilder.REQUEST_METHOD.DELETE;
        }
        return null;
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
