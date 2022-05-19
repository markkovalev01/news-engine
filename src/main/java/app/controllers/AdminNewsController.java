package app.controllers;


import app.model.dto.NewsDTO;
import app.model.dto.NewsViewDTO;
import app.model.entities.NewsEntity;
import app.model.mapper.NewsMapper;
import app.model.mapper.NewsViewMapper;
import app.services.NewsService;
import app.services.UserService;
import lombok.SneakyThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/admin/news")
public class AdminNewsController implements DefaultController {

    @Autowired
    NewsService newsService;
    @Autowired
    NewsViewMapper newsViewMapper;
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;


    @GetMapping()
    List<NewsViewDTO> getAllNews() {
        return newsService.getAll().stream().map(newsEntity -> {
            return newsViewMapper.toDto(newsEntity);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    NewsDTO getNews(@PathVariable Long id) {
        return newsMapper.toDto(newsService.get(id));
    }

    @PostMapping
    @SneakyThrows
    NewsDTO addNews(@RequestParam("dto") String newsDTO,
                    @RequestParam(value = "preview", required = false) MultipartFile image,
                    Authentication authentication) {
        NewsDTO news = objectMapper.readValue(newsDTO, NewsDTO.class);
        NewsEntity newsEntity = newsMapper.toEntity(news);
        newsEntity.setCreatedBy(userService.getByLogin(authentication.getName()));
        return newsMapper.toDto(newsService.createWithImage(newsEntity, image));
    }


    @PutMapping
    @SneakyThrows
    NewsDTO updateNews(@RequestParam("dto") String newsDTO,
                       @RequestParam(value = "preview", required = false) MultipartFile image,
                       Authentication authentication) {
        NewsDTO news = objectMapper.readValue(newsDTO, NewsDTO.class);
        NewsEntity newsEntity = newsMapper.toEntity(news);
        newsEntity.setUpdatedBy(userService.getByLogin(authentication.getName()));
        return newsMapper.toDto(newsService.updateWithImage(newsEntity, image));
    }

    @DeleteMapping("/{id}")
    public List<NewsViewDTO> archiveNews(@PathVariable Long id) {
        return newsService.delete(id).stream().map(entity -> {
            return newsViewMapper.toDto(entity);
        }).collect(Collectors.toList());
    }

}
