package alarm;

/**
 * Created by Jewana on 1/19/2016.
 */
public class NotificationModel {
    public String id;
    public String notificationTtile;

    public NotificationModel(){

    }

    public NotificationModel(String id, String notificationTtile) {
        this.id = id;
        this.notificationTtile = notificationTtile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotificationTtile() {
        return notificationTtile;
    }

    public void setNotificationTtile(String notificationTtile) {
        this.notificationTtile = notificationTtile;
    }
}
