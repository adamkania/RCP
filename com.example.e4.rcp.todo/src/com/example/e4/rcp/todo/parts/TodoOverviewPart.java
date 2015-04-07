package com.example.e4.rcp.todo.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.model.ITodoService;
import com.example.e4.rcp.todo.model.Todo;

public class TodoOverviewPart {

	@Inject
	private ITodoService todoService;
	
	@Inject
	private EMenuService menuService;
	
	private Table table;

	private TableViewer tableViewer;

	protected String searchString = "";
	private Text search;

	@PostConstruct
	private void postConstruct(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		Button btnLoadData = new Button(parent, SWT.NONE);
		btnLoadData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableViewer.setInput(todoService.getTodos());

			}
		});
		btnLoadData.setText("Load Data");
		new Label(parent, SWT.NONE);

		search = new Text(parent, SWT.BORDER | SWT.SEARCH | SWT.CANCEL
				| SWT.ICON_SEARCH);
		search.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2,
				1));
		search.setMessage("Filter");

		search.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Text source = (Text) e.getSource();
				searchString = source.getText();
				tableViewer.refresh();

			}
		});

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION| SWT.MULTI);
		table = tableViewer.getTable();
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_table.widthHint = 439;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		TableViewerColumn colSummary = new TableViewerColumn(tableViewer,
				SWT.NONE);
		colSummary.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Todo todo = (Todo) element;
				return todo.getSummary();
			}
		});
		colSummary.getColumn().setWidth(100);
		colSummary.getColumn().setText("Summary");

		TableViewerColumn colDescription = new TableViewerColumn(tableViewer,
				SWT.NONE);
		colDescription.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Todo todo = (Todo) element;
				return todo.getDescription();
			}
		});
		colDescription.getColumn().setWidth(200);
		colDescription.getColumn().setText("Description");

		tableViewer.setInput(todoService.getTodos());

		tableViewer.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				Todo todo = (Todo) element;
				return todo.getSummary().contains(searchString)
						|| todo.getDescription().contains(searchString);
			}
		});
		
		menuService.registerContextMenu(tableViewer.getControl(), "com.example.e4.rcp.todo.popupmenu.table");
	}

	@Focus
	public void focus() {
		tableViewer.getControl().setFocus();
	}
}
