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
    private String filePath;
    private String fileNameGate;
    private String language;
    private String parsed;
    private String parsedStatus;
    private CV extractedData;
    private String extractedStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filed filed = (Filed) o;
        if (getFilePath().equals(filed.getFilePath())) return true;

        return super.equals(o) && getFilePath().equals(filed.getFilePath());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getFilePath().hashCode();
        return result;
    }
}
