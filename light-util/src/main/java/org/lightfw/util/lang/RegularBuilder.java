package org.lightfw.util.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;

/**
 * RegularBuilder
 *
 * @author Michael.Wang
 * @date 2017/10/19
 */
public class RegularBuilder {

    StringBuilder prefixes = new StringBuilder();
    StringBuilder source = new StringBuilder();
    StringBuilder suffixes = new StringBuilder();
    int modifiers = Pattern.MULTILINE;

    private static final Map<Character, Integer> SYMBOL_MAP = new HashMap<Character, Integer>() {{
        put('d', Pattern.UNIX_LINES);
        put('i', Pattern.CASE_INSENSITIVE);
        put('x', Pattern.COMMENTS);
        put('m', Pattern.MULTILINE);
        put('s', Pattern.DOTALL);
        put('u', Pattern.UNICODE_CASE);
        put('U', Pattern.UNICODE_CHARACTER_CLASS);
    }};

    /**
     * Package private. Use regex() to build a new one
     *
     * @since 1.2
     */
    RegularBuilder() {
    }

    /**
     * Escapes any non-word char with two backslashes
     * used by any method, except {@link #add(String)}
     *
     * @param pValue - the string for char escaping
     * @return sanitized string value
     */
    private String sanitize(final String pValue) {
        return pValue.replaceAll("[\\W]", "\\\\$0");
    }

    /**
     * Counts occurrences of some substring in whole string
     * Same as org.apache.commons.lang3.StringUtils#countMatches(String, java.lang.String)
     * by effect. Used to count braces for {@link #or(String)} method
     *
     * @param where - where to find
     * @param what  - what needs to count matches
     * @return 0 if nothing found, count of occurrences instead
     */
    private int countOccurrencesOf(String where, String what) {
        return (where.length() - where.replace(what, "").length()) / what.length();
    }

    public RegularUtil build() {
        Pattern pattern = Pattern.compile(new StringBuilder(prefixes).append(source).append(suffixes).toString(), modifiers);
        return new RegularUtil(pattern);
    }

    /**
     * Append literal expression
     * Everything added to the expression should go trough this method
     * (keep in mind when creating your own methods).
     * All existing methods already use this, so for basic usage, you can just ignore this method.
     * <p/>
     * Example:
     * regex().add("\n.*").build() // produce exact "\n.*" regexp
     *
     * @param pValue - literal expression, not sanitized
     * @return this builder
     */
    public RegularBuilder add(final String pValue) {
        this.source.append(pValue);
        return this;
    }

    /**
     * Append a regex from builder and wrap it with unnamed group (?: ... )
     *
     * @param regex - RegularUtil.Builder, that not changed
     * @return this builder
     * @since 1.2
     */
    public RegularBuilder add(final RegularBuilder regex) {
        return this.group().add(regex.build().toString()).endGr();
    }

    /**
     * Enable or disable the expression to start at the beginning of the line
     *
     * @param pEnable - enables or disables the line starting
     * @return this builder
     */
    public RegularBuilder startOfLine(final boolean pEnable) {
        this.prefixes.append(pEnable ? "^" : "");
        if (!pEnable) {
            this.prefixes = new StringBuilder(this.prefixes.toString().replace("^", ""));
        }
        return this;
    }

    /**
     * Mark the expression to start at the beginning of the line
     * Same as {@link #startOfLine(boolean)} with true arg
     *
     * @return this builder
     */
    public RegularBuilder startOfLine() {
        return startOfLine(true);
    }

    /**
     * Enable or disable the expression to end at the last character of the line
     *
     * @param pEnable - enables or disables the line ending
     * @return this builder
     */
    public RegularBuilder endOfLine(final boolean pEnable) {
        this.suffixes.append(pEnable ? "$" : "");
        if (!pEnable) {
            this.suffixes = new StringBuilder(this.suffixes.toString().replace("$", ""));
        }
        return this;
    }

    /**
     * Mark the expression to end at the last character of the line
     * Same as {@link #endOfLine(boolean)} with true arg
     *
     * @return this builder
     */
    public RegularBuilder endOfLine() {
        return endOfLine(true);
    }

