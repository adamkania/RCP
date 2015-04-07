package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.model.IServiceConstants;
import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class TodoDetailsPart {

	private Text textSummary;
	private Text textDescription;
	private DateTime dateTimeDueDate;
	private Button btnCheckDone;
	private Todo todo;

	@Inject
	private ITodoService todoService;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(3, false));

		Label lblSummary = new Label(parent, SWT.NONE);
		lblSummary.setText("Summary");
		new Label(parent, SWT.NONE);

		textSummary = new Text(parent, SWT.BORDER);
		textSummary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblDescription = new Label(parent, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 4));
		lblDescription.setText("Description");
		new Label(parent, SWT.NONE);

		textDescription = new Text(parent, SWT.BORDER);
		GridData gd_textDescription = new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 4);
		gd_textDescription.widthHint = 326;
		gd_textDescription.heightHint = 187;
		textDescription.setLayoutData(gd_textDescription);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		Label lblDueDate = new Label(parent, SWT.NONE);
		lblDueDate.setText("Due Date");
		new Label(parent, SWT.NONE);

		dateTimeDueDate = new DateTime(parent, SWT.BORDER);
		GridData gd_dateTimeDueDate = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_dateTimeDueDate.widthHint = 334;
		dateTimeDueDate.setLayoutData(gd_dateTimeDueDate);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		btnCheckDone = new Button(parent, SWT.CHECK);
		btnCheckDone.setText("Done");

		updateUserInterface(todo);

	}

	@Inject
	public void setTodo(
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo todo) {
		if (todo != null) {
			this.todo = todo;
		}

		updateUserInterface(todo);
	}

	private void enableUserInterface(boolean enabled) {
		if (textSummary != null && !textSummary.isDisposed()) {
			textSummary.setEnabled(enabled);
			textDescription.setEnabled(enabled);
			dateTimeDueDate.setEnabled(enabled);
			btnCheckDone.setEnabled(enabled);
		}
	}

	private void updateUserInterface(Todo todo) {
		if (todo == null) {
			enableUserInterface(false);
			return;
		}
		if (textSummary != null && !textSummary.isDisposed()) {
			enableUserInterface(true);
			textSummary.setText(todo.getSummary());
			textDescription.setText(todo.getDescription());
			dateTimeDueDate.setData(todo.getDueDate());
			btnCheckDone.setSelection(todo.isDone());
		}
	}

	@Focus
	public void onFocus() {
		textSummary.setFocus();
	}
}