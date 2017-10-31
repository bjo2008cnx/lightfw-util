package org.lightfw.utilx.web.request;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * request wrapper: 可重复读取request.getInputStream
 */
@Log4j2
public class RepeatableReadWrapper extends HttpServletRequestWrapper {

    private static final int BUFFER_START_POSITION = 0;

    private static final int CHAR_BUFFER_LENGTH = 1024;

    /**
     * input stream 的buffer
     */
    @Getter
    @Setter
    private final String body;

    /**
     * @param request {@link javax.servlet.http.HttpServletRequest} object.
     */
    public RepeatableReadWrapper(HttpServletRequest request) {
        super(request);

        StringBuilder stringBuilder = new StringBuilder();

        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            log.error("Error reading the request body…", e);
        }
        if (inputStream != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                char[] charBuffer = new char[CHAR_BUFFER_LENGTH];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, BUFFER_START_POSITION, bytesRead);
                }
            } catch (IOException e) {
                log.error("Fail to read input stream", e);
            }
        } else {
            stringBuilder.append("");
        }
        body = stringBuilder.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new CustomServletInputStream(byteArrayInputStream);
    }

    /**
     * 带本地buffer的InputStream
     */
    private static class CustomServletInputStream extends ServletInputStream {
        /**
         * 本地buffer
         */
        private ByteArrayInputStream byteArrayInputStream;

        /**
         * @param byteArrayInputStream {@link ByteArrayInputStream} object.
         */
        CustomServletInputStream(ByteArrayInputStream byteArrayInputStream) {
            this.byteArrayInputStream = byteArrayInputStream;
        }

        @Override
        public int read() throws IOException {
            return byteArrayInputStream.read();
        }

        public boolean isFinished() {
            return false;
        }

        public boolean isReady() {
            return false;
        }

        public void setReadListener(ReadListener readListener) {

        }
    }
}