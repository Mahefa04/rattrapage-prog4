package hei.rattrapage.prog.repository;

import hei.rattrapage.prog.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}