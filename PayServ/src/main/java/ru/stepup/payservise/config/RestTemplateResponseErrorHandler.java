package ru.stepup.payservise.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import ru.stepup.payservise.exceptions.IntegrationException;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {
        return httpResponse.getStatusCode().is4xxClientError() || httpResponse.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        // нужно отформаировать сообщение!
        String body = StreamUtils.copyToString(httpResponse.getBody(), Charset.defaultCharset());
        //             AddDefaultCharsetFilter.ResponseWrapper responseWrapper = objectMapper.readValue(body, AddDefaultCharsetFilter.ResponseWrapper.class);
        log.info("######## httpResponse.getBody().toString() = " + body + ";\n getStatusText() = " + httpResponse.getStatusText() + ";\n getStatusCode() = " + httpResponse.getStatusCode());

        if (httpResponse.getStatusCode().is4xxClientError()) {
            throw new IntegrationException(body, (HttpStatus) httpResponse.getStatusCode());
        } else if (httpResponse.getStatusCode().is5xxServerError()) {
            throw new IntegrationException(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
