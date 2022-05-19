package app.repositories;

import app.model.entities.NewsEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface NewsRepository extends DefaultRepository<NewsEntity, Long>{


    @Query("select n from NewsEntity n where n.archive=false")
    public List<NewsEntity> findActualNews();
}
