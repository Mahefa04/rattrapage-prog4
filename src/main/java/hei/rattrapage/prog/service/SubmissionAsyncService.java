package hei.rattrapage.prog.service;

import hei.rattrapage.prog.file.image.ImageResizer;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionAsyncService {

    private final ImageResizer imageResizer;

    @Async
    public void processSubmission(byte[] imageBytes) {
        try {
            log.info(
                    "Traitement asynchrone démarré sur le thread {}",
                    Thread.currentThread().getName());

            byte[] thumbnail = imageResizer.resize(imageBytes);

            log.info("Thumbnail générée : {} bytes", thumbnail.length);

            // S3 et mise à jour de la Submission ensuite.
        } catch (IOException e) {
            log.error("Erreur pendant la génération de la thumbnail", e);
        }
    }
}