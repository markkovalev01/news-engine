package app.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity extends AbstractEntity{



    String title;
    String previewImage;
    String annotation;
    String content;

    @Column(columnDefinition = "boolean default false")
    boolean archive;


    LocalDateTime created;
    @ManyToOne(fetch = FetchType.LAZY)
    UserEntity createdBy;

    LocalDateTime updated;
    @ManyToOne(fetch = FetchType.LAZY)
    UserEntity updatedBy;

    @Override
    public Long getId() {
        return super.getId();
    }
}
