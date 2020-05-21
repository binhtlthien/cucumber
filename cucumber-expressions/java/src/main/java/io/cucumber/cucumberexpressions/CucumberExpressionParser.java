//      This code was generated by Berp (http://https://github.com/gasparnagy/berp/).
//
//      Changes to this file may cause incorrect behavior and will be lost if
//      the code is regenerated.
package io.cucumber.cucumberexpressions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.util.Arrays.asList;

class CucumberExpressionParser<T> {
    private interface Func<T> {
        T call() ;
    }

    enum TokenType {
        None,
        EOF,
        TEXT,
        WHITE_SPACE,
        ALTERNATION,
        BEGIN_OPTIONAL,
        END_OPTIONAL,
        BEGIN_PARAMETER,
        END_PARAMETER,
        Other,
        ;
    }

    enum RuleType {
        None,
        _EOF, // #EOF
        _TEXT, // #TEXT
        _WHITE_SPACE, // #WHITE_SPACE
        _ALTERNATION, // #ALTERNATION
        _BEGIN_OPTIONAL, // #BEGIN_OPTIONAL
        _END_OPTIONAL, // #END_OPTIONAL
        _BEGIN_PARAMETER, // #BEGIN_PARAMETER
        _END_PARAMETER, // #END_PARAMETER
        _Other, // #Other
        CucumberExpression, // CucumberExpression! := (Separator | Parameter | Alternation)*
        Separator, // Separator! := #WHITE_SPACE
        Alternation, // Alternation! := Alternate (#ALTERNATION Alternate)*
        Alternate, // Alternate! [-&gt;#TEXT] := (Optional | Text)+
        Text, // Text! := #TEXT
        Optional, // Optional! := #BEGIN_OPTIONAL (#TEXT | #WHITE_SPACE)* #END_OPTIONAL
        Parameter, // Parameter! := #BEGIN_PARAMETER (#TEXT | #WHITE_SPACE)* #END_PARAMETER
        ;

        static RuleType cast(TokenType tokenType) {
            return RuleType.values()[tokenType.ordinal()];
        }
    }

    private final Builder<T> builder;

    boolean stopAtFirstError;

    private static class ParserContext {
        final Iterator<Token> tokens;
        final TokenMatcher tokenMatcher;
        final Queue<Token> tokenQueue;
        final List<ParserException> errors;

        ParserContext(Iterator<Token> tokens, TokenMatcher tokenMatcher, Queue<Token> tokenQueue, List<ParserException> errors) {
            this.tokens = tokens;
            this.tokenMatcher = tokenMatcher;
            this.tokenQueue = tokenQueue;
            this.errors = errors;
        }
    }

    CucumberExpressionParser(Builder<T> builder) {
        this.builder = builder;
    }


    T parse(Iterable<Token> tokens) {
        return parse(tokens, new SimpleTokenMatcher());
    }


    T parse(Iterable<Token> tokens, TokenMatcher tokenMatcher) {
        builder.reset();
        tokenMatcher.reset();

        ParserContext context = new ParserContext(
                tokens.iterator(),
                tokenMatcher,
                new LinkedList<>(),
                new ArrayList<>()
        );

        startRule(context, RuleType.CucumberExpression);
        int state = 0;
        Token token;
        do {
            token = readToken(context);
            state = matchToken(state, token, context);
        } while (!token.isEOF());

        endRule(context, RuleType.CucumberExpression);

        if (context.errors.size() > 0) {
            throw new ParserException.CompositeParserException(context.errors);
        }

        return builder.getResult();
    }

    private void addError(ParserContext context, ParserException error) {
        context.errors.add(error);
        if (context.errors.size() > 1)
            throw new ParserException.CompositeParserException(context.errors);
    }

    private <V> V handleAstError(ParserContext context, final Func<V> action) {
        return handleExternalError(context, action, null);
    }

