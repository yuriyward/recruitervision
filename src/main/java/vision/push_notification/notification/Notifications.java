package vision.push_notification.notification;

/**
 * @author Yuriy on 12.10.2017.
 */
public enum  Notifications implements Notification {

    INFORMATION("images/info.png", "#2C54AB"),
    NOTICE("images/notice.png", "#8D9695"),
    SUCCESS("images/success.png", "#009961"),
    WARNING("images/warning.png", "#E23E0A"),
    ERROR("images/error.png", "#CC0033");

    private final String urlResource;
    private final String paintHex;

    Notifications(String urlResource, String paintHex) {
        this.urlResource = urlResource;
        this.paintHex = paintHex;
    }

    @Override
    public String getURLResource() {
        return urlResource;
    }

    @Override
    public String getPaintHex() {
        return paintHex;
    }
}
