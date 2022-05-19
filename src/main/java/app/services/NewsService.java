package app.services;

import app.exceptions.ConflictException;
import app.model.entities.JournalEntity;
import app.model.entities.NewsEntity;
import app.model.entities.UserEntity;
import app.repositories.NewsRepository;
import app.utils.FileUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NewsService extends AbstractService<NewsRepository, NewsEntity> {

    @Autowired
    JournalService journalService;
    @Autowired
    UserService userService;

    @Autowired
    FileUtil fileUtil;


    @Transactional
    @Override
    public NewsEntity create(NewsEntity entity) {
        entity.setCreated(LocalDateTime.now());
        NewsEntity news = super.create(entity);
        journalService.create(new JournalEntity("News created", news.getCreatedBy(), news));
        return news;
    }

    @Transactional
    @Override
    public NewsEntity update(NewsEntity entity) {
        entity.setUpdated(LocalDateTime.now());
        NewsEntity news = update(entity);
        journalService.create(new JournalEntity("News updated", news.getUpdatedBy(), news));
        return news;
    }


    public List<NewsEntity> getActualNews() {
        return repository.findActualNews();
    }

    public NewsEntity getActualNews(Long id) {
        NewsEntity news = get(id);
        if (news.isArchive()) {
            throw new ConflictException("News in archive");
        }
        return news;
    }

    @Transactional(rollbackFor = {IOException.class})
    public NewsEntity createWithImage(NewsEntity entity, MultipartFile image) {
        NewsEntity news = create(entity);
        if (Objects.nonNull(image)) {
            String fileName = String.format("%s.%s", news.getId(), fileUtil.getExtension(image));
            news.setPreviewImage(fileName);
            fileUtil.save(image, fileName);
        }
        return save(news);
    }

    @Transactional(rollbackFor = {IOException.class})
    public NewsEntity updateWithImage(NewsEntity entity, MultipartFile image) {
        NewsEntity news = update(entity);
        if(Objects.isNull(image) || fileUtil.compareFiles(image, news.getPreviewImage())){
            return news;
        }
        if (Objects.nonNull(image)) {
            String fileName = String.format("%s.%s", news.getId(), fileUtil.getExtension(image));
            news.setPreviewImage(fileName);
            fileUtil.save(image, fileName);
        }
        return save(news);
    }

    @Override
    @Transactional
    public List<NewsEntity> delete(Long id) {
        NewsEntity news = get(id);
        news.setArchive(true);
        save(news);
        return getAll();
    }
}
