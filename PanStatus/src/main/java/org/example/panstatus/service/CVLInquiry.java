package org.example.panstatus.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CVLInquiry {
    public static String requestPan(String encryptedPanData, String EncryptedCVLToken) {
        RestTemplate restTemplate = new RestTemplate();
        String Url = "https://api.kracvl.com/int/api/GetPanStatus";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token",EncryptedCVLToken);
        HttpEntity<String> entity = new HttpEntity<String>(encryptedPanData, headers);
        ResponseEntity<String> response = restTemplate.exchange(Url, HttpMethod.POST, entity, String.class);
    return response.getBody().substring(1, response.getBody().length() - 1);
    }
}
