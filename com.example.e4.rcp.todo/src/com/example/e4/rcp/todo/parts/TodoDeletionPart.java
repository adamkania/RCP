package com.example.e4.rcp.todo.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class TodoDeletionPart {

	@Inject
	private ITodoService todoService;
	private ComboViewer comboViewer;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(9, false));

		comboViewer = new ComboViewer(parent, SWT.NONE);
		Combo combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 8,
				1));
		comboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				Todo todo = (Todo) element;
				return todo.getSummary();
			}
		});
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		List<Todo> todos = todoService.getTodos();
		updateViewer(todos);

		comboViewer.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 12, 1));

		Button btnDeleteSelected = new Button(parent, SWT.NONE);
		btnDeleteSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		btnDeleteSelected.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = comboViewer.getSelection();

				IStructuredSelection sel = (IStructuredSelection) selection;

				if (sel.size() > 0) {
					Todo firstElement = (Todo) sel.getFirstElement();
					todoService.deleteTodo(firstElement.getId());
					updateViewer(todoService.getTodos());
				}
			}
		});
		btnDeleteSelected.setText("Delete selected");

	}

	private void updateViewer(List<Todo> todos) {
		comboViewer.setInput(todos);
		if (todos.size() > 0) {
			comboViewer.setSelection(new StructuredSelection(todos.get(0)));
		}
	}

	@Focus
	public void focus() {
		comboViewer.getControl().setFocus();
	}
}
