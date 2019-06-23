package pl.robert.kotlinweb.vaadin;

import com.vaadin.flow.component.notification.Notification;

abstract class NotificationService {

    static void show(String message) {
        Notification.show(message, 4000, Notification.Position.MIDDLE);
    }
}
