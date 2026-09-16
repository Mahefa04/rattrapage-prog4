package hei.rattrapage.prog.service;

import hei.rattrapage.prog.file.image.ImageResizer;
import hei.rattrapage.prog.file.storage.S3FileStorage;
import hei.rattrapage.prog.model.Submission;
import hei.rattrapage.prog.repository.SubmissionRepository;
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
    private final S3FileStorage s3FileStorage;
    private final SubmissionRepository submissionRepository;

    @Async
    public void processSubmission(Submission submission, byte[] imageBytes) {
        try {
            log.info(
                    "Traitement asynchrone de {} sur le thread {}",
                    submission.getId(),
                    Thread.currentThread().getName());

            byte[] thumbnail = imageResizer.resize(imageBytes);

            String key =
                    "submissions/" + submission.getId() + "/thumbnail.jpg";

            s3FileStorage.upload(key, thumbnail, "image/jpeg");

            submission.updateThumbnailKey(key);

            submissionRepository.save(submission);

            log.info("Thumbnail uploadée avec succès : {}", key);

        } catch (IOException e) {
            log.error(
                    "Erreur pendant le traitement de la submission {}",
                    submission.getId(),
                    e);
        }
    }
}