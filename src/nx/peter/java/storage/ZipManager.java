package nx.peter.java.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipManager {

    public static boolean zipAFile(CharSequence sourceFilePath, CharSequence zippedFilePath) {
        sourceFilePath = sourceFilePath != null ? sourceFilePath : "";
        try {
            zippedFilePath = zippedFilePath != null ?
                    !zippedFilePath.toString().endsWith(".zip") ?
                            zippedFilePath + ".zip" : !zippedFilePath.isEmpty() ? zippedFilePath : !sourceFilePath.isEmpty() ?
                    new File(sourceFilePath).getNameWithoutExtension() + ".zip" : "" : "";
            // Output zip
            ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zippedFilePath.toString()));
            // Source file
            File file = new File(sourceFilePath);
            FileInputStream in = new FileInputStream(file);

            ZipEntry entry = new ZipEntry(file.getName());

            zip.putNextEntry(entry);
            byte[] bytes = new byte[1024];

            int length;
            while ((length = in.read(bytes)) > 0)
                zip.write(bytes, 0, length);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
