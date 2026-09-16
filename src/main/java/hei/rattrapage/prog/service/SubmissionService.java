package hei.rattrapage.prog.service;

import hei.rattrapage.prog.model.Submission;
import hei.rattrapage.prog.repository.SubmissionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SubmissionAsyncService submissionAsyncService;

    public Submission createSubmission(String email) {
        Submission submission = new Submission(email);

        Submission savedSubmission = submissionRepository.save(submission);

        submissionAsyncService.processSubmission();

        return savedSubmission;
    }

    public List<Submission> listSubmissions() {
        return submissionRepository.findAll();
    }
}