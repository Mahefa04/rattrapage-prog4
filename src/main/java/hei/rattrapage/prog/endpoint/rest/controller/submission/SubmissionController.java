package hei.rattrapage.prog.endpoint.submission;

import hei.rattrapage.prog.model.Submission;
import hei.rattrapage.prog.service.SubmissionService;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class SubmissionController {

  private final SubmissionService submissionService;

  @PostMapping(
          value = "/submissions",
          consumes = "multipart/form-data",
          produces = "application/json")
  public ResponseEntity<Submission> createSubmission(
          @RequestParam("file") MultipartFile file,
          @RequestParam("email") String email) throws IOException {

    byte[] imageBytes = file.getBytes();

    Submission submission =
            submissionService.createSubmission(email, imageBytes);

    return ResponseEntity.status(HttpStatus.CREATED).body(submission);
  }

  @GetMapping(value = "/submissions", produces = "application/json")
  public List<Submission> listSubmissions() {
    return submissionService.listSubmissions();
  }
}
