package com.qodara.virtual_store.shared.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Value("${FIREBASE_CREDENTIALS}")
    private String firebaseCredentials;
    @Value("${BUCKET_NAME}")
    private String bucketName;

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        if (firebaseCredentials == null || firebaseCredentials.isEmpty()) {
            throw new IllegalArgumentException("Firebase credentials are not configured.");
        }

        ByteArrayInputStream serviceAccountStream = new ByteArrayInputStream(firebaseCredentials.getBytes());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .setStorageBucket(bucketName)
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
