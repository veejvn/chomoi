package com.ecommerce.chomoi.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class EmailTemplateUtil {

    //placeholders: danh sach key: value can thay the, filePath: duong dan file can thay the
    public String replaceValueInEmailTemplate(Map<String, String> placeholders, String filePath){
        try{
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Thay thế tất cả các placeholder bằng giá trị thực tế
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                content = content.replace(entry.getKey(), entry.getValue());
            }
            return content;
        }catch (IOException e){
            throw new RuntimeException("Error reading email template file", e);
        }
    }

    public String emailTemplate (String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Error reading email template file", e);
        }
    }
}
