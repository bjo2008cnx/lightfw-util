package org.lightfw.util.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegularUtil {

    private final Pattern pattern;

    RegularUtil(final Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Test that full string matches regular expression
     *
     * @param pToTest - string to check match
     * @return true if matches exact string, false otherwise
     */
    public boolean testExact(final String pToTest) {
        boolean ret = false;
        if (pToTest != null) {
            ret = pattern.matcher(pToTest).matches();
        }
        return ret;
    }

    /**
     * Test that full string contains regex
     *
     * @param pToTest - string to check match
     * @return true if string contains regex, false otherwise
     */
    public boolean test(final String pToTest) {
        boolean ret = false;
        if (pToTest != null) {
            ret = pattern.matcher(pToTest).find();
        }
        return ret;
    }

    /**
     * Extract full string that matches regex
     * Same as {@link #getText(String, int)} for 0 group
     *
     * @param toTest - string to extract from
     * @return group 0, extracted from text
     */
    public String getText(final String toTest) {
        return getText(toTest, 0);
    }

    /**
     * Extract exact group from string
     *
     * @param toTest - string to extract from
     * @param group  - group to extract
     * @return extracted group
     * @since 1.1
     */
    public String getText(final String toTest, final int group) {
        Matcher m = pattern.matcher(toTest);
        StringBuilder result = new StringBuilder();
        while (m.find()) {
            result.append(m.group(group));
        }
        return result.toString();
    }

    /**
     * Extract exact group from string and add it to list
     *
     * Example:
     * String text = "SampleHelloWorldString";
     * RegularUtil regex = regex().capt().oneOf("Hello", "World").endCapt().maybe("String").build();
     * list = regex.getTextGroups(text, 0) //result: "Hello", "WorldString"
     * list = regex.getTextGroups(text, 1) //result: "Hello", "World"
     *
     * @param toTest - string to extract from
     * @param group  - group to extract
     * @return list of extracted groups
     */
    public List<String> getTextGroups(final String toTest, final int group) {
        List<String> groups = new ArrayList<>();
        Matcher m = pattern.matcher(toTest);
        while (m.find()) {
            groups.add(m.group(group));
        }
        return groups;
    }

    @Override
    public String toString() {
        return pattern.pattern();
    }

    /**
     * Creates new instance of RegularUtil builder from cloned builder
     *
     * @param pBuilder - instance to clone
     * @return new RegularUtil.Builder copied from passed
     * @since 1.1
     */
    public static RegularBuilder regex(final RegularBuilder pBuilder) {
        RegularBuilder builder = new RegularBuilder();

        //Using created StringBuilder
        builder.prefixes.append(pBuilder.prefixes);
        builder.source.append(pBuilder.source);
        builder.suffixes.append(pBuilder.suffixes);
        builder.modifiers = pBuilder.modifiers;

        return builder;
    }

    /**
     * Creates new instance of RegularUtil builder
     *
     * @return new RegularUtil.Builder
     * @since 1.1
     */
    public static RegularBuilder regex() {
        return new RegularBuilder();
    }
}
