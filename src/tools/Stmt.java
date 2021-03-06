package tools;

import lox.Token;

import java.util.List;

public abstract class Stmt{
    public interface Visitor<R> {
       void visitBlockStmt();

       R visitBlockStmt(Block stmt);

       Object visitVarExpr(Var expr);

       Object visitVariableExpr(Var expr);

       R visitExpressionStmt(Expression stmt);
    R visitIfStmt(If stmt);
    R visitPrintStmt(Print stmt);
    R visitVarStmt(Var stmt);
    }
 public static class Block extends Stmt {
    public Block(List<Stmt> statements) {
    this.statements = statements;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
       return visitor.visitBlockStmt(this);
    }

    public final List<Stmt> statements;
    }
 public static class Expression extends Stmt {
    public Expression(Expr expression) {
    this.expression = expression;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
       return visitor.visitExpressionStmt(this);
    }

    public final Expr expression;
    }
 public static class If extends Stmt {
    public If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
    this.condition = condition;
    this.thenBranch = thenBranch;
    this.elseBranch = elseBranch;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
       return visitor.visitIfStmt(this);
    }

    public final Expr condition;
    public final Stmt thenBranch;
    public final Stmt elseBranch;
    }
 public static class Print extends Stmt {
    public Print(Expr expression) {
    this.expression = expression;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
       return visitor.visitPrintStmt(this);
    }

    public final Expr expression;
    }
 public static class Var extends Stmt {
    public Var(Token name, Expr initializer) {
    this.name = name;
    this.initializer = initializer;
    }



    @Override
    public <R> R accept(Visitor<R> visitor) {
       return visitor.visitVarStmt(this);
    }

    public final Token name;
    public final Expr initializer;
    }

    public abstract <R> R accept(Visitor<R> visitor);
}