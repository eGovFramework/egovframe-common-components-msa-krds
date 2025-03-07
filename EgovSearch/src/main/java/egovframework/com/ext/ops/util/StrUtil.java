package egovframework.com.ext.ops.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StrUtil {

    public static List<String> readWordsFromFile(String filePath) {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            log.error("##### StrUtil IOException >>> {}", e.getMessage());
        }
        return words;
    }

    public static String cleanString(String input) {
        if (ObjectUtils.isEmpty(input)) {
            return null;
        }

        return input.replace("\r", " ")
                .replace("\n", " ")
                .replace("\\", " ")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&nbsp;", " ")
                .replace("&#39;", "'")
                .replace("&#34;", "\"")
                .replace("<br>", "\n")
                .replace("<br/>", "\n")
                .replace("<br />", "\n")
                .replace("<p>", "")
                .replace("</p>", "");
    }

}