    /**
     * Add a string to the expression
     *
     * @param pValue - the string to be looked for (sanitized)
     * @return this builder
     */
    public RegularBuilder then(final String pValue) {
        return this.add("(?:" + sanitize(pValue) + ")");
    }

    /**
     * Add a string to the expression
     * Syntax sugar for {@link #then(String)} - use it in case:
     * regex().find("string") // when it goes first
     *
     * @param value - the string to be looked for (sanitized)
     * @return this builder
     */
    public RegularBuilder find(final String value) {
        return this.then(value);
    }

    /**
     * Add a string to the expression that might appear once (or not)
     * Example:
     * The following matches all strings that contain http:// or https://
     * RegularUtil regex = regex()
     * .find("http")
     * .maybe("s")
     * .then("://")
     * .anythingBut(" ").build();
     * regex.test("http://")    //true
     * regex.test("https://")   //true
     *
     * @param pValue - the string to be looked for
     * @return this builder
     */
    public RegularBuilder maybe(final String pValue) {
        return this.then(pValue).add("?");
    }

    /**
     * Add a regex to the expression that might appear once (or not)
     * Example:
     * The following matches all names that have a prefix or not.
     * RegularUtil.Builder namePrefix = regex().oneOf("Mr.", "Ms.");
     * RegularUtil name = regex()
     * .maybe(namePrefix)
     * .space()
     * .zeroOrMore()
     * .word()
     * .oneOrMore()
     * .build();
     * regex.test("Mr. Bond/")    //true
     * regex.test("James")   //true
     *
     * @param regex - the string to be looked for
     * @return this builder
     */
    public RegularBuilder maybe(final RegularBuilder regex) {
        return this.group().add(regex).endGr().add("?");
    }

    /**
     * Add expression that matches anything (includes empty string)
     *
     * @return this builder
     */
    public RegularBuilder anything() {
        return this.add("(?:.*)");
    }

    /**
     * Add expression that matches anything, but not passed argument
     *
     * @param pValue - the string not to match
     * @return this builder
     */
    public RegularBuilder anythingBut(final String pValue) {
        return this.add("(?:[^" + sanitize(pValue) + "]*)");
    }

    /**
     * Add expression that matches something that might appear once (or more)
     *
     * @return this builder
     */
    public RegularBuilder something() {
        return this.add("(?:.+)");
    }

    public RegularBuilder somethingButNot(final String pValue) {
        return this.add("(?:[^" + sanitize(pValue) + "]+)");
    }

    /**
     * Add universal line break expression
     *
     * @return this builder
     */
    public RegularBuilder lineBreak() {
        return this.add("(?:\\n|(?:\\r\\n)|(?:\\r\\r))");
    }

    /**
     * Shortcut for {@link #lineBreak()}
     *
     * @return this builder
     */
    public RegularBuilder br() {
        return this.lineBreak();
    }

    /**
     * Add expression to match a tab character ('\u0009')
     *
     * @return this builder
     */
    public RegularBuilder tab() {
        return this.add("(?:\\t)");
    }

    /**
     * Add word, same as [a-zA-Z_0-9]+
     *
     * @return this builder
     */
    public RegularBuilder word() {
        return this.add("(?:\\w+)");
    }


    /*
       --- Predefined character classes
     */

    /**
     * Add word character, same as [a-zA-Z_0-9]
     *
     * @return this builder
     */
    public RegularBuilder wordChar() {
        return this.add("(?:\\w)");
    }


    /**
     * Add non-word character: [^\w]
     *
     * @return this builder
     */
    public RegularBuilder nonWordChar() {
        return this.add("(?:\\W)");
    }

    /**
     * Add non-digit: [^0-9]
     *
     * @return this builder
     */
    public RegularBuilder nonDigit() {
        return this.add("(?:\\D)");
    }

    /**
     * Add same as [0-9]
     *
     * @return this builder
     */
    public RegularBuilder digit() {
        return this.add("(?:\\d)");
    }

    /**
     * Add whitespace character, same as [ \t\n\x0B\f\r]
     *
     * @return this builder
     */
    public RegularBuilder space() {
        return this.add("(?:\\s)");
    }

