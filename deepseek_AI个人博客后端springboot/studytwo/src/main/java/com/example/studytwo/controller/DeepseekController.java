
package com.example.studytwo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api/deepseek")
public class DeepseekController {

    private static final String API_KEY = "";//自己去deepseek申请apikey，充10元，放在这里

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter streamChat(@RequestBody Map<String, Object> payload, HttpServletResponse response) {
        // ✅ 设置超时时间为 2 分钟
        ResponseBodyEmitter emitter = new ResponseBodyEmitter(120_000L);

        new Thread(() -> {
            try {
                URL url = new URL("https://api.deepseek.com/chat/completions");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
                conn.setRequestProperty("Accept", "text/event-stream"); // 建议加上
                conn.setDoOutput(true);
                conn.setChunkedStreamingMode(0); // 开启 chunked 流式输出

                payload.put("stream", true);
                OutputStream os = conn.getOutputStream();
                os.write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsBytes(payload));
                os.flush();
                os.close();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                );
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("data:")) {
                        String content = line.substring(5).trim();
                        if ("[DONE]".equals(content)) break;

                        emitter.send("data:" + content + "\n\n");
                    }
                }

                emitter.complete();
                reader.close();
            } catch (Exception e) {
                try {
                    emitter.send("error:" + e.getMessage());
                    emitter.completeWithError(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();

        return emitter;
    }
}
