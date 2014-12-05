package com.wolfden.java.duplicitydestroyer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.wolfden.java.duplicitydestroyer.utils.FileUtils;

public class DuplicityDestroyerApplication {

	protected Shell shell;
	private Text text;
	private Table table;
	private Combo fileFormatsComboBox;
	private Combo fileExtensionsComboBox;
	
	ArrayList<TaggedFile> fileList;
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DuplicityDestroyerApplication window = new DuplicityDestroyerApplication();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setText("Duplicity Destroyer");
		shell.setLayout(new GridLayout(5, false));

		Label lblPath = new Label(shell, SWT.NONE);
		lblPath.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblPath.setText("Path");

		text = new Text(shell, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		fileFormatsComboBox = new Combo(shell, SWT.NONE);
		fileFormatsComboBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO - update other combo box
			}
		});
		fileFormatsComboBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		fileFormatsComboBox.setItems(FileUtils.SUPPORTED_FORMATS);
		fileFormatsComboBox.select(0);

		fileExtensionsComboBox = new Combo(shell, SWT.NONE);
		fileExtensionsComboBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		
		fileExtensionsComboBox.setItems(getExtensionFilterItems(fileFormatsComboBox.getSelectionIndex()));
		Button btnBrowse = new Button(shell, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);
				dialog.setFilterPath(text.getText());
				String path = dialog.open();
				if (path != null) {
					// Set the text box to the new selection
					text.setText(path);
					File directory = new File(path);
					File[] filesInDir = FileUtils.getFilesFromDir(directory);
					fileList = FileUtils.createTaggedFileList(filesInDir);
					updateTable(fileList);
				}
			}
		});
		btnBrowse.setText("Browse...");

		table = new Table(shell, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		table.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				String string = event.detail == SWT.CHECK ? "Checked"
						: "Selected";
				System.out.println(event.item + " " + string);
			}
		});

		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(200);
		tblclmnName.setText("Name");

		TableColumn tblclmnSize = new TableColumn(table, SWT.NONE);
		tblclmnSize.setWidth(100);
		tblclmnSize.setText("Size");

		TableColumn tblclmnDateModified = new TableColumn(table, SWT.NONE);
		tblclmnDateModified.setWidth(100);
		tblclmnDateModified.setText("Date Modified");

		TableColumn tblclmnPath = new TableColumn(table, SWT.NONE);
		tblclmnPath.setWidth(400);
		tblclmnPath.setText("Path");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileUtils.tagDuplicates(fileList);
				table.removeAll();
				updateTable(fileList);
			}
		});
		btnNewButton.setText("Find Duplicates");

	}

	private void updateTable(ArrayList<TaggedFile> taggedFiles) {
		for (TaggedFile taggedFile : taggedFiles) {
			TableItem item = new TableItem(table, SWT.NONE);
			if(taggedFile.isDuplicate()){
				item.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_CYAN));
			}
			item.setText(0, taggedFile.getFile().getName());
			item.setText(1, String.valueOf(taggedFile.getFile().length()));
			item.setText(2, String.valueOf(taggedFile.getFile().lastModified()));
			item.setText(3, String.valueOf(taggedFile.getFile().getPath()));
		}
	}

	private String[] getExtensionFilterItems(int selectionIndex) {
		switch (selectionIndex) {
		case 0:
			return FileUtils.ALL_FORMATS;
		case 1:
			return FileUtils.SUPPORTED_MUSIC_EXTENSIONS;
		case 2:
			return FileUtils.SUPPORTED_IMAGES_EXTENSIONS;

		case 3:
			return FileUtils.SUPPORTED_VIDEO_EXTENSIONS;
		case 4:
			return FileUtils.SUPPORTED_TEXT_EXTENSIONS;
		default:
			return null;
		}
	}
}
