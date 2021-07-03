package tools.mtsuite.core.core.service;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import tools.mtsuite.core.core.excepctions.BadRequestException;
import tools.mtsuite.core.endpoint.files.dto.FileDto;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Base64;

@Service
public class HttpClientService {

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public <T> ResponseEntity<T> post(String url, String body, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        ResponseEntity<T> result = restTemplate.postForEntity(url, entity, responseType);
        return result;
    }

    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public <T> ResponseEntity<T> postUploadAttachment(String url, FileDto fileDto, Class<T> responseType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // This nested HttpEntiy is important to create the correct
        // Content-Disposition entry with metadata "name" and "filename"
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(fileDto.getName())
                .build();

        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        byte[] fileBytes;
        fileBytes = Base64.getDecoder().decode(fileDto.getContent().split(",")[1]);
      //  ByteArrayInputStream byteStream = new ByteArrayInputStream(fileBytes);

        HttpEntity<byte[]> fileEntity = new HttpEntity<>(fileBytes, fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<T> result = restTemplate.postForEntity(url, entity, responseType);
        return result;
    }

    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<T> result = restTemplate.exchange(url, HttpMethod.GET, entity,responseType);
        return result;
    }

    public void put() {
    }

    public void delete() {
    }

}
