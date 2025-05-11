package com.bfhl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@SpringBootApplication
public class BfhlWebhookApp implements CommandLineRunner {

    private static final String INIT_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String SUBMIT_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

    private static final String NAME = "John Doe";
    private static final String REG_NO = "REG12347";
    private static final String EMAIL = "john@example.com";

    public static void main(String[] args) {
        SpringApplication.run(BfhlWebhookApp.class, args);
    }

    @Override
    public void run(String... args) {
        RestTemplate restTemplate = new RestTemplate();

        // Step 1: Generate webhook
        InitRequest initRequest = new InitRequest(NAME, REG_NO, EMAIL);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InitRequest> entity = new HttpEntity<>(initRequest, headers);
        ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(INIT_URL, entity, WebhookResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            WebhookResponse resp = response.getBody();
            System.out.println("Received webhook: " + resp.webhookUrl);
            System.out.println("Received token: " + resp.accessToken);

            // Step 2: Solve the SQL (You must replace this with actual solution)
            String sqlSolution = "SELECT * FROM users WHERE status = 'active';"; // Replace with actual solution

            // Step 3: Submit solution
            SubmitRequest submitRequest = new SubmitRequest(sqlSolution);
            HttpHeaders authHeaders = new HttpHeaders();
            authHeaders.setContentType(MediaType.APPLICATION_JSON);
            authHeaders.setBearerAuth(resp.accessToken);

            HttpEntity<SubmitRequest> submitEntity = new HttpEntity<>(submitRequest, authHeaders);
            ResponseEntity<String> submitResponse = restTemplate.postForEntity(SUBMIT_URL, submitEntity, String.class);

            System.out.println("Submission status: " + submitResponse.getStatusCode());
            System.out.println("Response: " + submitResponse.getBody());
        } else {
            System.err.println("Failed to initialize webhook.");
        }
    }

    static class InitRequest {
        public String name;
        public String regNo;
        public String email;

        public InitRequest(String name, String regNo, String email) {
            this.name = name;
            this.regNo = regNo;
            this.email = email;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class WebhookResponse {
        @JsonProperty("webhookUrl")
        public String webhookUrl;

        @JsonProperty("accessToken")
        public String accessToken;
    }

    static class SubmitRequest {
        @JsonProperty("finalQuery")
        public String finalQuery;

        public SubmitRequest(String finalQuery) {
            this.finalQuery = finalQuery;
        }
    }
}
