package pl.robert.kotlinweb.vaadin.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import pl.robert.kotlinweb.vaadin.PrimitiveAuthentication;

@Route("")
@PageTitle("Homepage")
public class Homepage extends VerticalLayout {

    public Homepage() {
        setupLayout();
        addHeader();

        if (PrimitiveAuthentication.isAuthenticated()) {
            authorized();
        } else {
            unauthorized();
        }
    }

    private void setupLayout() {
        ThemeList theme = UI.getCurrent().getElement().getThemeList();
        theme.add(Lumo.DARK);
        setAlignItems(FlexComponent.Alignment.CENTER);
    }

    private void addHeader() {
        add(new H1("Homepage"));
    }

    private void authorized() {
        add(new Anchor("manage-users", "Manage users"));
        logoutBtn();
    }

    private void logoutBtn() {
        Button button = new Button("Logout");

        button.addClickListener(event -> {
            PrimitiveAuthentication.email = null;
            UI.getCurrent().navigate("login");
        });

        add(button);
    }

    private void unauthorized() {
        add(
            new Anchor("register", "Create account"),
            new Anchor("login", "Sing In")
        );
    }
}
