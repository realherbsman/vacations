package opt.vacation.ui.components;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import opt.vacation.beans.DataBean;
import opt.vacation.beans.DataBinderBean;
import opt.vacation.services.DatesService;
import opt.vacation.services.LengthCombinationService;
import opt.vacation.services.PeriodCombinationService;
import opt.vacation.services.PeriodService;

@SpringComponent
@UIScope
public class InputPanel extends HorizontalLayout {

	private Logger logger = LoggerFactory.getLogger(opt.vacation.ui.components.InputPanel.class);

	private static final String YEAR_FIELD_TITLE = "Year";
	private static final String DAYS_FIELD_TITLE = "Days";
	private static final String PARTS_FIELD_TITLE = "Parts";
	private static final String REFRESH_BUTTON_TITLE = "Refresh";

	private TextField yearField;
	private TextField daysField;
	private TextField partsField;
	private Button refreshButton;

	@Autowired
	private DatesService datesService;
	@Autowired
	private LengthCombinationService lengthCombinationService;
	@Autowired
	private PeriodService periodService;
	@Autowired
	private PeriodCombinationService periodCombinationService;
	@Autowired
	private DataBinderBean binder;

	public InputPanel() {
		super();
		this.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
		this.yearField = new TextField(YEAR_FIELD_TITLE);
		this.daysField = new TextField(DAYS_FIELD_TITLE);
		this.partsField = new TextField(PARTS_FIELD_TITLE);
		this.refreshButton = new Button(REFRESH_BUTTON_TITLE, new RefreshClickListener());
		this.addComponents(this.yearField, this.daysField, this.partsField, this.refreshButton);
	}

	@PostConstruct
	protected void init() {
		binder.getDataBinder().forField(this.yearField).asRequired().withConverter(Integer::valueOf, String::valueOf)
				.bind(DataBean::getYear, DataBean::setYear);
		binder.getDataBinder().forField(this.daysField).asRequired().withConverter(Integer::valueOf, String::valueOf)
				.bind(DataBean::getDays, DataBean::setDays);
		binder.getDataBinder().forField(this.partsField).asRequired().withConverter(Integer::valueOf, String::valueOf)
				.bind(DataBean::getParts, DataBean::setParts);
	}

	private class RefreshClickListener implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			binder.updateDayMarkers(datesService.getAllDays(binder.getData().getYear()));
			binder.updateLengthCombinations(
					lengthCombinationService.getCombinations(binder.getData().getDays(), binder.getData().getParts()));
			binder.getData().getPeriods().clear();
			for (int i = 1; i <= binder.getData().getDays(); i++) {
				binder.updatePeriods(i, periodService.getPeriods(binder.getData().getYear(), i));
			}
			binder.updateVacationRating(periodCombinationService.getPeriodCombinations(binder.getData().getYear(), binder.getData().getDays(), binder.getData().getParts()));
		}

	}

}
