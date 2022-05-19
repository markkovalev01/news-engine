package app.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntity extends AbstractEntity{

    String action;

    @OneToOne(fetch = FetchType.LAZY)
    UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    NewsEntity news;
}
