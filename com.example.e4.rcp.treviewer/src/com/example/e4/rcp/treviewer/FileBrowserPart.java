package com.example.e4.rcp.treviewer;

import java.io.File;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class FileBrowserPart {
	
	private TreeViewer viewer;
	
	private Image image;
	
	@PostConstruct
	public void createControls(Composite parent){
		parent.setLayout(new GridLayout(1, false));
		
		createImage();
		
		viewer = new TreeViewer(parent, SWT.BORDER);
		Tree tree = viewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		
		viewer.setInput(File.listRoots());
		
	}
	
	@Focus
	public void onFocus(){
		viewer.getControl().setFocus();
	}
	
	@PreDestroy
	public void dispose(){
		image.dispose();
	}
	
	private void createImage(){
		
		Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/sample.png"), null);
		ImageDescriptor imageDscr = ImageDescriptor.createFromURL(url);
		this.image = imageDscr.createImage();
	}
	
	
	
	
	class ViewLabelProvider extends StyledCellLabelProvider {

		@Override
		public void update(ViewerCell cell){
			Object element = cell.getElement();
			StyledString text = new StyledString();
			File file = (File) element;
			if(file.isDirectory()){
				text.append(getFileName(file));
				cell.setImage(image);
				String[] files = file.list();
				if(files != null){
					text.append(" (" + files.length + ") ", StyledString.COUNTER_STYLER);
				} 
			}
			else {
				text.append(getFileName(file));
			}
			cell.setText(text.toString());
			cell.setStyleRanges(text.getStyleRanges());
			super.update(cell);
		}
		
		private String getFileName(File file){
			String name = file.getName();
			return name.isEmpty() ?file.getPath() : name;
		}
	}

}
