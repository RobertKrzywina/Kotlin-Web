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
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import pl.robert.kotlinweb.user.UserController;
import pl.robert.kotlinweb.user.domain.dto.UserDto;
import pl.robert.kotlinweb.vaadin.NotificationService;
import pl.robert.kotlinweb.shared.GlobalExceptionHandler;
import pl.robert.kotlinweb.vaadin.PrimitiveAuthentication;

@Route("register")
@PageTitle("Register")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Register extends VerticalLayout {

    UserController controller;

    public Register(UserController controller) {
        this.controller = controller;

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
        setAlignItems(Alignment.CENTER);
    }

    private void authorized() {
        add(
            new H1("You are already logged!"),
            new Anchor("", "Back to the homepage")
        );
    }

    private void unauthorized() {
        add(new H1("Register"));
        addForm();
    }

    private void addForm() {
        Button button = new Button("Submit");

        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField email = new TextField("Email");

        PasswordField pass = new PasswordField();
        pass.setLabel("Password");
        NativeButton btn = new NativeButton("Toggle eye icon", event -> {
            pass.setRevealButtonVisible(!pass.isRevealButtonVisible());
        });

        VaadinSession.getCurrent().setErrorHandler(e -> {
            NotificationService.show(GlobalExceptionHandler.Companion.getLabel());
        });

        button.addClickListener(event -> {
            controller.save(new UserDto(email.getValue(), pass.getValue(), firstName.getValue(), lastName.getValue()));
            NotificationService.show("Congratulations! Your account has been created.");
        });

        add(
            firstName,
            lastName,
            email,
            pass,
            button,
            new Anchor("", "Back to the homepage")
        );
    }
}
