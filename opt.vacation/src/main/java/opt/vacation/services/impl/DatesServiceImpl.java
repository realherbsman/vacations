package opt.vacation.services.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.spring.annotation.SpringComponent;

import opt.vacation.jpa.entities.*;
import opt.vacation.jpa.entities.repositories.*;
import opt.vacation.services.DatesService;

@SpringComponent
public class DatesServiceImpl implements DatesService {

	private static final double AVG_MONTH_LENGTH = 29.7d;

	@Autowired
	private YearMarkerRepository yearRepo;
	@Autowired
	private MonthMarkerRepository monthRepo;
	@Autowired
	private DayMarkerRepository dayRepo;
	
	@Override
	@Transactional
	public List<DayMarker> getAllDays(int year) {
		for (int i = -1; i < 2; i++) {
			markDays(year + i);
		}
		return dayRepo.findAllByYearMarker(year);
	}

	public static LocalDate getOrthodoxEaster(int year) {
		LocalDate result = null;
		int r1 = year % 4;
		int r2 = year % 7;
		int r3 = year % 19;
		int r4 = (19 * r3 + 15) % 30;
		int r5 = (2 * r1 + 4 * r2 + 6 * r4 + 6) % 7;
		int mdays = r5 + r4 + 13;

		if (mdays > 39) {
			mdays = mdays - 39;
			result = LocalDate.of(year, 5, mdays);
		} else if (mdays > 9) {
			mdays = mdays - 9;
			result = LocalDate.of(year, 4, mdays);
		} else {
			mdays = mdays + 22;
			result = LocalDate.of(year, 3, mdays);
		}

		return result;
	}

	public static LocalDate getRadunitsa(int year) {
		return getOrthodoxEaster(year).plusDays(9);
	}

	public static List<LocalDate> getHolidays(int year) {
		List<LocalDate> result = new LinkedList<LocalDate>();
		result.add(getRadunitsa(year));
		result.add(LocalDate.of(year, Month.JANUARY, 1));
		result.add(LocalDate.of(year, Month.JANUARY, 7));
		result.add(LocalDate.of(year, Month.MARCH, 8));
		result.add(LocalDate.of(year, Month.MAY, 1));
		result.add(LocalDate.of(year, Month.MAY, 9));
		result.add(LocalDate.of(year, Month.JULY, 3));
		result.add(LocalDate.of(year, Month.NOVEMBER, 7));
		result.add(LocalDate.of(year, Month.DECEMBER, 25));
		Collections.sort(result);
		return result;
	}

	private void markDays(int year) {
		YearMarker yearMarker = yearRepo.findOne(year);
		if (yearMarker == null) {
			LocalDate dayPointer = LocalDate.of(year, Month.JANUARY, 1);
			List<LocalDate> holidays = getHolidays(year);

			yearMarker = new YearMarker();
			yearMarker.setYearMarker(year);
			yearMarker.setTotalDays(dayPointer.lengthOfYear());

			List<MonthMarker> monthMarkers = new LinkedList<MonthMarker>();
			List<DayMarker> yearDays = new LinkedList<DayMarker>();
			List<DayMarker> monthDays = new LinkedList<DayMarker>();

			Month monthPointer = dayPointer.getMonth();
			while (dayPointer.getYear() == year) {
				while (dayPointer.getMonth() == monthPointer) {
					DayMarker dayMarker = prepareDayMarker(dayPointer, holidays.contains(dayPointer));
					monthDays.add(dayMarker);
					yearDays.add(dayMarker);

					dayPointer = dayPointer.plusDays(1);
				}
				MonthMarker monthMarker = prepareMonthMarker(monthPointer, year, monthDays);
				monthMarkers.add(monthMarker);
				updateDayPrices(monthMarker, monthDays);
				monthPointer = dayPointer.getMonth();
				monthDays.clear();
			}
			
			dayRepo.save(yearDays);
			monthRepo.save(monthMarkers);
			yearRepo.save(yearMarker);
		}
	}
	
	private void updateDayPrices(MonthMarker monthMarker, List<DayMarker> monthDays) {
		for (DayMarker dayMarker : monthDays) {
			if (!dayMarker.getIsHoliday()) {
				dayMarker.setVacationPrice(monthMarker.getVacationPrice());
				if ((dayMarker.getDayOfWeek() != DayOfWeek.SATURDAY)
						&& (dayMarker.getDayOfWeek() != DayOfWeek.SUNDAY)) {
					dayMarker.setWorkPrice(monthMarker.getWorkPrice());
				}
			}
		}
	}

