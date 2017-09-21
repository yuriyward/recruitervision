package vision.repository;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import rx.subjects.PublishSubject;
import vision.models.CV;
import vision.models.Filed;
import vision.utils.CommonUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Yuriy on 20.08.2017.
 */
@Repository
public class FiledRepository {
    @Getter
    private final ArrayList<Filed> filedList;
    @Getter
    private final PublishSubject<Filed> onAdd;
    @Getter
    private final PublishSubject<Filed> onRemove;
    @Getter
    private final PublishSubject<Filed> onClear;
    @Getter
    private final PublishSubject<Filed> onRefresh;

    private final static Logger logger = LoggerFactory.getLogger(FiledRepository.class);


    public FiledRepository() {
        this.filedList = new ArrayList<>();
        this.onAdd = PublishSubject.create();
        this.onRemove = PublishSubject.create();
        this.onClear = PublishSubject.create();
        this.onRefresh = PublishSubject.create();
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

    public void addNewFiled(File file, String language, String fileNameGate, String parsed, String parsedStatus, CV extracted, String extractedStatus) {
        Filed filed = new Filed(file, file.getPath(), fileNameGate, language, parsed, parsedStatus, extracted, extractedStatus);
        filedList.add(filed);
        onAdd.onNext(filed);
        logger.info("File added to FiledRepository [parameters] " + file.getName());
    }

    public void addNewFiled(Filed filed) {
        filedList.add(filed);
        onAdd.onNext(filed);
        logger.info("File added to FiledRepository [filed] " + filed.getFile().getName());
    }

    public void removeFiled(Filed filed) {
        filedList.remove(filed);
        onRemove.onNext(filed);
        logger.info("File removed from FiledRepository [filed]");
    }

    public void removeFiled(String filePath) {
        Iterator iterator = filedList.iterator();
        while (iterator.hasNext()) {
            Filed filed = (Filed) iterator.next();
            if (filed.getFile().getPath().equals(filePath)) {
                iterator.remove();
                onRemove.onNext(filed);
            }
        }
        logger.info("File removed from FiledRepository [filePath] " + filePath);
    }

    public void clearFiledRepository() {
        filedList.clear();
        onClear.onNext(new Filed());
        logger.info("Clear FiledRepository");
    }

    public void refreshFiledRepository() {
        onRefresh.onNext(new Filed());
        logger.info("Refresh FiledRepository");
    }

}
