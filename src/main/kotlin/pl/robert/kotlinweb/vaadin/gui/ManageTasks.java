package pl.robert.kotlinweb.vaadin.gui;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import pl.robert.kotlinweb.task.domain.Task;
import pl.robert.kotlinweb.task.domain.TaskService;
import pl.robert.kotlinweb.vaadin.PrimitiveAuthentication;

@Route("manage-tasks")
@PageTitle("manage-tasks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManageTasks extends VerticalLayout {

    TaskService service;

    public ManageTasks(TaskService service) {
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
        add(new H1("Manage tasks"));

        List<String> tasksTitles = service.getAll()
                .stream()
                .map(Task::getTitle)
                .collect(Collectors.toList());

        tasksTitles.forEach(task -> add(new Checkbox(task)));

        TextField field = new TextField();
        Button button = new Button("Add");

        button.addClickListener(e -> {
            Checkbox checkbox = new Checkbox(field.getValue());
            service.save(new Task(UUID.randomUUID().toString(), field.getValue(), "noDetails", false));
            add(checkbox);
        });

        Button deleteAllBtn = new Button("Delete all");
        deleteAllBtn.addClickListener(e -> {
            service.deleteAll();
            UI.getCurrent().getPage().reload();
        });

        add(
            field,
            button,
            deleteAllBtn,
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
