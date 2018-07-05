package opt.vacation.ui.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Window;

public class ProgressPlaceholder extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8155364840518490273L;

	public ProgressPlaceholder() {
		super();
		this.setModal(true);
		this.setClosable(false);
		this.setResizable(false);
		this.setDraggable(false);
		this.center();
				
		HorizontalLayout content = new HorizontalLayout();
		content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		
		ProgressBar progress = new ProgressBar();
		progress.setIndeterminate(true);
		content.addComponent(progress);
		
		this.setContent(content);
		content.setSizeFull();
	}
	
}
