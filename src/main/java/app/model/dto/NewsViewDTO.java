package app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsViewDTO extends AbstractDto{

    String title;
    long created;
    long updated;
    String creator;
    String updater;
}
