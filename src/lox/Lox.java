package lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import tools.Stmt;
import static lox.TokenType.EOF;

public class Lox {
    private static final Interpreter interpreter = new Interpreter();
    static boolean hasError = false;
    static boolean hadRuntimeError = false;
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("jLox Interpreter");
            System.exit(64);
        } else if (args.length == 1 ) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hasError) System.exit(65);
        if (hadRuntimeError) System.exit(70);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("(LoxEnv) >>>");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hasError = false;
        }
    }

    private static void run(String source) {
        CustomScanner scanner = new CustomScanner(source);
        List<lox.Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        // Stop if there's a syntax error
        if (hasError) return;

        //System.out.println(new ASTPrinter().print(expression)); // This prints the AST
        interpreter.interpret(statements);
    }

    static void error(int line, String message) {
        report(line, "", message);
    }


    private static void report(int line, String where, String message) {
        System.err.println("Line: " + line + "Error: " + where + "Message: " + message);
        hasError = true;
    }


    public static void error(Token token, String message) {
        if (token.type == EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\n [line " + error.token.line + "]");
        hadRuntimeError = true;
    }

}