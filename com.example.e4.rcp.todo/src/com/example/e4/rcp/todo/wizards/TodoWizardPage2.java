package com.example.e4.rcp.todo.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class TodoWizardPage2 extends WizardPage {
	
	private boolean checked = false;

	public TodoWizardPage2() {
		super("wozardPage");
		setTitle("Validate");
		setDescription("Check to create the todo item");
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(5, false));
		
		Label lblCreateTodo = new Label(container, SWT.NONE);
		lblCreateTodo.setText("Create the Todo");
		
		Button btnCheck = new Button(container, SWT.CHECK);
		btnCheck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checked = ((Button) e.getSource()).getSelection();
				TodoWizard wizard = (TodoWizard) getWizard();
				wizard.setFinish(checked);
				
				wizard.getContainer().updateButtons();
			}
		});
		btnCheck.setText("Check");

		
	}

}
