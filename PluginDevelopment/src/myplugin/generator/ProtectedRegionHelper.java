package myplugin.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProtectedRegionHelper {

    private static final String START =
            "// <protected region name=\"custom\">";

    private static final String END =
            "// </protected region>";

    public static String preserveProtectedRegion(
            File existingFile,
            String generatedContent) throws IOException {

        if (!existingFile.exists()) {
            return generatedContent;
        }

        String oldContent = readFile(existingFile);

        int startIndex = oldContent.indexOf(START);
        int endIndex = oldContent.indexOf(END);

        if (startIndex == -1 || endIndex == -1 || endIndex < startIndex) {
            return generatedContent;
        }

        startIndex += START.length();

        String protectedContent =
                oldContent.substring(startIndex, endIndex);

        int newStartIndex = generatedContent.indexOf(START);
        int newEndIndex = generatedContent.indexOf(END);

        if (newStartIndex == -1 ||
            newEndIndex == -1 ||
            newEndIndex < newStartIndex) {

            return generatedContent;
        }

        newStartIndex += START.length();

        return generatedContent.substring(0, newStartIndex)
                + protectedContent
                + generatedContent.substring(newEndIndex);
    }

    private static String readFile(File file) throws IOException {

        StringBuilder content = new StringBuilder();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }

        reader.close();

        return content.toString();
    }
}