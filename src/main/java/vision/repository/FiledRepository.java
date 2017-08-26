package vision.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import rx.subjects.PublishSubject;
import vision.models.CV;
import vision.models.Filed;
import vision.utils.CommonUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Yuriy on 20.08.2017.
 */
@Repository
public class FiledRepository {
    @Getter
    private final ArrayList<Filed> filedList;
    @Getter
    private final PublishSubject<Filed> onAdd;

    public FiledRepository() {
        this.filedList = new ArrayList<>();
        this.onAdd = PublishSubject.create();
    }

    public Filed getFiledByPath(String path) {
        for (Filed f : filedList) {
            URL fURL = CommonUtils.getFileUrl(f.getFile());
            if (fURL != null) {
                if (fURL.getPath().equals(path)) {
                    return f;
                }
            }
        }
        return null;
    }

    public void addNewFiled(File file, String language, String parsed, String parsedStatus, CV extracted, String extractedStatus) {
        Filed filed = new Filed(file, file.getPath(), language, parsed, parsedStatus, extracted, extractedStatus);
        filedList.add(filed);
        onAdd.onNext(filed);
    }

    public void addNewFiled(Filed filed) {
        filedList.add(filed);
        onAdd.onNext(filed);
    }


}
