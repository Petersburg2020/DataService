package nx.peter.java.util;

import nx.peter.java.document.core.Document;
import nx.peter.java.pis.Pis;
import nx.peter.java.pis.core.Node;
import nx.peter.java.util.data.IData;
import nx.peter.java.util.data.Word;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Util {

    /**
     * Formats Strings to follow the given length of characters per line
     *
     * @param line   line to be formatted
     * @param length length of characters in the expected line
     * @return formatted line
     */
    public static String formatLine(CharSequence line, int length) {
        Word data = new Word(line != null ? line.toString() : ""), formatted = new Word();
        if (length <= 0 || length > data.length()) return data.get();

        int start = 0, end = 0;
        while (end < data.length() && start < data.length()) {
            end = start + length;
            if (start < data.length() && end > data.length() - 1) {
                formatted.append(data.subLetters(start));
                break;
            } else if (end > data.length() - 1) break;
            // System.out.println("Before: " + end);
            TrimmedLine trimmed = trimLine(data.get(), data.subLetters(start, end).get(), start);
            formatted.append(trimmed.line)
                    .append("\n");
            start = end - trimmed.trimmed.length();
            // System.out.println("After: " + start);
            if (start <= -1 || start > data.length() - 1) break;
        }
        return formatted.get();
    }

    public static String centerLine(CharSequence line, int length) {
        Word data = new Word(line != null ? line.toString() : ""), centered = new Word();
        if (length <= 0 || length == data.length()) return data.get();
        else if (length > data.length()) {
            int diff = length - data.length();
            diff += diff % 2 == 0 ? 0 : 1;
            // System.out.println("Length Diff: " + diff);
            return centered.set(" ".repeat(diff / 2) + data.get() + " ".repeat(length - data.length() - diff / 2)).get();
        }

        int start = 0, end = 0;
        while (end < data.length() && start < data.length()) {
            end = start + length;
            if (start < data.length() && end > data.length() - 1) {
                centered.append(centerLine(data.subLetters(start).get(), length));
                break;
            } else if (end > data.length() - 1) break;
            TrimmedLine trimmed = trimLine(data.get(), data.subLetters(start, end).get(), start);
            centered.append(centerLine(trimmed.line, length))
                    .append("\n");
            start = end + trimmed.trimmed.length();
            if (start <= -1 || start > data.length() - 1) break;
        }
        return centered.get();
    }

    public static String rightLine(CharSequence line, int length) {
        Word data = new Word(line != null ? line.toString() : ""), right = new Word();
        if (length <= 0 || length == data.length()) return data.get();
        else if (length > data.length()) {
            int diff = length - data.length();
            // System.out.println("Length Diff: " + diff);
            return right.set(" ".repeat(diff) + data.get()).get();
        }

        int start = 0, end = 0;
        while (end < data.length() && start < data.length()) {
            end = start + length;
            if (start < data.length() && end > data.length() - 1) {
                right.append(rightLine(data.subLetters(start).get(), length));
                break;
            } else if (end > data.length() - 1) break;
            TrimmedLine trimmed = trimLine(data.get(), data.subLetters(start, end).get(), start);
            right.append(rightLine(trimmed.line, length))
                    .append("\n");
            start = end + trimmed.trimmed.length();
            if (start <= -1 || start > data.length() - 1) break;
        }
        return right.get();
    }

    public static TrimmedLine trimLine(CharSequence line, CharSequence formatted, int start) {
        Word data = new Word(line != null ? line : ""), wrap = new Word(formatted != null ? formatted : "");
        Word.Words words = data.subLetters(0, wrap.length()).getWordsOnly(),
                wrapWords = wrap.getWordsOnly();
        if (data.subLetters(start).startsWith(wrap.get()) && wrap.endsWith(wrapWords.getLast())) {
            // System.out.println("Words: " + words.size() + ", Trimmed: " + wrapWords.size() + ", Equals: " + (words.get(wrapWords.size() - 1)));
            // System.out.println("\nEquals: " + (words.size() > wrapWords.size()));// && words.get(wrapWords.size() - 1).equals(wrapWords.getLast())));
            if (words.size() >= wrapWords.size() && words.isNotEmpty() && wrapWords.isNotEmpty() && words.get(wrapWords.size() - 1).equals(wrapWords.getLast())) {
                // System.out.println("We are here finally!");
                return new TrimmedLine(wrap.get(), "");
            } else if (words.size() >= wrapWords.size() && words.isNotEmpty() && wrapWords.isNotEmpty() && words.get(wrapWords.size() - 1).contains(wrapWords.getLast()) && wrapWords.getLast().length() >= 4) {
                int end = start + wrap.lastIndexOf(wrapWords.getLast().get()) + wrapWords.getLast().length();
                // System.out.println("Start0: " + start + ", End0: " + end + ", Length0: " + data.length());
                return new TrimmedLine(
                        data.subLetters(start, end - 1).get().trim() + "-",
                        wrapWords.getLast().substring(wrapWords.getLast().length() - 1));
            }
            int end = start + wrap.lastIndexOf(wrapWords.getLast().get());
            // System.out.println("Start: " + start + ", End: " + end + ", Length: " + data.length());
            return new TrimmedLine(
                    data.subLetters(start, end).get().trim(),
                    wrapWords.getLast().get());
        }
        return new TrimmedLine(wrap.get().trim(), "");
    }


    public static String toJson(List<Object> json, int indent) {
        String pretty = toJson(json, indent, false);
        return pretty.substring(0, pretty.trim().length() - 1) + "]";
    }

    public static String toPrettyJson(List<Object> json, int indent) {
        String pretty = toJson(json, indent, true);
        return pretty.substring(0, pretty.trim().length() - 1) + "\n" + tab(indent) + "]";
    }

    public static String toJson(Map<String, Object> json, int indent) {
        String notPretty = toJson(json, indent, false);
        return "{" + notPretty.substring(1, notPretty.trim().length() - 1) + '}';
    }

    public static String toPrettyJson(Map<String, Object> json, int indent) {
        String pretty = toJson(json, indent, true);
        return "{" + pretty.substring(1, pretty.trim().length() - 1) + "\n" + tab(indent) + '}';
    }

    public static String toJson(List<Object> json, int indent, boolean format) {
        // System.out.println("Indent: " + indent);
        return json.stream().map(e -> {
            Object value = e;
            if (isString(value))
                value = "\"" + value + "\"";
            else if (isObject(value))
                value = format ? toPrettyJson((Map<String, Object>) value, indent + 1) : toJson((Map<String, Object>) value, indent + 1);
            else if (isArray(value))
                value = format ? toPrettyJson((List<Object>) value, indent + 1) : toJson((List<Object>) value, indent + 1);
            return (format ? "\n" + tab(indent + 1) : "") + value;
        }).toList().toString();
    }

    public static String toJson(Map<String, Object> json, int indent, boolean format) {
        return json.entrySet().stream().map(e -> {
            Object value = e.getValue();
            if (isString(value))
                value = "\"" + value + "\"";
            else if (isObject(value))
                value = format ? toPrettyJson((Map<String, Object>) value, indent + 1) : toJson((Map<String, Object>) value, indent + 1);
            else if (isArray(value))
                value = format ? toPrettyJson((List<Object>) value, indent + 1) : toJson((List<Object>) value, indent + 1);
            return (format ? "\n" + tab(indent + 1) : "") + "\"" + e.getKey() + "\": " + value;
        }).toList().toString();
    }


    public static String toPrettyAttr(Node.Attrs<Node.Attr> attrs, int indent) {
        return toAttr(attrs, indent, true);
    }

    public static String toAttr(Node.Attrs<Node.Attr> attrs, int indent) {
        return toAttr(attrs, indent, false);
    }

    public static String toAttr(Node.Attrs<Node.Attr> attrs, int indent, boolean format) {
        var word = new Word(attrs.map(attr -> {
            Object value = ((nx.peter.java.pis.reader.Node.Attr) attr).get();
            if (isString(value))
                value = "\"" + value + "\"";
            return (format ? "\n" + tab(indent + 1) : " ") + "\"" + ((nx.peter.java.pis.reader.Node.Attr) attr).getName() + "\"=" + value;
        }).collect(Collectors.joining()));
        return word.remove("{", 0).remove("}", word.lastIndexOf("}") - 1).get();
    }

    public static String toPrettyAttr(Map<String, Object> attrs, int indent) {
        return toAttr(attrs, indent, true);
    }

    public static String toAttr(Map<String, Object> attrs, int indent) {
        return toAttr(attrs, indent, false);
    }

    public static String toAttr(Map<String, Object> attrs, int indent, boolean format) {
        var word = new Word(attrs.entrySet().stream().map(e -> {
            Object value = e.getValue();
            if (isString(value))
                value = "\"" + value + "\"";
            return (format ? "\n" + tab(indent + 1) : " ") + "\"" + e.getKey() + "\"=" + value;
        }).collect(Collectors.joining()));
        return word.remove("{", 0).remove("}", word.lastIndexOf("}") - 1).get();
    }

    public static <N extends Node, A extends Node.Attr> String toPrettyPis(Pis<N, A> pis, int indent) {
        return toPis(pis, indent, true);
    }

    public static <N extends Node, A extends Node.Attr> String toPis(Pis<N, A> pis, int indent) {
        return toPis(pis, indent, false);
    }

    public static <N extends Node, A extends Node.Attr> String toPrettyPis(N pis, int indent) {
        return toPis(pis, indent, true);
    }

    public static <N extends Node, A extends Node.Attr> String toPis(N pis, int indent) {
        return toPis(pis, indent, false);
    }

    public static <N extends Node, A extends Node.Attr> String toPis(N node, int indent, boolean format) {
        String result = (format ? tab(indent) : "") + "<" + node.getTag();
        nx.peter.java.pis.reader.Node nd = (nx.peter.java.pis.reader.Node) node;
        result += (node.hasAttribute() ? "" + (format ? toPrettyAttr(nd.getAttributes(), indent) : toAttr(nd.getAttributes(), indent + 1)) : "") + ">";

        if (nd.isNotEmpty())
            if (nd.isSingleValue())
                result += (format && nd.hasAttribute() ? "\n" + tab(indent + 1) : "") + nd.get();
            else {
                int count = 0;
                for (Object o : nd.getChildren()) {
                    if (count == 0) result += format ? "\n" : "";
                    nx.peter.java.pis.reader.Node n = (nx.peter.java.pis.reader.Node) o;
                    result += format ? toPrettyPis(n, indent + 1) : toPis(n, indent + 1);
                    count++;
                    if (count < nd.getChildren().size())
                        result += format ? "\n" : " ";
                }
            }
        result += (format && (nd.hasChildren() || nd.hasAttribute()) ? "\n" + tab(indent) : "") + "</" + nd.getTag() + ">";
        return result;
    }

    public static <N extends Node, A extends Node.Attr> String toPis(Pis<N, A> pis, int indent, boolean format) {
        String result = "";
        if (pis.isValid()) {
            result += (format ? tab(indent) : "") + "<" + pis.getTag();
            result += (pis.hasAttribute() ? "" + (format ? toPrettyAttr(pis.getAttrs(), indent) : toAttr(pis.getAttrs(), indent + 1)) : "") + ">";

            if (pis.isNotEmpty())
                if (pis.isSingleValue())
                    result += (format && pis.hasAttribute() ? "\n" + tab(indent + 1) : "") + pis.get();
                else {
                    int count = 0;
                    for (N node : pis.getChildren()) {
                        if (count == 0) result += format ? "\n" : "";
                        result += format ? toPrettyPis(node, indent + 1) : toPis(node, indent + 1);
                        count++;
                        if (count < pis.getChildren().size())
                            result += format ? "\n" : " ";
                    }
                }
            result += (format && (pis.hasChildren() || pis.hasAttribute()) ? "\n" + tab(indent) : "") + "</" + pis.getTag() + ">";
        }

        return result;
    }


    public static List<Object> toObjectList(List<?> list) {
        return new ArrayList<>(list);
    }

    public static <T> T[] toArray(List<T> list) {
        return (T[]) list.toArray();
    }

    public static Map<String, Object> toObjectMap(Map<?, ?> map) {
        Map<String, Object> objectMap = new LinkedHashMap<>();
        for (Object key : map.keySet())
            objectMap.put(key.toString(), map.get(key));
        return objectMap;
    }

    public static boolean isArr(Object value) {
        return isArr(value.getClass());
    }

    public static <T> boolean isArr(Class<T> clazz) {
        return clazz.getName().startsWith("[");
    }


    public static boolean isInt(Object value) {
        return value instanceof Integer;
    }

    public static boolean isLong(Object value) {
        return value instanceof Long;
    }

    public static boolean isBoolean(Object value) {
        return value instanceof Boolean;
    }

    public static boolean isFloat(Object value) {
        return value instanceof Float;
    }

    public static boolean isDouble(Object value) {
        return value instanceof Double;
    }

    public static boolean isString(Object value) {
        return value instanceof String;
    }

    public static boolean isArray(Object value) {
        return value instanceof List;
    }

    public static boolean isObject(Object value) {
        return value instanceof Map;
    }

    public static Thread getThread(CharSequence name) {
        if (name != null)
            for (Thread t : Thread.getAllStackTraces().keySet())
                if (t.getName().contentEquals(name))
                    return t;
        return null;
    }

    public static <T> Object[] toArray(T value) {
        if (isArr(value)) {
            if (getTClass(value).equals(int[].class)) {
                Object[] array = new Object[((int[]) value).length];
                int index = 0;
                for (int v : (int[]) value) {
                    array[index] = v;
                    index++;
                }
                return array;
            } else if (getTClass(value).equals(long[].class)) {
                Object[] array = new Object[((long[]) value).length];
                int index = 0;
                for (long v : (long[]) value) {
                    array[index] = v;
                    index++;
                }
                return array;
            } else if (getTClass(value).equals(float[].class)) {
                Object[] array = new Object[((float[]) value).length];
                int index = 0;
                for (float v : (float[]) value) {
                    array[index] = v;
                    index++;
                }
                return array;
            } else if (getTClass(value).equals(double[].class)) {
                Object[] array = new Object[((long[]) value).length];
                int index = 0;
                for (long v : (long[]) value) {
                    array[index] = v;
                    index++;
                }
                return array;
            } else if (getTClass(value).equals(boolean[].class)) {
                Object[] array = new Object[((boolean[]) value).length];
                int index = 0;
                for (boolean v : (boolean[]) value) {
                    array[index] = v;
                    index++;
                }
                return array;
            } else return (Object[]) value;
        }
        return new Object[0];
    }

    public static <T> Class<T> getTClass(T value) {
        return (Class<T>) value.getClass();
    }


    public static String toLines(List<String> lines) {
        StringBuilder lStr = new StringBuilder();
        int index = 0;
        if (lines != null)
            for (String line : lines) {
                if (line != null) {
                    lStr.append(line);
                    if (index < lines.size() - 1)
                        lStr.append("\n");
                }
                index++;
            }
        return lStr.toString();
    }

    public static String[] toStringArray(List<String> list) {
        return list.toArray(new String[0]);
    }

    public static String appendBefore(CharSequence source, CharSequence textToAppend, CharSequence before, int start) {
        if (source != null && before != null && textToAppend != null && start >= 0 && start < source.length() && source.toString().substring(start).contains(before)) {
            int index = source.toString().indexOf(before.toString(), start);

            if (index == 0)
                return textToAppend + source.toString();

            if (index == source.length() - 1)
                return source.toString() + textToAppend;

            return source.toString().substring(0, index) + textToAppend + source.toString().substring(index);
        }
        return source != null ? source.toString() : null;
    }

    public static <T> boolean isObject(Class<T> obj) {
        return !obj.equals(int.class) && !obj.equals(double.class) && !obj.equals(long.class) &&
                !obj.equals(Map.class) && !obj.equals(List.class) && !obj.equals(float.class) &&
                !obj.equals(boolean.class) && !obj.equals(String.class);
    }


    public static List<String> toLines(CharSequence lines) {
        List<String> list = new ArrayList<>();
        if (lines != null && lines.toString().contains("\n")) {
            int start = 0, end = 0;
            while (end > -1 && start < lines.length() - 1 && end < lines.length())
                end = lines.toString().indexOf("\n");
        } else if (lines != null) list.add(lines.toString());
        return list;
    }


    public static String tab(int count) {
        return count > 0 ? "\t".repeat(count) : "";
    }


    public static List<SubText> getSubTexts(CharSequence source, CharSequence sub) {
        List<SubText> subTexts = new ArrayList<>();
        int index = 0;
        if (source.toString().indexOf(sub.toString()) == 0)
            subTexts.add(new SubText(source, sub, 0));
        while (source.toString().substring(index + 1).contains(sub)) {
            index = source.toString().indexOf(sub.toString(), index);
            if (index == -1 || index >= source.length() - 1)
                break;
            subTexts.add(new SubText(source, sub, index));
            index += sub.length();
            if (index >= source.length() - 1 || !source.toString().substring(index).contains(sub))
                break;
        }
        return subTexts;
    }

    public static boolean isOnNewLine(SubText sub) {
        return lastLetterBefore(sub.source, sub.index) > -1 &&
                sub.source.substring(lastLetterBefore(sub.source, sub.index), sub.index).contains("\n");
    }

    public static int lastLetterBefore(String source, int index) {
        String text = source.length() > index && index > 0 ? source.substring(0, index) : null;
        if (text == null) return -1;
        for (int n = index - 1; n >= 0; n--) {
            char c = text.charAt(n);
            if (c != ' ' && c != '\n' && c != '\t') return n;
        }
        return -1;
    }

    public static int indexOf(String source, String text, int index, Position position) {
        return switch (position) {
            case After -> source.substring(0, index).lastIndexOf(text);
            case Before -> source.substring(index + 1).indexOf(text);
        };
    }

    public static final String NUMBERS = "0123456789";

    public static String toString(List<String> lines) {
        StringBuilder text = new StringBuilder();
        for (String line : lines)
            if (line != null)
                text.append(line).append("\n");
        return text.substring(0, text.lastIndexOf("\n"));
    }

    public static <O> List<String> toList(List<O> lines) {
        List<String> list = new ArrayList<>();
        for (O line : lines)
            if (line != null)
                list.add(line.toString());
        return list;
    }

    public static <D extends IData> String toDataString(List<D> lines) {
        StringBuilder text = new StringBuilder();
        for (D line : lines)
            if (line != null && line.isValid())
                text.append(line.get()).append("\n");
        return text.substring(0, text.lastIndexOf("\n"));
    }

    public static <D extends IData> List<String> toStringList(List<D> list) {
        List<String> texts = new ArrayList<>();
        for (D data : list)
            if (data != null && data.isValid())
                texts.add(data.get());
        return texts;
    }

    public static double nthRoot(double num, int nth) {
        double xPre = Math.random() * 10;
        double eps = 0.001;
        double delX = 2147483647;
        double xK = 0.0;

        while (delX > eps) {
            xK = ((nth - 1) * xPre + num / Math.pow(xPre, nth - 1)) / nth;
            delX = Math.abs(xK - xPre);
            xPre = xK;
        }
        return xK;
    }

    public static long nthRoot(int num, int nth) {
        return Math.round(nthRoot((double) num, nth));
    }

    public static double logN(double num, int nth) {
        return Math.log(num) / Math.log(nth);
    }

    public static int logN(int num, int nth) {
        return (int) logN((double) num, nth);
    }

    public static int toNearestWhole(double value, int numberOfZeros) {
        String text = (value + "");
        String whole = text.substring(0, text.indexOf("."));
        int digits = whole.length();

        String format;
        if (digits > numberOfZeros && numberOfZeros > 0) {
            if (digits - numberOfZeros > 1) {
                String main = whole.substring(0, digits - numberOfZeros);
                int last = Integer.parseInt(whole.charAt(digits - numberOfZeros) + "");
                // Main.println(last);
                if (last >= 5)
                    main = (main.length() > 1 ? main.substring(0, main.length() - 1) : main.charAt(0) + "") + (Integer.parseInt("" + main.charAt(main.length() - 1)) + 1);
                format = main + "0".repeat(numberOfZeros);
            } else {
                format = whole.charAt(0) + "0".repeat(numberOfZeros);
            }
            return Integer.parseInt(format);
        }
        return toNearestWhole(value);
    }

    public static int toNearestWhole(double value) {
        String text = value + "";

        String whole = text.substring(0, text.indexOf("."));
        String decimal = text.contains(".") ? text.substring(text.indexOf(".") + 1) : "";

        if (text.contains(".")) {
            int last = Integer.parseInt(decimal.charAt(0) + "");
            if (last >= 5)
                whole = (whole.length() > 1 ? whole.substring(0, whole.length() - 1) : whole.charAt(0) + "") + (Integer.parseInt(whole.charAt(whole.length() - 1) + "") + 1);
            return Integer.parseInt(whole);
        }
        return (int) value;
    }

    public static int toNearestTen(double value) {
        return toNearestWhole(value, 1);
    }

    public static int toNearestHundred(double value) {
        return toNearestWhole(value, 2);
    }

    public static int toNearestThousand(double value) {
        return toNearestWhole(value, 3);
    }

    public static int toNearestTenThousand(double value) {
        return toNearestWhole(value, 4);
    }

    public static int toNearestHundredThousand(double value) {
        return toNearestWhole(value, 5);
    }


    public static double toNDecimalPlace(double value, int decimalPlace) {
        if (decimalPlace == 0) return Math.round(value);
        int decimalDigits = String.valueOf(value).substring(String.valueOf(value).indexOf(".") + 1).length();
        if (decimalPlace < decimalDigits) {
            DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
            format.applyPattern("#." + "#".repeat(decimalPlace));
            try {
                return Double.parseDouble(format.format(value));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return value;
    }

    public static String repeat(String letter, int count) {
        if (letter == null) return null;
        if (count > 0 && !letter.isEmpty()) {
            StringBuilder repeat = new StringBuilder();
            int n = 0;
            while (n < count) {
                n++;
                repeat.append(letter);
            }
            return repeat.toString();
        }
        return "";
    }

    public static boolean isOdd(int number) {
        return !isEven(number);
    }

    public static boolean isEven(int number) {
        return Math.abs(number) % 2 == 0;
    }

    public static boolean isNumber(char value) {
        return NUMBERS.contains(value + "");
    }

    public static boolean isZero(char number) {
        return (number + "").equals("0");
    }

    public static boolean isDigit(char number) {
        return isNumber(number) && !isZero(number);
    }

    public static boolean isNumber(String value) {
        for (char c : value.toCharArray())
            if (!isNumber(c))
                return false;
        return !value.isEmpty();
    }

    public static int getIndexOfFirstDigit(double value) {
        long whole = getWholePortion(value);
        long decimal = getDecimalPortion(value);
        int index = getIndexOfFirstDigit(whole);
        if (index > -1) return digitCount(whole) - index - 1;
        index = getIndexOfFirstDigit(decimal);
        return index > -1 ? index - 2 : index;
    }

    public static int getIndexOfFirstDigit(long value) {
        for (int n = 0; n < digitCount(value); n++)
            if (isDigit((value + "").charAt(n)))
                return n;
        return -1;
    }

    public static long getWholePortion(double number) {
        return Long.parseLong((number + "").substring(0, (number + "").indexOf(".")));
    }

    public static long getDecimalPortion(double number) {
        String num = String.valueOf(number);
        int power;
        if (num.contains("-") && num.contains("E")) {
            power = Integer.parseInt(num.substring(num.indexOf("-") + 1));
            // System.out.println(power);
            num = repeat("0", power) + num.substring(num.indexOf(".") + 1, num.indexOf("-")).replace("E", "");
        } else
            num = num.substring((number + "").indexOf(".") + 1);
        // System.out.println("Num: " + num);
        return Long.parseLong(num);
    }

    public static int digitCount(long number) {
        return (number + "").length();
    }

    public static boolean containsDigit(long number) {
        return containsDigit((double) number);
    }

    public static boolean containsDigit(double number) {
        for (char c : (number + "").toCharArray())
            if (isDigit(c))
                return true;
        return false;
    }

    public static long roundOffValue(long number) {
        return number >= 5 ? 1 : 0;
    }

    public static String format(long number) {
        String val = number + "";
        String value = "";
        int len = val.length() - 1;
        int last;
        if (len > 2) {
            while (len > 3) {
                last = len;
                len -= 3;
                if (len >= 0)
                    value = "," + val.substring(len) + value;
                else
                    value = val.substring(0, last) + value;
                if (len < 3)
                    value = val.substring(0, last) + value;
            }
            return value;
        }
        return val;
    }

    public static long getCombination(int n, int r) {
        // n!/((n-r)! * r!) Where n >= r
        MinMax<Integer> m = getMinMax(n, r);
        // System.out.println(m);
        n = m.max;
        r = m.min;
        // perm = n * (n - 1) * (n - 2) * ... * (n - r) = n!/(n - r)!
        long perm = 1;
        for (int i = n; i > n - r; i--)
            perm *= i;
        return perm / getFactorial(r);
    }

    public static long getPermutation(int n, int r) {
        // n!/(n-r)! Where n >= r
        MinMax<Integer> m = getMinMax(n, r);
        // System.out.println(m);
        n = m.max;
        r = m.min;
        // System.out.println("n:" + n + ", r: " + r);
        return getFactorial(n) / getFactorial(n - r);
    }


    public static <N extends Number> MinMax<N> getMinMax(N a, N b) {
        return new MinMax<>(a, b);
    }

    public static long getFactorial(int n) {
        long fact = 1;
        n = Math.abs(n);
        if (n > 1) for (int i = 1; i <= n; i++) fact *= i;
        // System.out.println("Fact(" + n + ") = " + Math.abs(fact));
        return n >= 0 ? Math.abs(fact) : 0;
    }


    public static <N extends Number> N sum(N a, N b) {
        if (a != null && b != null)
            if (b instanceof Integer tB)
                return sum(a, tB);
            else if (b instanceof Double tB)
                return sum(a, tB);
            else if (b instanceof Float tB)
                return sum(a, tB);
            else if (b instanceof Long tB)
                return sum(a, tB);
        return null;
    }

    public static <N extends Number> N sum(N a, Integer b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (b + tA);
            else if (a instanceof Double tA)
                return (N) (Double) (b + tA);
            else if (a instanceof Float tA)
                return (N) (Float) (b + tA);
            else if (a instanceof Long tA)
                return (N) (Long) (b + tA);
        return null;
    }

    public static <N extends Number> N sum(N a, Long b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (b + tA);
            else if (a instanceof Double tA)
                return (N) (Double) (b + tA);
            else if (a instanceof Float tA)
                return (N) (Float) (b + tA);
            else if (a instanceof Long tA)
                return (N) (Long) (b + tA);
        return null;
    }

    public static <N extends Number> N sum(N a, Float b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (b + tA);
            else if (a instanceof Double tA)
                return (N) (Double) (b + tA);
            else if (a instanceof Float tA)
                return (N) (Float) (b + tA);
            else if (a instanceof Long tA)
                return (N) (Long) (long) (b + tA);
        return null;
    }

    public static <N extends Number> N sum(N a, Double b) {
        if (a != null && b != null) {
            if (a instanceof Integer tA) {
                return (N) (Integer) (int) (b + tA);
            } else if (a instanceof Double tA) {
                return (N) (Double) (b + tA);
            } else if (a instanceof Float tA) {
                return (N) (Float) (float) (b + tA);
            } else if (a instanceof Long tA) {
                return (N) (Long) (long) (b + tA);
            }
        }
        return null;
    }


    public static <N extends Number> N diff(N a, N b) {
        if (a != null && b != null)
            if (b instanceof Integer tB)
                return diff(a, tB);
            else if (b instanceof Double tB)
                return diff(a, tB);
            else if (b instanceof Float tB)
                return diff(a, tB);
            else if (b instanceof Long tB)
                return diff(a, tB);
        return null;
    }

    public static <N extends Number> N diff(N a, Integer b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (tA - b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA - b);
            else if (a instanceof Float tA)
                return (N) (Float) (tA - b);
            else if (a instanceof Long tA)
                return (N) (Long) (tA - b);
        return null;
    }

    public static <N extends Number> N diff(N a, Long b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (tA - b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA - b);
            else if (a instanceof Float tA)
                return (N) (Float) (tA - b);
            else if (a instanceof Long tA)
                return (N) (Long) (tA - b);
        return null;
    }

    public static <N extends Number> N diff(N a, Float b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (tA - b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA - b);
            else if (a instanceof Float tA)
                return (N) (Float) (tA - b);
            else if (a instanceof Long tA)
                return (N) (Long) (long) (tA - b);
        return null;
    }

    public static <N extends Number> N diff(N a, Double b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (tA - b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA - b);
            else if (a instanceof Float tA)
                return (N) (Float) (float) (tA - b);
            else if (a instanceof Long tA)
                return (N) (Long) (long) (tA - b);
        return null;
    }


    public static <N extends Number> N divide(N a, N b) {
        if (a != null && b != null)
            if (b instanceof Integer tB)
                return divide(a, tB);
            else if (b instanceof Double tB)
                return divide(a, tB);
            else if (b instanceof Float tB)
                return divide(a, tB);
            else if (b instanceof Long tB)
                return divide(a, tB);
        return null;
    }

    public static <N extends Number> N divide(N a, Integer b) {
        if (a != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (tA / b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA / b);
            else if (a instanceof Float tA)
                return (N) (Float) (float) (tA / b);
            else if (a instanceof Long tA)
                return (N) (Long) (long) (tA / b);
        return null;
    }

    public static <N extends Number> N divide(N a, Float b) {
        if (a != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (tA / b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA / b);
            else if (a instanceof Float tA)
                return (N) (Float) (float) (tA / b);
            else if (a instanceof Long tA)
                return (N) (Long) (long) (tA / b);
        return null;
    }

    public static <N extends Number> N divide(N a, Long b) {
        if (a != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (tA / b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA / b);
            else if (a instanceof Float tA)
                return (N) (Float) (float) (tA / b);
            else if (a instanceof Long tA)
                return (N) (Long) (long) (tA / b);
        return null;
    }

    public static <N extends Number> N divide(N a, Double b) {
        if (a != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (tA / b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA / b);
            else if (a instanceof Float tA)
                return (N) (Float) (float) (tA / b);
            else if (a instanceof Long tA)
                return (N) (Long) (long) (tA / b);
        return null;
    }

    public static <N extends Number> N divide(Integer a, N number) {
        if (number != null)
            if (number instanceof Integer tA)
                return (N) (Integer) (a / tA);
            else if (number instanceof Double tA)
                return (N) (Double) (a / tA);
            else if (number instanceof Float tA)
                return (N) (Float) (a / tA);
            else if (number instanceof Long tA)
                return (N) (Long) (a / tA);
        return null;
    }

    public static <N extends Number> N divide(Float a, N number) {
        if (number != null)
            if (number instanceof Integer tA)
                return (N) (Integer) (int) (a / tA);
            else if (number instanceof Double tA)
                return (N) (Double) (a / tA);
            else if (number instanceof Float tA)
                return (N) (Float) (a / tA);
            else if (number instanceof Long tA)
                return (N) (Long) (long) (a / tA);
        return null;
    }

    public static <N extends Number> N divide(Double a, N number) {
        if (number != null)
            if (number instanceof Integer tA)
                return (N) (Integer) (int) (a / tA);
            else if (number instanceof Double tA)
                return (N) (Double) (a / tA);
            else if (number instanceof Float tA)
                return (N) (Float) (float) (a / tA);
            else if (number instanceof Long tA)
                return (N) (Long) (long) (a / tA);
        return null;
    }

    public static <N extends Number> N divide(Long a, N number) {
        if (number != null)
            if (number instanceof Integer tA)
                return (N) (Integer) (int) (a / tA);
            else if (number instanceof Double tA)
                return (N) (Double) (a / tA);
            else if (number instanceof Float tA)
                return (N) (Float) (a / tA);
            else if (number instanceof Long tA)
                return (N) (Long) (a / tA);
        return null;
    }


    public static <N extends Number> N product(N a, N b) {
        if (a != null && b != null)
            if (b instanceof Integer tB)
                return product(a, tB);
            else if (b instanceof Double tB)
                return product(a, tB);
            else if (b instanceof Float tB)
                return product(a, tB);
            else if (b instanceof Long tB)
                return product(a, tB);
        return null;
    }

    public static <N extends Number> N product(N a, Integer b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (tA * b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA * b);
            else if (a instanceof Float tA)
                return (N) (Float) (tA * b);
            else if (a instanceof Long tA)
                return (N) (Long) (tA * b);
        return null;
    }

    public static <N extends Number> N product(N a, Long b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (tA * b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA * b);
            else if (a instanceof Float tA)
                return (N) (Float) (tA * b);
            else if (a instanceof Long tA)
                return (N) (Long) (tA * b);
        return null;
    }

    public static <N extends Number> N product(N a, Float b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (tA * b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA * b);
            else if (a instanceof Float tA)
                return (N) (Float) (tA * b);
            else if (a instanceof Long tA)
                return (N) (Long) (long) (tA * b);
        return null;
    }

    public static <N extends Number> N product(N a, Double b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return (N) (Integer) (int) (tA * b);
            else if (a instanceof Double tA)
                return (N) (Double) (tA * b);
            else if (a instanceof Float tA)
                return (N) (Float) (float) (tA * b);
            else if (a instanceof Long tA)
                return (N) (Long) (long) (tA * b);
        return null;
    }


    public static <N extends Number> double pow(N a, N b) {
        if (a != null && b != null)
            if (b instanceof Integer tB)
                return pow(a, tB);
            else if (b instanceof Double tB)
                return pow(a, tB);
            else if (b instanceof Float tB)
                return pow(a, tB);
            else if (b instanceof Long tB)
                return pow(a, tB);
        return 0;
    }

    public static <N extends Number> double pow(N a, Integer b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return Math.pow(tA, b);
            else if (a instanceof Double tA)
                return Math.pow(tA, b);
            else if (a instanceof Float tA)
                return Math.pow(tA, b);
            else if (a instanceof Long tA)
                return Math.pow(tA, b);
        return 0;
    }

    public static <N extends Number> double pow(N a, Long b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return Math.pow(tA, b);
            else if (a instanceof Double tA)
                return Math.pow(tA, b);
            else if (a instanceof Float tA)
                return Math.pow(tA, b);
            else if (a instanceof Long tA)
                return Math.pow(tA, b);
        return 0;
    }

    public static <N extends Number> double pow(N a, Float b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return Math.pow(tA, b);
            else if (a instanceof Double tA)
                return Math.pow(tA, b);
            else if (a instanceof Float tA)
                return Math.pow(tA, b);
            else if (a instanceof Long tA)
                return Math.pow(tA, b);
        return 0;
    }

    public static <N extends Number> double pow(N a, Double b) {
        if (a != null && b != null)
            if (a instanceof Integer tA)
                return Math.pow(tA, b);
            else if (a instanceof Double tA)
                return Math.pow(tA, b);
            else if (a instanceof Float tA)
                return Math.pow(tA, b);
            else if (a instanceof Long tA)
                return Math.pow(tA, b);
        return 0;
    }


    public static <N extends Number> N inverse(N number) {
        return divide(1, number);
    }


    public static <N extends Number> List<nx.peter.java.util.data.Number> toNumbers(List<N> numbers) {
        List<nx.peter.java.util.data.Number> result = new ArrayList<>();
        for (N number : numbers)
            result.add(new nx.peter.java.util.data.Number(number.toString()));
        return result;
    }


    public static void clearConsole() {
        // System.out.println("\033[H\033[2J");
        // System.out.flush();
        /*try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
                Runtime.getRuntime().exec("cls");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getClassName(Class<?> clazz) {
        String name = clazz.getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }


    public static class SubText {
        public String source, text;
        public int index;

        public SubText(CharSequence source, CharSequence text, int index) {
            this.source = source.toString();
            this.text = text.toString();
            this.index = index;
        }

        @Override
        public String toString() {
            return "{Text: '" + text + "', Index: " + index + "}";
        }
    }

    public record MinMax<N extends Number>(N min, N max) {
        public MinMax {
            N a = min, b = max;
            if (a != null && b != null) {
                if (a instanceof Integer tA) {
                    if (b instanceof Integer tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Double tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Float tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Long tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    }
                } else if (a instanceof Double tA) {
                    if (b instanceof Integer tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Double tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Float tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Long tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    }
                } else if (a instanceof Float tA) {
                    if (b instanceof Integer tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Double tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Float tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Long tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    }
                } else if (a instanceof Long tA) {
                    if (b instanceof Integer tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Double tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Float tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    } else if (b instanceof Long tB) {
                        Number tMax = Math.max(tA, tB);
                        Number tMin = Math.min(tA, tB);
                        max = (N) tMax;
                        min = (N) tMin;
                    }
                }
            }
        }

        public N diff() {
            return Util.diff(max, min);
        }

        public N sum() {
            return Util.sum(max, min);
        }

        public N divide() {
            return Util.divide(max, min);
        }

        public N product() {
            return Util.product(max, min);
        }

        public MinMax<Integer> toInteger() {
            return new MinMax<>(min.intValue(), max.intValue());
        }

        public MinMax<Double> toDouble() {
            return new MinMax<>(min.doubleValue(), max.doubleValue());
        }

        public MinMax<Long> toLong() {
            return new MinMax<>(min.longValue(), max.longValue());
        }

        public MinMax<Float> toFloat() {
            return new MinMax<>(min.floatValue(), max.floatValue());
        }

        public MinMax<N> getMinMax(N data) {
            if (data == null) return this;
            N min = new MinMax<>(this.min, data).min;
            N max = new MinMax<>(this.max, data).max;
            return new MinMax<>(min, max);
        }

        public MinMax<N> getMinMax(N min, N max) {
            return getMinMax(new MinMax<>(min, max));
        }

        public MinMax<N> getMinMax(MinMax<N> data) {
            if (data == null) return this;
            N min = data.getMinMax(this.min).min;
            N max = data.getMinMax(this.max).max;
            return new MinMax<>(min, max);
        }

        public boolean isEqual() {
            return Objects.equals(min, max);
        }

        public boolean isUnequal() {
            return !isEqual();
        }

        public boolean isInteger() {
            return max instanceof Integer;
        }

        public boolean isDouble() {
            return max instanceof Double;
        }

        public boolean isFloat() {
            return max instanceof Float;
        }

        public boolean isLong() {
            return max instanceof Long;
        }

        @Override
        public String toString() {
            return "[min=" + min + ", max=" + max + ']';
        }

        public boolean equals(MinMax<? extends Number> another) {
            return another != null && Objects.equals(min, another.min) && Objects.equals(max, another.max);
        }
    }

    public static class DataAnalyser<N extends Number> implements Iterable<N> {
        protected List<N> data;

        @SafeVarargs
        public DataAnalyser(N... data) {
            setData(data);
        }

        public DataAnalyser(List<N> data) {
            setData(data);
        }

        public void reset() {
            data = new ArrayList<>();
        }

        @SafeVarargs
        public final void setData(N... data) {
            setData(Arrays.asList(data));
        }

        public void setData(List<N> data) {
            this.data = new ArrayList<>();
            if (data != null)
                for (N d : data)
                    if (d != null)
                        this.data.add(d);
        }

        public boolean isEmpty() {
            return data.isEmpty();
        }

        public boolean isNotEmpty() {
            return !isEmpty();
        }

        public int getDataSize() {
            return data.size();
        }

        public MinMax<N> getMinMax() {
            return isNotEmpty() ? new MinMax<>(getSortedData().get(1), getSortedData().get(getDataSize())) : null;
        }

        public N getMedian() {
            return getSortedData().getMidData();
        }

        public N getMode() {
            return getRawData().getMode();
        }

        public N getRange() {
            return getMinMax().diff();
        }

        public double getMean() {
            // Sum of Data/Number of Data
            return getRawData().getAverage();
        }

        public DataCounts<N> getDataCounts() {
            return getRawData().getDataCount();
        }

        public Data<N> getRawData() {
            return new Data<>(data);
        }

        public Data<N> getSortedData() {
            List<N> temp = new ArrayList<>(data);
            temp.sort((a, b) -> {
                MinMax<Double> m = new MinMax<>(a.doubleValue(), b.doubleValue());
                return m.isEqual() ? 0 : Objects.equals(m.min, a.doubleValue()) ? -1 : 1;
            });
            return new Data<>(temp);
        }

        @Override
        public Iterator<N> iterator() {
            return data.iterator();
        }

        @Override
        public String toString() {
            return data.toString().replace("[", "").replace("]", "");
        }


        public static class Data<N extends Number> extends Document.Array<N> {
            public Data(List<N> data) {
                super(data);
            }

            public N get(int index) {
                return index > 0 && index <= size() ? items.get(index - 1) : null;
            }

            public int getMidPoint() {
                return isEmpty() ? -1 : (isEven(size()) ? size() : size() + 1) / 2;
            }

            public N getMidData() {
                return isEmpty() ? null : isOdd(size()) ? get(getMidPoint()) : Util.divide(Util.sum(get(getMidPoint()), get(getMidPoint() + 1)), 2);
            }

            public N getMode() {
                DataCount<N> mode = getModeCount();
                return mode != null ? mode.data : null;
            }

            public DataCount<N> getModeCount() {
                return getDataCount().getMode();
            }

            public int getMaxOccurrence() {
                DataCount<N> mode = getModeCount();
                return mode != null ? mode.count : 0;
            }

            public N getSum() {
                if (isNotEmpty()) {
                    int count = 1;
                    N sum = get(1);
                    for (N d : items) {
                        if (count > 1)
                            sum = Util.sum(sum, d);
                        count++;
                    }
                    return sum;
                }
                return null;
            }

            public double getAverage() {
                return getSum().doubleValue() / size();
            }

            public N getProduct() {
                if (isEmpty()) return null;
                if (size() > 1) {
                    int count = 1;
                    MinMax<N> m = new MinMax<>(items.get(0), items.get(1));
                    for (N d : items) {
                        if (count > 2)
                            m = new MinMax<>(m.product(), d);
                        count++;
                    }
                    return m.product();
                }
                return size() > 0 ? items.get(0) : null;
            }

            public DataCounts<N> getDataCount() {
                List<DataCount<N>> counts = new ArrayList<>();
                for (N number : getData())
                    counts.add(new DataCount<>(number, getCount(number)));
                return new DataCounts<>(counts);
            }

            private int getCount(N number) {
                int count = 0;
                for (N num : items)
                    if (num.equals(number)) count++;
                return count;
            }

            // Returns all data (without repeating an element)
            public Data<N> getData() {
                List<N> temp = new ArrayList<>();
                for (N d : items)
                    if (!temp.contains(d))
                        temp.add(d);
                return new Data<>(temp);
            }

            @Override
            public Iterator<N> iterator() {
                return items.iterator();
            }

            @Override
            public String toString() {
                return items.toString().replace("[", "").replace("]", "");
            }

        }

        public record DataCount<D extends Number>(D data, int count) implements Comparable<DataCount<D>> {

            public boolean equals(DataCount<?> other) {
                return other != null && equals(other.data()) && count == other.count;
            }

            public boolean equals(Number other) {
                return other != null && other.doubleValue() == data.doubleValue();
            }

            public D getSum() {
                return getData().getSum();
            }

            public D getProduct() {
                return getData().getProduct();
            }

            public Data<D> getData() {
                List<D> temp = new ArrayList<>();
                for (int i = 0; i < count; i++) temp.add(data);
                return new Data<>(temp);
            }

            @Override
            public String toString() {
                return data + "=>" + count;
            }

            @Override
            public int compareTo(DataCount other) {
                return String.valueOf(this).compareTo(String.valueOf(other));
            }
        }

        public static class DataCounts<D extends Number> extends Document.Array<DataCount<D>> {

            public DataCounts(List<DataCount<D>> count) {
                super(count);
            }

            public boolean contains(Number number) {
                for (DataCount<D> c : items)
                    if (c.data.doubleValue() == number.doubleValue()) return true;
                return false;
            }

            public int getCount(D number) {
                return getDataCount(number).count;
            }

            public DataCount<D> getDataCount(Number number) {
                for (DataCount<D> c : items)
                    if (c.data.doubleValue() == number.doubleValue())
                        return c;
                return new DataCount<>(toData(number), 0);
            }

            protected D toData(Number number) {
                if (isNotEmpty())
                    if (items.get(0).data instanceof Integer)
                        return (D) (Integer) number.intValue();
                    else if (items.get(0).data instanceof Double)
                        return (D) (Double) number.doubleValue();
                    else if (items.get(0).data instanceof Float)
                        return (D) (Float) number.floatValue();
                    else if (items.get(0).data instanceof Long)
                        return (D) (Long) number.longValue();
                return null;
            }

            public int indexOf(Number number) {
                DataCount<D> count = getDataCount(number);
                return count != null ? indexOf(count) : -1;
            }

            public DataCount<D> getMode() {
                DataCount<D> mode = null;
                int max = 0;
                for (DataCount<D> c : items)
                    if (Math.max(max, c.count) == c.count) {
                        mode = c;
                        max = c.count;
                    }
                return mode;
            }

        }
    }

    public record TrimmedLine(String line, String trimmed) {
    }

    public enum Position {
        After, Before
    }

}
