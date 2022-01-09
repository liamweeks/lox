package lox;

enum TokenType {
    // Single Character Tokens:
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

    // One or Two Character Tokens
    BANG, BANG_EQUAL, EQUAL, EQUAL_EQUAL, GREATER, GREATER_THAN, LESS, LESS_THAN,

    // Literals
    IDENTIFIER, STRING, NUMBER,

    // Keywords
    AND, CLASS, ELSE, IF, FALSE, TRUE, FUN, NIL, OR, PRINT, RETURN, SUPER, THIS, VAR, WHILE, EOF, FOR;
}