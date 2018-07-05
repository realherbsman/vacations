package opt.vacation.ui.components;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import opt.vacation.beans.DataBean;
import opt.vacation.beans.DataBinderBean;
import opt.vacation.services.DatesService;
import opt.vacation.services.LengthCombinationService;

@SpringComponent
@UIScope
public class InputPanel extends HorizontalLayout {

	//private Logger logger = LoggerFactory.getLogger(opt.vacation.ui.components.InputPanel.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -8159200394184944582L;
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

		/**
		 * 
		 */
		private static final long serialVersionUID = -7737916151154456948L;

		@Override
		public void buttonClick(ClickEvent event) {
			binder.updateDayMarkers(datesService.getAllDays(binder.getData().getYear()));
			binder.updateLengthCombinations(
					lengthCombinationService.getCombinations(binder.getData().getDays(), binder.getData().getParts()));
			binder.getData().getPeriods().clear();
			binder.updatePeriods(0, Collections.emptyList());
			binder.updateVacationRating(Collections.emptyMap());
		}

	}

}
