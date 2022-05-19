package app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreviewNewsDTO extends AbstractDto {

    String title;
    String previewImage;
    String annotation;

    long created;
    long updated;


}