    private <V> V handleExternalError(ParserContext context, Func<V> action, V defaultValue) {
        if (stopAtFirstError) {
            return action.call();
        }

        try {
            return action.call();
        } catch (ParserException.CompositeParserException compositeParserException) {
            for (ParserException error : compositeParserException.errors) {
                addError(context, error);
            }
        } catch (ParserException error) {
            addError(context, error);
        }
        return defaultValue;
    }

    private void build(final ParserContext context, final Token token) {
        handleAstError(context, new Func<Void>() {
            public Void call() {
                builder.build(token);
                return null;
            }
        });
    }

    private void startRule(final ParserContext context, final RuleType ruleType) {
        handleAstError(context, new Func<Void>() {
            public Void call() {
                builder.startRule(ruleType);
                return null;
            }
        });
    }

    private void endRule(final ParserContext context, final RuleType ruleType) {
        handleAstError(context, new Func<Void>() {
            public Void call() {
                builder.endRule(ruleType);
                return null;
            }
        });
    }

    private Token readToken(ParserContext context) {
        return context.tokenQueue.size() > 0 ? context.tokenQueue.remove() : context.tokens.next();
    }


    private boolean match_EOF(final ParserContext context, final Token token) {
        return handleExternalError(context, new Func<Boolean>() {
            public Boolean call() {
                return context.tokenMatcher.match_EOF(token);
            }
        }, false);
    }

    private boolean match_TEXT(final ParserContext context, final Token token) {
        if (token.isEOF()) return false;
        return handleExternalError(context, new Func<Boolean>() {
            public Boolean call() {
                return context.tokenMatcher.match_TEXT(token);
            }
        }, false);
    }

    private boolean match_WHITE_SPACE(final ParserContext context, final Token token) {
        if (token.isEOF()) return false;
        return handleExternalError(context, new Func<Boolean>() {
            public Boolean call() {
                return context.tokenMatcher.match_WHITE_SPACE(token);
            }
        }, false);
    }

    private boolean match_ALTERNATION(final ParserContext context, final Token token) {
        if (token.isEOF()) return false;
        return handleExternalError(context, new Func<Boolean>() {
            public Boolean call() {
                return context.tokenMatcher.match_ALTERNATION(token);
            }
        }, false);
    }

    private boolean match_BEGIN_OPTIONAL(final ParserContext context, final Token token) {
        if (token.isEOF()) return false;
        return handleExternalError(context, new Func<Boolean>() {
            public Boolean call() {
                return context.tokenMatcher.match_BEGIN_OPTIONAL(token);
            }
        }, false);
    }

    private boolean match_END_OPTIONAL(final ParserContext context, final Token token) {
        if (token.isEOF()) return false;
        return handleExternalError(context, new Func<Boolean>() {
            public Boolean call() {
                return context.tokenMatcher.match_END_OPTIONAL(token);
            }
        }, false);
    }

    private boolean match_BEGIN_PARAMETER(final ParserContext context, final Token token) {
        if (token.isEOF()) return false;
        return handleExternalError(context, new Func<Boolean>() {
            public Boolean call() {
                return context.tokenMatcher.match_BEGIN_PARAMETER(token);
            }
        }, false);
    }

    private boolean match_END_PARAMETER(final ParserContext context, final Token token) {
        if (token.isEOF()) return false;
        return handleExternalError(context, new Func<Boolean>() {
            public Boolean call() {
                return context.tokenMatcher.match_END_PARAMETER(token);
            }
        }, false);
    }

    private boolean match_Other(final ParserContext context, final Token token) {
        if (token.isEOF()) return false;
        return handleExternalError(context, new Func<Boolean>() {
            public Boolean call() {
                return context.tokenMatcher.match_Other(token);
            }
        }, false);
    }