    /**
     * Add non-whitespace character: [^\s]
     *
     * @return this builder
     */
    public RegularBuilder nonSpace() {
        return this.add("(?:\\S)");
    }


    /*
       --- / end of predefined character classes
     */


    public RegularBuilder anyOf(final String pValue) {
        this.add("[" + sanitize(pValue) + "]");
        return this;
    }

    /**
     * Shortcut to {@link #anyOf(String)}
     *
     * @param value - CharSequence every char from can be matched
     * @return this builder
     */
    public RegularBuilder any(final String value) {
        return this.anyOf(value);
    }

    /**
     * Add expression to match a range (or multiply ranges)
     * Usage: .range(from, to [, from, to ... ])
     * Example: The following matches a hexadecimal number:
     * regex().range( "0", "9", "a", "f") // produce [0-9a-f]
     *
     * @param pArgs - pairs for range
     * @return this builder
     */
    public RegularBuilder range(final String... pArgs) {
        StringBuilder value = new StringBuilder("[");
        for (int firstInPairPosition = 1; firstInPairPosition < pArgs.length; firstInPairPosition += 2) {
            String from = sanitize(pArgs[firstInPairPosition - 1]);
            String to = sanitize(pArgs[firstInPairPosition]);

            value.append(from).append("-").append(to);
        }
        value.append("]");

        return this.add(value.toString());
    }

    public RegularBuilder addModifier(final char pModifier) {
        if (SYMBOL_MAP.containsKey(pModifier)) {
            modifiers |= SYMBOL_MAP.get(pModifier);
        }

        return this;
    }

    public RegularBuilder removeModifier(final char pModifier) {
        if (SYMBOL_MAP.containsKey(pModifier)) {
            modifiers &= ~SYMBOL_MAP.get(pModifier);
        }

        return this;
    }

    public RegularBuilder withAnyCase(final boolean pEnable) {
        if (pEnable) {
            this.addModifier('i');
        } else {
            this.removeModifier('i');
        }
        return this;
    }

    /**
     * Turn ON matching with ignoring case
     * Example:
     * // matches "a"
     * // matches "A"
     * regex().find("a").withAnyCase()
     *
     * @return this builder
     */
    public RegularBuilder withAnyCase() {
        return withAnyCase(true);
    }

    public RegularBuilder searchOneLine(final boolean pEnable) {
        if (pEnable) {
            this.removeModifier('m');
        } else {
            this.addModifier('m');
        }
        return this;
    }

    /**
     * Convenient method to show that string usage count is exact count, range count or simply one or more
     * Usage:
     * regex().multiply("abc")                                  // Produce (?:abc)+
     * regex().multiply("abc", null)                            // Produce (?:abc)+
     * regex().multiply("abc", (int)from)                       // Produce (?:abc){from}
     * regex().multiply("abc", (int)from, (int)to)              // Produce (?:abc){from, to}
     * regex().multiply("abc", (int)from, (int)to, (int)...)    // Produce (?:abc)+
     *
     * @param pValue - the string to be looked for
     * @param count  - (optional) if passed one or two numbers, it used to show count or range count
     * @return this builder
     * @see #oneOrMore()
     * @see #then(String)
     * @see #zeroOrMore()
     */
    public RegularBuilder multiple(final String pValue, final int... count) {
        if (count == null) {
            return this.then(pValue).oneOrMore();
        }
        switch (count.length) {
            case 1:
                return this.then(pValue).count(count[0]);
            case 2:
                return this.then(pValue).count(count[0], count[1]);
            default:
                return this.then(pValue).oneOrMore();
        }
    }

    /**
     * Adds "+" char to regexp
     * Same effect as {@link #atLeast(int)} with "1" argument
     * Also, used by {@link #multiple(String, int...)} when second argument is null, or have length more than 2
     *
     * @return this builder
     * @since 1.2
     */
    public RegularBuilder oneOrMore() {
        return this.add("+");
    }

    /**
     * Adds "*" char to regexp, means zero or more times repeated
     * Same effect as {@link #atLeast(int)} with "0" argument
     *
     * @return this builder
     * @since 1.2
     */
    public RegularBuilder zeroOrMore() {
        return this.add("*");
    }

