package opt.vacation.services;

import java.time.LocalDate;
import java.util.List;

import opt.vacation.jpa.entities.DayMarker;

public interface DatesService {
	public List<DayMarker> getAllDays(int year);
	public List<DayMarker> getDays(LocalDate firstDay, LocalDate lastDay);
	public LocalDate getFirstPeriodDay(int year, int days);
	public LocalDate getLastPeriodDay(int year, int days);
}