    private int matchToken(int state, Token token, ParserContext context) {
        int newState;
        switch (state) {
            case 0:
                newState = matchTokenAt_0(token, context);
                break;
            case 1:
                newState = matchTokenAt_1(token, context);
                break;
            case 2:
                newState = matchTokenAt_2(token, context);
                break;
            case 3:
                newState = matchTokenAt_3(token, context);
                break;
            case 4:
                newState = matchTokenAt_4(token, context);
                break;
            case 5:
                newState = matchTokenAt_5(token, context);
                break;
            case 6:
                newState = matchTokenAt_6(token, context);
                break;
            case 7:
                newState = matchTokenAt_7(token, context);
                break;
            case 8:
                newState = matchTokenAt_8(token, context);
                break;
            case 9:
                newState = matchTokenAt_9(token, context);
                break;
            case 10:
                newState = matchTokenAt_10(token, context);
                break;
            default:
                throw new IllegalStateException("Unknown state: " + state);
        }
        return newState;
    }


    // Start
    private int matchTokenAt_0(Token token, ParserContext context) {
        if (match_EOF(context, token))
        {
                build(context, token);
            return 11;
        }
        if (match_WHITE_SPACE(context, token))
        {
                startRule(context, RuleType.Separator);
                build(context, token);
            return 1;
        }
        if (match_BEGIN_PARAMETER(context, token))
        {
                startRule(context, RuleType.Parameter);
                build(context, token);
            return 2;
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 4;
        }
        if (match_TEXT(context, token))
        {
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Text);
                build(context, token);
            return 6;
        }
        