    /**
     * Add count of previous group
     * for example:
     * .find("w").count(3) // produce - (?:w){3}
     *
     * @param count - number of occurrences of previous group in expression
     * @return this Builder
     */
    public RegularBuilder count(final int count) {
        this.source.append("{").append(count).append("}");
        return this;
    }

    /**
     * Produce range count
     * for example:
     * .find("w").count(1, 3) // produce (?:w){1,3}
     *
     * @param from - minimal number of occurrences
     * @param to   - max number of occurrences
     * @return this Builder
     * @see #count(int)
     */
    public RegularBuilder count(final int from, final int to) {
        this.source.append("{").append(from).append(",").append(to).append("}");
        return this;
    }

    /**
     * Produce range count with only minimal number of occurrences
     * for example:
     * .find("w").atLeast(1) // produce (?:w){1,}
     *
     * @param from - minimal number of occurrences
     * @return this Builder
     * @see #count(int)
     * @see #oneOrMore()
     * @see #zeroOrMore()
     * @since 1.2
     */
    public RegularBuilder atLeast(final int from) {
        return this.add("{").add(valueOf(from)).add(",}");
    }

    /**
     * Add a alternative expression to be matched
     * <p>
     * Issue #32
     *
     * @param pValue - the string to be looked for
     * @return this builder
     */
    public RegularBuilder or(final String pValue) {
        this.prefixes.append("(?:");

        int opened = countOccurrencesOf(this.prefixes.toString(), "(");
        int closed = countOccurrencesOf(this.suffixes.toString(), ")");

        if (opened >= closed) {
            this.suffixes = new StringBuilder(")" + this.suffixes.toString());
        }

        this.add(")|(?:");
        if (pValue != null) {
            this.then(pValue);
        }
        return this;
    }

    /**
     * Adds an alternative expression to be matched
     * based on an array of values
     *
     * @param pValues - the strings to be looked for
     * @return this builder
     * @since 1.3
     */
    public RegularBuilder oneOf(final String... pValues) {
        if (pValues != null && pValues.length > 0) {
            this.add("(?:");
            for (int i = 0; i < pValues.length; i++) {
                String value = pValues[i];
                this.add("(?:");
                this.add(value);
                this.add(")");
                if (i < pValues.length - 1) {
                    this.add("|");
                }
            }
            this.add(")");
        }
        return this;
    }

    /**
     * Adds capture - open brace to current position and closed to suffixes
     *
     * @return this builder
     */
    public RegularBuilder capture() {
        this.suffixes.append(")");
        return this.add("(");
    }

    /**
     * Shortcut for {@link #capture()}
     *
     * @return this builder
     * @since 1.2
     */
    public RegularBuilder capt() {
        return this.capture();
    }

    /**
     * Same as {@link #capture()}, but don't save result
     * May be used to set count of duplicated captures, without creating a new saved capture
     * Example:
     * // Without group() - count(2) applies only to second capture
     * regex().group()
     * .capt().range("0", "1").endCapt().tab()
     * .capt().digit().count(5).endCapt()
     * .endGr().count(2);
     *
     * @return this builder
     * @since 1.2
     */
    public RegularBuilder group() {
        this.suffixes.append(")");
        return this.add("(?:");
    }

    /**
     * Close brace for previous capture and remove last closed brace from suffixes
     * Can be used to continue build regex after capture or to add multiply captures
     *
     * @return this builder
     */
    public RegularBuilder endCapture() {
        if (this.suffixes.indexOf(")") != -1) {
            this.suffixes.setLength(suffixes.length() - 1);
            return this.add(")");
        } else {
            throw new IllegalStateException("Can't end capture (group) when it not started");
        }
    }

    /**
     * Shortcut for {@link #endCapture()}
     *
     * @return this builder
     * @since 1.2
     */
    public RegularBuilder endCapt() {
        return this.endCapture();
    }

    /**
     * Closes current unnamed and unmatching group
     * Shortcut for {@link #endCapture()}
     * Use it with {@link #group()} for prettify code
     * Example:
     * regex().group().maybe("word").count(2).endGr()
     *
     * @return this builder
     * @since 1.2
     */
    public RegularBuilder endGr() {
        return this.endCapture();
    }
}