package hei.rattrapage.prog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SubmissionAsyncService {

    @Async
    public void processSubmission(byte[] imageBytes) {
        log.info(
                "Traitement asynchrone démarré sur le thread {}",
                Thread.currentThread().getName());

        // Resize, S3 et email seront ajoutés ensuite.
    }
}