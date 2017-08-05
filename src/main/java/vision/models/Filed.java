package vision.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author Yuriy on 04.08.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filed {
    private File file;
    private String parsed;
    private String parsedStatus;
    private String extracted;
    private String extractedStatus;
}
