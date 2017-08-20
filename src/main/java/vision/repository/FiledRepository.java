package vision.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import rx.subjects.PublishSubject;
import vision.models.Filed;

import java.io.File;
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

    public Filed findFiledByPath(String path) {
        for (Filed f : filedList) {
            if (f.getFile().getPath().equals(path)) {
                return f;
            }
        }
        return null;
    }

    public void addNewFiled(File file, String language, String parsed, String parsedStatus, String extracted, String extractedStatus) {
        Filed filed = new Filed(file, language, parsed, parsedStatus, extracted, extractedStatus);
        filedList.add(filed);
        onAdd.onNext(filed);
    }

    public void addNewFiled(Filed filed) {
        filedList.add(filed);
        onAdd.onNext(filed);
    }

}
