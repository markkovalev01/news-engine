package app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO extends AbstractDto {

    String title;
    String annotation;
    String content;

    boolean archive;

    long created;

    long updated;
}
