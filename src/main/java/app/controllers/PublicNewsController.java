package app.controllers;

import app.model.dto.NewsDTO;
import app.model.dto.PreviewNewsDTO;
import app.model.mapper.NewsMapper;
import app.model.mapper.PreviewNewsMapper;
import app.services.NewsService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class PublicNewsController {


    @Autowired
    PreviewNewsMapper previewNewsMapper;
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    NewsService newsService;

    @GetMapping
    public List<PreviewNewsDTO> getPreviewNews() {
        return newsService.getActualNews().stream().map(news -> {
            return previewNewsMapper.toDto(news);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NewsDTO getNews(@PathVariable Long id) {
        return newsMapper.toDto(newsService.getActualNews(id));
    }
}
