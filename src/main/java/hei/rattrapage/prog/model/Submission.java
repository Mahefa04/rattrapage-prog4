package hei.rattrapage.prog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "submissions")
@Getter
@NoArgsConstructor
public class Submission {

    @Id
    private UUID id;

    private String email;

    private String thumbnailKey;

    private Instant createdAt;

    public Submission(String email) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.thumbnailKey = null;
        this.createdAt = Instant.now();
    }

    public void updateThumbnailKey(String thumbnailKey) {
        this.thumbnailKey = thumbnailKey;
    }
}