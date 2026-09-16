package hei.rattrapage.prog.repository;

import hei.rattrapage.prog.model.Submission;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {}