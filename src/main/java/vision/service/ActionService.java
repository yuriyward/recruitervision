package vision.service;

/**
 * @author Yuriy on 22.07.2017.
 */
public interface ActionService {
    default String processName(String name){
        if(name.equals("Yuriy")) {
            return "Hello Yuriy!";
        }
        else {
            return "Hello Unknown Stranger!";
        }
    }
}