        final String stateComment = "State: 0 - Start";
        token.detach();
        List<String> expectedTokens = asList("#EOF", "#WHITE_SPACE", "#BEGIN_PARAMETER", "#BEGIN_OPTIONAL", "#TEXT");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 0;

    }


    // CucumberExpression:0>__alt0:0>Separator:0>#WHITE_SPACE:0
    private int matchTokenAt_1(Token token, ParserContext context) {
        if (match_EOF(context, token))
        {
                endRule(context, RuleType.Separator);
                build(context, token);
            return 11;
        }
        if (match_WHITE_SPACE(context, token))
        {
                endRule(context, RuleType.Separator);
                startRule(context, RuleType.Separator);
                build(context, token);
            return 1;
        }
        if (match_BEGIN_PARAMETER(context, token))
        {
                endRule(context, RuleType.Separator);
                startRule(context, RuleType.Parameter);
                build(context, token);
            return 2;
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
                endRule(context, RuleType.Separator);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 4;
        }
        if (match_TEXT(context, token))
        {
                endRule(context, RuleType.Separator);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Text);
                build(context, token);
            return 6;
        }
        
        final String stateComment = "State: 1 - CucumberExpression:0>__alt0:0>Separator:0>#WHITE_SPACE:0";
        token.detach();
        List<String> expectedTokens = asList("#EOF", "#WHITE_SPACE", "#BEGIN_PARAMETER", "#BEGIN_OPTIONAL", "#TEXT");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 1;

    }


    // CucumberExpression:0>__alt0:1>Parameter:0>#BEGIN_PARAMETER:0
    private int matchTokenAt_2(Token token, ParserContext context) {
        if (match_TEXT(context, token))
        {
                build(context, token);
            return 2;
        }
        if (match_WHITE_SPACE(context, token))
        {
                build(context, token);
            return 2;
        }
        if (match_END_PARAMETER(context, token))
        {
                build(context, token);
            return 3;
        }
        
        final String stateComment = "State: 2 - CucumberExpression:0>__alt0:1>Parameter:0>#BEGIN_PARAMETER:0";
        token.detach();
        List<String> expectedTokens = asList("#TEXT", "#WHITE_SPACE", "#END_PARAMETER");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 2;

    }


    // CucumberExpression:0>__alt0:1>Parameter:2>#END_PARAMETER:0
    private int matchTokenAt_3(Token token, ParserContext context) {
        if (match_EOF(context, token))
        {
                endRule(context, RuleType.Parameter);
                build(context, token);
            return 11;
        }
        if (match_WHITE_SPACE(context, token))
        {
                endRule(context, RuleType.Parameter);
                startRule(context, RuleType.Separator);
                build(context, token);
            return 1;
        }
        if (match_BEGIN_PARAMETER(context, token))
        {
                endRule(context, RuleType.Parameter);
                startRule(context, RuleType.Parameter);
                build(context, token);
            return 2;
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
                endRule(context, RuleType.Parameter);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 4;
        }
        if (match_TEXT(context, token))
        {
                endRule(context, RuleType.Parameter);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Text);
                build(context, token);
            return 6;
        }
        
        final String stateComment = "State: 3 - CucumberExpression:0>__alt0:1>Parameter:2>#END_PARAMETER:0";
        token.detach();
        List<String> expectedTokens = asList("#EOF", "#WHITE_SPACE", "#BEGIN_PARAMETER", "#BEGIN_OPTIONAL", "#TEXT");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 3;

    }


    // CucumberExpression:0>__alt0:2>Alternation:0>Alternate:0>__alt2:0>Optional:0>#BEGIN_OPTIONAL:0
    private int matchTokenAt_4(Token token, ParserContext context) {
        if (match_TEXT(context, token))
        {
                build(context, token);
            return 4;
        }
        if (match_WHITE_SPACE(context, token))
        {
                build(context, token);
            return 4;
        }
        if (match_END_OPTIONAL(context, token))
        {
                build(context, token);
            return 5;
        }
        
        final String stateComment = "State: 4 - CucumberExpression:0>__alt0:2>Alternation:0>Alternate:0>__alt2:0>Optional:0>#BEGIN_OPTIONAL:0";
        token.detach();
        List<String> expectedTokens = asList("#TEXT", "#WHITE_SPACE", "#END_OPTIONAL");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 4;

    }


    // CucumberExpression:0>__alt0:2>Alternation:0>Alternate:0>__alt2:0>Optional:2>#END_OPTIONAL:0
    private int matchTokenAt_5(Token token, ParserContext context) {
        if (match_EOF(context, token))
        {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                build(context, token);
            return 11;
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Optional);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 4;
            }
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 4;
            }
        }
        if (match_TEXT(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Optional);
                startRule(context, RuleType.Text);
                build(context, token);
            return 6;
            }
        }
        if (match_TEXT(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Text);
                build(context, token);
            return 6;
            }
        }
        if (match_ALTERNATION(context, token))
        {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                build(context, token);
            return 7;
        }
        if (match_WHITE_SPACE(context, token))
        {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Separator);
                build(context, token);
            return 1;
        }
        if (match_BEGIN_PARAMETER(context, token))
        {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Parameter);
                build(context, token);
            return 2;
        }
        
        final String stateComment = "State: 5 - CucumberExpression:0>__alt0:2>Alternation:0>Alternate:0>__alt2:0>Optional:2>#END_OPTIONAL:0";
        token.detach();
        List<String> expectedTokens = asList("#EOF", "#BEGIN_OPTIONAL", "#TEXT", "#ALTERNATION", "#WHITE_SPACE", "#BEGIN_PARAMETER");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 5;

    }


    // CucumberExpression:0>__alt0:2>Alternation:0>Alternate:1>__alt2:1>Text:0>#TEXT:0
    private int matchTokenAt_6(Token token, ParserContext context) {
        if (match_EOF(context, token))
        {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                build(context, token);
            return 11;
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Text);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 4;
            }
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 4;
            }
        }
        if (match_TEXT(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Text);
                startRule(context, RuleType.Text);
                build(context, token);
            return 6;
            }
        }
        if (match_TEXT(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Text);
                build(context, token);
            return 6;
            }
        }
        if (match_ALTERNATION(context, token))
        {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                build(context, token);
            return 7;
        }
        if (match_WHITE_SPACE(context, token))
        {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Separator);
                build(context, token);
            return 1;
        }
        if (match_BEGIN_PARAMETER(context, token))
        {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Parameter);
                build(context, token);
            return 2;
        }
        
        final String stateComment = "State: 6 - CucumberExpression:0>__alt0:2>Alternation:0>Alternate:1>__alt2:1>Text:0>#TEXT:0";
        token.detach();
        List<String> expectedTokens = asList("#EOF", "#BEGIN_OPTIONAL", "#TEXT", "#ALTERNATION", "#WHITE_SPACE", "#BEGIN_PARAMETER");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 6;

    }


    // CucumberExpression:0>__alt0:2>Alternation:1>__grp1:0>#ALTERNATION:0
    private int matchTokenAt_7(Token token, ParserContext context) {
        if (match_BEGIN_OPTIONAL(context, token))
        {
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 8;
        }
        if (match_TEXT(context, token))
        {
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Text);
                build(context, token);
            return 10;
        }
        
        final String stateComment = "State: 7 - CucumberExpression:0>__alt0:2>Alternation:1>__grp1:0>#ALTERNATION:0";
        token.detach();
        List<String> expectedTokens = asList("#BEGIN_OPTIONAL", "#TEXT");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 7;

    }


    // CucumberExpression:0>__alt0:2>Alternation:1>__grp1:1>Alternate:0>__alt2:0>Optional:0>#BEGIN_OPTIONAL:0
    private int matchTokenAt_8(Token token, ParserContext context) {
        if (match_TEXT(context, token))
        {
                build(context, token);
            return 8;
        }
        if (match_WHITE_SPACE(context, token))
        {
                build(context, token);
            return 8;
        }
        if (match_END_OPTIONAL(context, token))
        {
                build(context, token);
            return 9;
        }
        
        final String stateComment = "State: 8 - CucumberExpression:0>__alt0:2>Alternation:1>__grp1:1>Alternate:0>__alt2:0>Optional:0>#BEGIN_OPTIONAL:0";
        token.detach();
        List<String> expectedTokens = asList("#TEXT", "#WHITE_SPACE", "#END_OPTIONAL");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 8;

    }


    // CucumberExpression:0>__alt0:2>Alternation:1>__grp1:1>Alternate:0>__alt2:0>Optional:2>#END_OPTIONAL:0
    private int matchTokenAt_9(Token token, ParserContext context) {
        if (match_EOF(context, token))
        {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                build(context, token);
            return 11;
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Optional);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 8;
            }
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 4;
            }
        }
        if (match_TEXT(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Optional);
                startRule(context, RuleType.Text);
                build(context, token);
            return 10;
            }
        }
        if (match_TEXT(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Text);
                build(context, token);
            return 6;
            }
        }
        if (match_ALTERNATION(context, token))
        {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                build(context, token);
            return 7;
        }
        if (match_WHITE_SPACE(context, token))
        {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Separator);
                build(context, token);
            return 1;
        }
        if (match_BEGIN_PARAMETER(context, token))
        {
                endRule(context, RuleType.Optional);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Parameter);
                build(context, token);
            return 2;
        }
        
        final String stateComment = "State: 9 - CucumberExpression:0>__alt0:2>Alternation:1>__grp1:1>Alternate:0>__alt2:0>Optional:2>#END_OPTIONAL:0";
        token.detach();
        List<String> expectedTokens = asList("#EOF", "#BEGIN_OPTIONAL", "#TEXT", "#ALTERNATION", "#WHITE_SPACE", "#BEGIN_PARAMETER");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 9;

    }


    // CucumberExpression:0>__alt0:2>Alternation:1>__grp1:1>Alternate:1>__alt2:1>Text:0>#TEXT:0
    private int matchTokenAt_10(Token token, ParserContext context) {
        if (match_EOF(context, token))
        {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                build(context, token);
            return 11;
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Text);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 8;
            }
        }
        if (match_BEGIN_OPTIONAL(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Optional);
                build(context, token);
            return 4;
            }
        }
        if (match_TEXT(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Text);
                startRule(context, RuleType.Text);
                build(context, token);
            return 10;
            }
        }
        if (match_TEXT(context, token))
        {
            if (lookahead_0(context, token))
            {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternation);
                startRule(context, RuleType.Alternate);
                startRule(context, RuleType.Text);
                build(context, token);
            return 6;
            }
        }
        if (match_ALTERNATION(context, token))
        {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                build(context, token);
            return 7;
        }
        if (match_WHITE_SPACE(context, token))
        {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Separator);
                build(context, token);
            return 1;
        }
        if (match_BEGIN_PARAMETER(context, token))
        {
                endRule(context, RuleType.Text);
                endRule(context, RuleType.Alternate);
                endRule(context, RuleType.Alternation);
                startRule(context, RuleType.Parameter);
                build(context, token);
            return 2;
        }
        
        final String stateComment = "State: 10 - CucumberExpression:0>__alt0:2>Alternation:1>__grp1:1>Alternate:1>__alt2:1>Text:0>#TEXT:0";
        token.detach();
        List<String> expectedTokens = asList("#EOF", "#BEGIN_OPTIONAL", "#TEXT", "#ALTERNATION", "#WHITE_SPACE", "#BEGIN_PARAMETER");
        ParserException error = token.isEOF()
                ? new ParserException.UnexpectedEOFException(token, expectedTokens, stateComment)
                : new ParserException.UnexpectedTokenException(token, expectedTokens, stateComment);
        if (stopAtFirstError)
            throw error;

        addError(context, error);
        return 10;

    }



    private boolean lookahead_0(ParserContext context, Token currentToken) {
        currentToken.detach();
        Token token;
        Queue<Token> queue = new ArrayDeque<Token>();
        boolean match = false;
        do
        {
            token = readToken(context);
            token.detach();
            queue.add(token);

            if (false
                || match_TEXT(context, token)
            )
            {
                match = true;
                break;
            }
        } while (false
        );

        context.tokenQueue.addAll(queue);

        return match;
    }


    interface Builder<T> {
        void build(Token token);
        void startRule(RuleType ruleType);
        void endRule(RuleType ruleType);
        T getResult();
        void reset();
    }

    interface TokenMatcher {
        boolean match_EOF(Token token);
        boolean match_TEXT(Token token);
        boolean match_WHITE_SPACE(Token token);
        boolean match_ALTERNATION(Token token);
        boolean match_BEGIN_OPTIONAL(Token token);
        boolean match_END_OPTIONAL(Token token);
        boolean match_BEGIN_PARAMETER(Token token);
        boolean match_END_PARAMETER(Token token);
        boolean match_Other(Token token);
        void reset();
    }

    class SimpleTokenMatcher implements TokenMatcher {

        @Override
        public void reset() {
        }

        @Override
        public boolean match_EOF(Token token) {
            return token.tokenType == TokenType.EOF;
        }

        @Override
        public boolean match_TEXT(Token token) {
            return token.tokenType == TokenType.TEXT;
        }

        @Override
        public boolean match_WHITE_SPACE(Token token) {
            return token.tokenType == TokenType.WHITE_SPACE;
        }

        @Override
        public boolean match_ALTERNATION(Token token) {
            return token.tokenType == TokenType.ALTERNATION;
        }

        @Override
        public boolean match_BEGIN_OPTIONAL(Token token) {
            return token.tokenType == TokenType.BEGIN_OPTIONAL;
        }

        @Override
        public boolean match_END_OPTIONAL(Token token) {
            return token.tokenType == TokenType.END_OPTIONAL;
        }

        @Override
        public boolean match_BEGIN_PARAMETER(Token token) {
            return token.tokenType == TokenType.BEGIN_PARAMETER;
        }

        @Override
        public boolean match_END_PARAMETER(Token token) {
            return token.tokenType == TokenType.END_PARAMETER;
        }

        @Override
        public boolean match_Other(Token token) {
            return token.tokenType == TokenType.Other;
        }
    }
}
