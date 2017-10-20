package ua.lg.karazhanov.ast;

import com.squareup.javapoet.CodeBlock;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;

/**
 * @author karazhanov on 19.10.17.
 */
public class AstBodyCode extends TreeTranslator {

    private CodeBlock.Builder astCodeBlock;

    public AstBodyCode(CodeBlock.Builder astCodeBlock) {
        this.astCodeBlock = astCodeBlock;
    }

    @Override
    public void visitMethodDef(JCTree.JCMethodDecl methodDecl) {
        super.visitMethodDef(methodDecl);
        List<JCTree.JCStatement> stats = methodDecl.body.stats;
        stats.forEach(jcStatement -> astCodeBlock.addStatement(jcStatement.toString()));
    }

    public CodeBlock.Builder getCodeBlock() {
        return astCodeBlock;
    }
}
