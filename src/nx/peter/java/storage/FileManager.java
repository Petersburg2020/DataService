package nx.peter.java.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static List<String> readLines(CharSequence path) {
        try {
            return readLines(new FileInputStream(path.toString()));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<String> readLines(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<String> lines = new ArrayList<>();
        try {
            String line;
            while ((line = reader.readLine()) != null)
                lines.add(line);
            reader.close();
            stream.close();
            return lines;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return lines;
        }
    }

    public static String readString(InputStream stream) {
        List<String> lines = readLines(stream);
        StringBuilder str = new StringBuilder();
        int index = 0;
        for (String line : lines) {
            str.append(line);
            if (index < lines.size() - 1)
                str.append("\n");
            index++;
        }
        return str.toString();
    }

    public static String readString(CharSequence path) {
        if (isFile(path)) {
            try {
                return readString(new FileInputStream(path.toString()));
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return "";
    }


    public static boolean isDirectory(CharSequence path) {
        return getFile(path).isDirectory();
    }

    public static boolean isFile(CharSequence path) {
        return getFile(path).isFile();
    }

    public static File getFile(CharSequence path) {
        return new File(path != null ? path.toString().trim() : "");
    }


    public static boolean writeFile(CharSequence path, List<String> lines, boolean append) {
        StringBuilder line = new StringBuilder();
        int index = 0;
        for (String s : lines) {
            line.append(s);
            if (index < lines.size() - 1)
                line.append("\n");
            index++;
        }
        return writeFile(path, line, append);
    }

    public static boolean writeFile(CharSequence path, CharSequence lines, boolean append) {
        try {
            return writeStream(new FileOutputStream(path.toString(), append), lines);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean writeStream(OutputStream stream, List<String> lines) {
        StringBuilder line = new StringBuilder();
        int index = 0;
        for (String s : lines) {
            line.append(s);
            if (index < lines.size() - 1)
                line.append("\n");
            index++;
        }
        return writeStream(stream, line);
    }

    public static boolean writeStream(OutputStream stream, CharSequence lines) {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
        try {
            writer.write(lines.toString());
            writer.flush();
            writer.close();
            stream.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static boolean fileExists(CharSequence path) {
        return new File(path).exists();
    }
}
