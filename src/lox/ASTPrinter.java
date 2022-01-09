package lox;

import tools.Expr;
import tools.Stmt;

class ASTPrinter implements Expr.Visitor<Object> {

    Object print(Expr expr) {
        return expr.accept(this);
    }

    // TODO: No longer needed after printing Expr.java

    @Override
    public Object visitAssignExpr(Expr.Assign expr) {
        return null;
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    @Override
    public Object visitVariableExpr(Expr.Variable expr) {
        return null;
    }

    @Override
    public Object visitVarExpr(Stmt.Var expr) {
        return null;
    }

    @Override
    public Object visitVariableExpr(Stmt.Var expr) {
        return null;
    }


    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        evaluate(stmt.expression);
        return null;
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);

        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));

        }
        builder.append(")");

        return builder.toString();
    }

    public Object evaluate(Expr expr) {
        return expr.accept(this);
    }


    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
                new Expr.Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123)),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(
                        new Expr.Literal(45.67)
                )
        );

        System.out.println(new ASTPrinter().print(expression));
    }

}

