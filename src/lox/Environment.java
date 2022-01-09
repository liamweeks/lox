package lox;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    final Environment enclosing;
    Environment() {
        enclosing = null;
    }
    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }
    private final Map<String, Object> values = new HashMap<>();

    Object get(Token name) {
        if (values.containsKey(name.lexeme)) {
            return values.get(name.lexeme);
        }

        if (enclosing != null) return enclosing.get(name);

        throw new RuntimeError(name, "Undefined variable " + name.lexeme + ".");
    }

    void assign(Token name, Object value) {
        if (values.containsKey(name.lexeme)) {
            values.put(name.lexeme, value);
            return;
        }

        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }

    void define(String name, Object value) {
        values.put(name, value);
    }

    /*
    * Currently, (only with the define method, this is valid)
    var a = "before";
    print a; // "before".
    var a = "after";
    print a; // "after".
    *
    * However, doing so interacts poorly with the REPL.
    * In the middle of a REPL session, it’s nice to not
    * have to mentally track which variables you’ve already
    * defined. We could allow redefinition in the REPL but
    * not in scripts, but then users would have to learn two
    * sets of rules, and code copied and pasted from one form
    * to the other might not work.
    * */
}