	private MonthMarker prepareMonthMarker(Month monthPointer, int year, List<DayMarker> monthDays) {
		int totalDays = 0;
		int holidayAmount = 0;
		int workDays = 0;
		int daysOff = 0;
		int payingDays = 0;

		for (DayMarker dayMarker : monthDays) {
			totalDays++;
			if (dayMarker.getIsHoliday()) {
				holidayAmount++;
			}
			if ((dayMarker.getDayOfWeek() == DayOfWeek.SATURDAY)
					|| (dayMarker.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				daysOff++;
			} else {
				workDays++;
				if (!dayMarker.getIsHoliday()) {
					payingDays++;
				}
			}
		}

		MonthMarker monthMarker = new MonthMarker();
		monthMarker.setMonthMarker(monthPointer);
		monthMarker.setYearMarker(year);
		monthMarker.setTotalDays(totalDays);
		monthMarker.setDaysOff(daysOff);
		monthMarker.setHolidays(holidayAmount);
		monthMarker.setPayingDays(payingDays);
		monthMarker.setWorkdays(workDays);
		monthMarker.setWorkPrice(1d / payingDays);
		monthMarker.setVacationPrice(1d / AVG_MONTH_LENGTH);
		return monthMarker;
	}

	private DayMarker prepareDayMarker(LocalDate dayPointer, boolean isHoliday) {
		DayMarker dayMarker = new DayMarker();
		dayMarker.setDateMarker(dayPointer);
		dayMarker.setDayMarker(dayPointer.getDayOfMonth());
		dayMarker.setMonthMarker(dayPointer.getMonth());
		dayMarker.setYearMarker(dayPointer.getYear());
		dayMarker.setDayOfWeek(dayPointer.getDayOfWeek());
		dayMarker.setIsHoliday(isHoliday);
		dayMarker.setWorkPrice(0d);
		dayMarker.setVacationPrice(0d);
		return dayMarker;
	}

	@Override
	public LocalDate getFirstPeriodDay(int year, int days) {
		LocalDate firstDay = LocalDate.of(year, Month.JANUARY, 1);
		
		if (days > 1) {
			int daysToShift = days - 1;
			int holidaysShifted = 0;
			while (daysToShift > 0) {
				firstDay = firstDay.minusDays(daysToShift);
				
				List<LocalDate> holidays = new LinkedList<>();
				for (int y = firstDay.getYear(); y < year; y++) {
					holidays.addAll(getHolidays(y));
				}
				
				int holidaysToShift = 0;
				for (LocalDate holiday : holidays) {
					if (holiday.isEqual(firstDay) || holiday.isAfter(firstDay)) {
						holidaysToShift++;
					}
				}
				daysToShift = holidaysToShift - holidaysShifted;
				holidaysShifted = holidaysToShift;
			}
		}
		
		return firstDay;
	}

	@Override
	public LocalDate getLastPeriodDay(int year, int days) {
		LocalDate lastDay = LocalDate.of(year, Month.DECEMBER, 31);
		
		if (days > 1) {
			int daysToShift = days - 1;
			int holidaysShifted = 0;
			while (daysToShift > 0) {
				lastDay = lastDay.plusDays(daysToShift);
				
				List<LocalDate> holidays = new LinkedList<>();
				for (int y = lastDay.getYear(); y > year; y--) {
					holidays.addAll(getHolidays(y));
				}
				
				int holidaysToShift = 0;
				for (LocalDate holiday : holidays) {
					if (holiday.isEqual(lastDay) || holiday.isAfter(lastDay)) {
						holidaysToShift++;
					}
				}
				daysToShift = holidaysToShift - holidaysShifted;
				holidaysShifted = holidaysToShift;
			}
		}
		
		return lastDay;
	}
	
	@Override
	@Transactional
	public List<DayMarker> getDays(LocalDate firstDay, LocalDate lastDay) {
		for (int i = firstDay.getYear(); i <= lastDay.getYear(); i++) {
			this.markDays(i);
		}
		return dayRepo.findAllByDateMarkerBetween(firstDay, lastDay);
	}
}
