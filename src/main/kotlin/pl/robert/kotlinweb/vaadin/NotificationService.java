package pl.robert.kotlinweb.vaadin;

import com.vaadin.flow.component.notification.Notification;

public abstract class NotificationService {

    public static void show(String message) {
        Notification.show(message, 4000, Notification.Position.MIDDLE);
    }
}
