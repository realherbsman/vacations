package opt.vacation.services;

import java.util.List;

import opt.vacation.jpa.entities.Period;

public interface PeriodService {
	public List<Period> getPeriods(int year, int days);
}
