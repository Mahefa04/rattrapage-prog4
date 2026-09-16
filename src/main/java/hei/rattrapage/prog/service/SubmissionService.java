package hei.rattrapage.prog.service;

import hei.rattrapage.prog.model.Submission;
import hei.rattrapage.prog.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    public Submission createSubmission(String email) {
        Submission submission = new Submission(email);
        return submissionRepository.save(submission);
    }
}