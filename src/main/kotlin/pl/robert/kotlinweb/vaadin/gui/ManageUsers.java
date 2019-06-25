package pl.robert.kotlinweb.vaadin.gui;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import pl.robert.kotlinweb.user.domain.UserService;
import pl.robert.kotlinweb.user.domain.dto.UserDetailsDto;
import pl.robert.kotlinweb.vaadin.PrimitiveAuthentication;

import java.util.List;

@Route("manage-users")
@PageTitle("manage-users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManageUsers extends VerticalLayout {

    UserService service;

    public ManageUsers(UserService service) {
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
        Grid<UserDetailsDto> grid = new Grid<>();

        List<UserDetailsDto> users = service.getAll();

        grid.setItems(users);

        grid.addColumn(UserDetailsDto::getId).setHeader("Id");
        grid.addColumn(UserDetailsDto::getEmail).setHeader("Email");
        grid.addColumn(UserDetailsDto::getFirstName).setHeader("First name");
        grid.addColumn(UserDetailsDto::getLastName).setHeader("Last name");
        grid.addColumn(UserDetailsDto::getRoles).setHeader("Roles");
        grid.addColumn(UserDetailsDto::getEnabled).setHeader("Enabled");
        grid.addColumn(UserDetailsDto::getAccountNonExpired).setHeader("Account non expired");
        grid.addColumn(UserDetailsDto::getAccountNonLocked).setHeader("Account non locked");
        grid.addColumn(UserDetailsDto::getCredentialsNonExpired).setHeader("Credentials non expired");

        Button button = new Button("Delete all");

        button.addClickListener(e -> {
            String id = service.getByEmail(PrimitiveAuthentication.email).getId();
            service.deleteAllExceptGivenId(id);
            UI.getCurrent().getPage().reload();
        });

        add(
            new H1("Manage users"),
            grid,
            button,
            new Anchor("", "Back to the homepage")
        );
    }

    private void unauthorized() {
        add(
            new H1("Access denied!"),
            new Anchor("", "Back to the homepage")
        );
    }
}
