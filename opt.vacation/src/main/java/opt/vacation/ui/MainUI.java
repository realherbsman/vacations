package opt.vacation.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import opt.vacation.beans.DataBean;
import opt.vacation.services.DatesService;
import opt.vacation.ui.components.DaysTabPanel;
import opt.vacation.ui.components.InputPanel;
import opt.vacation.ui.components.LengthCombinationsTabPanel;
import opt.vacation.ui.components.PeriodsTabPanel;
import opt.vacation.ui.components.ProgressPlaceholder;
import opt.vacation.ui.components.VacationRatingTabPanel;

@SpringUI
@PreserveOnRefresh
public class MainUI extends UI {

	@Autowired
	private InputPanel inputPanel;
	@Autowired
	private DaysTabPanel daysGrid;
	@Autowired
	private LengthCombinationsTabPanel lengthCombinationGrid;
	@Autowired
	private PeriodsTabPanel periodsGrid;
	@Autowired
	private VacationRatingTabPanel ratingGrid;
	
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout content = new VerticalLayout();
		
		TabSheet dataLayout = new TabSheet();
		dataLayout.addComponents(daysGrid, lengthCombinationGrid, periodsGrid, ratingGrid);
		daysGrid.setSizeFull();
		lengthCombinationGrid.setSizeFull();
		periodsGrid.setSizeFull();
		ratingGrid.setSizeFull();
		
		content.addComponents(inputPanel, dataLayout);
		dataLayout.setSizeFull();
		content.setExpandRatio(inputPanel, 0.0f);
		content.setExpandRatio(dataLayout, 1.0f);
		
		this.setContent(content);
		content.setSizeFull();
	}


	
}
