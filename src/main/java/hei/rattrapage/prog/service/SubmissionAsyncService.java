package hei.rattrapage.prog.service;

import hei.rattrapage.prog.file.image.ImageResizer;
import hei.rattrapage.prog.file.storage.S3FileStorage;
import hei.rattrapage.prog.mail.Email;
import hei.rattrapage.prog.mail.Mailer;
import hei.rattrapage.prog.model.Submission;
import hei.rattrapage.prog.repository.SubmissionRepository;
import jakarta.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.List;
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
    private final Mailer mailer;

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

            String downloadUrl = s3FileStorage.generateDownloadUrl(key);

            sendEmail(submission, downloadUrl);

            log.info(
                    "Traitement terminé pour la submission {}",
                    submission.getId());

        } catch (IOException e) {
            log.error(
                    "Erreur pendant le traitement de la submission {}",
                    submission.getId(),
                    e);
        }
    }

    private void sendEmail(Submission submission, String downloadUrl) {
        try {
            Email email =
                    new Email(
                            new InternetAddress(submission.getEmail()),
                            List.of(),
                            List.of(),
                            "Votre miniature est prête",
                            "<p>Votre miniature a été générée.</p>"
                                    + "<p><a href=\""
                                    + downloadUrl
                                    + "\">Télécharger la miniature</a></p>",
                            List.of());

            mailer.accept(email);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}