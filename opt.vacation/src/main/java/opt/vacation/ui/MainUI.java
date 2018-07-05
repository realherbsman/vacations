package opt.vacation.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import opt.vacation.ui.components.DaysTabPanel;
import opt.vacation.ui.components.InputPanel;
import opt.vacation.ui.components.LengthCombinationsTabPanel;
import opt.vacation.ui.components.PeriodsTabPanel;

@SpringUI
@PreserveOnRefresh
public class MainUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1088001830802206070L;
	@Autowired
	private InputPanel inputPanel;
	@Autowired
	private DaysTabPanel daysGrid;
	@Autowired
	private LengthCombinationsTabPanel lengthCombinationGrid;
	@Autowired
	private PeriodsTabPanel periodsGrid;
	
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout content = new VerticalLayout();
		
		TabSheet dataLayout = new TabSheet();
		dataLayout.addComponents(daysGrid, lengthCombinationGrid, periodsGrid);
		daysGrid.setSizeFull();
		lengthCombinationGrid.setSizeFull();
		periodsGrid.setSizeFull();
				
		content.addComponents(inputPanel, dataLayout);
		dataLayout.setSizeFull();
		content.setExpandRatio(inputPanel, 0.0f);
		content.setExpandRatio(dataLayout, 1.0f);
		
		this.setContent(content);
		content.setSizeFull();
	}


	
}
