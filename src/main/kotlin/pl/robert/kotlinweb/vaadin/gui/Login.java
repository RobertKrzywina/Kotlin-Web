package pl.robert.kotlinweb.vaadin.gui;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import pl.robert.kotlinweb.user.domain.UserService;
import pl.robert.kotlinweb.vaadin.NotificationService;
import pl.robert.kotlinweb.shared.GlobalExceptionHandler;
import pl.robert.kotlinweb.vaadin.PrimitiveAuthentication;

@Route("login")
@PageTitle("login")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Login extends VerticalLayout {

    UserService service;

    public Login(UserService service) {
        this.service = service;

        setupLayout();

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

    private void authorized() {
        add(
            new H1("You are already logged!"),
            new Anchor("", "Back to the homepage")
        );
    }

    private void unauthorized() {
        add(new H1("Login"));
        addForm();
    }

    private void addForm() {
        TextField email = new TextField("Email");

        VaadinSession.getCurrent().setErrorHandler(e ->
                NotificationService.show(GlobalExceptionHandler.Companion.getLabel()));

        Button button = new Button("Submit");

        button.addClickListener(event -> {
            PrimitiveAuthentication.email = service.getByEmail(email.getValue()).getEmail();
            UI.getCurrent().navigate("");
        });

        add(
            email,
            button,
            new Anchor("", "Back to the homepage")
        );
    }
}
