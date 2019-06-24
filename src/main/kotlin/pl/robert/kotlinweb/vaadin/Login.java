package pl.robert.kotlinweb.vaadin;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import pl.robert.kotlinweb.user.domain.UserService;
import pl.robert.kotlinweb.shared.GlobalExceptionHandler;

@Route("login")
@PageTitle("login")
public class Login extends VerticalLayout {

    private UserService service;

    public Login(UserService service) {
        this.service = service;

        setupLayout();
        addHeader();
        addForm();
    }

    private void setupLayout() {
        ThemeList theme = UI.getCurrent().getElement().getThemeList();
        theme.add(Lumo.DARK);
        setAlignItems(FlexComponent.Alignment.CENTER);
    }

    private void addHeader() {
        add(new H1("Login"));
    }

    private void addForm() {
        TextField email = new TextField("Email");

        VaadinSession.getCurrent().setErrorHandler(e -> {
            NotificationService.show(GlobalExceptionHandler.Companion.getLabel());
        });

        Button button = new Button("Submit");

        button.addClickListener(event -> {
            PrimitiveAuthentication.email = service.getByEmail(email.getValue()).getEmail();
            UI.getCurrent().navigate("/");
        });

        add(
            email,
            button
        );
    }
}
