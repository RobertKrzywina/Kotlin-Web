package pl.robert.kotlinweb.vaadin;

public class PrimitiveAuthentication {

    public static String email;

    public static boolean isAuthenticated() {
        return email != null;
    }
}
