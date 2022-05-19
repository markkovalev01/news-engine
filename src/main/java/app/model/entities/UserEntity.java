package app.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends AbstractEntity {

    @Column(unique = true)
    String login;
    String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy")
    List<NewsEntity> created;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "updatedBy")
    List<NewsEntity> updated;

}
