package opt.vacation.services.impl;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.spring.annotation.SpringComponent;

import opt.vacation.jpa.entities.DayMarker;
import opt.vacation.jpa.entities.Period;
import opt.vacation.jpa.entities.repositories.PeriodRepository;
import opt.vacation.services.DatesService;
import opt.vacation.services.PeriodService;

@SpringComponent
public class PeriodServiceImpl implements PeriodService {
	
	@Autowired
	private PeriodRepository periodRepo;
	@Autowired
	private DatesService datesService;
	
	@Override
	@Transactional
	public List<Period> getPeriods(int year, int days) {
		List<Period> result = periodRepo.findAllByYearMarkerAndOfficialLength(year, days);
		
		if (result.isEmpty()) {
			result = generatePeriods(year, days);
			result = periodRepo.save(result);
		}
		
		return result;
	}

	private List<Period> generatePeriods(int year, int days) {
		
		LocalDate firstDay = datesService.getFirstPeriodDay(year, days);
		LocalDate lastDay = datesService.getLastPeriodDay(year, days);
		LocalDate lastDayYear = LocalDate.of(year, Month.DECEMBER, 31);
		
		List<DayMarker> dayList = datesService.getDays(firstDay, lastDay);
		Map<LocalDate, DayMarker> dayMap = new LinkedHashMap<>();
		for (DayMarker day : dayList) {
			dayMap.put(day.getDateMarker(), day);
		}
		
		List<Period> result = new LinkedList<>();
		while ((firstDay.isBefore(lastDayYear)) || (firstDay.isEqual(lastDayYear))) {
			LocalDate lastPeriodDay = firstDay;
			int actualLength = days;
			double workPrice = 0.0d;
			double vacationPrice = 0.0d;
			for (int shift = 0; shift < actualLength; shift++) {
				lastPeriodDay = firstDay.plusDays(shift);
				DayMarker day = dayMap.get(lastPeriodDay);
				if (day.getIsHoliday()) {
					actualLength++;
				}
				workPrice += day.getWorkPrice();
				vacationPrice += day.getVacationPrice();
			}
			
			Period item = new Period();
			item.setYearMarker(year);
			item.setFirstDateYear(firstDay.getYear());
			item.setFirstDateMonth(firstDay.getMonth());
			item.setFirstDateDay(firstDay.getDayOfMonth());
			item.setOfficialLength(days);
			item.setFirstDay(firstDay);
			item.setLastDay(lastPeriodDay);
			item.setActualLength(actualLength);
			item.setWorkPrice(workPrice);
			item.setVacationPrice(vacationPrice);
			result.add(item);
			
			firstDay = firstDay.plusDays(1);
		}
		
		return result;
	}
	
}
