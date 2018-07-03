package opt.vacation.jpa.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import opt.vacation.jpa.entities.Period;
import opt.vacation.jpa.entities.ids.PeriodId;

public interface PeriodRepository extends JpaRepository<Period, PeriodId> {
	public List<Period> findAllByYearMarkerAndOfficialLength(Integer yearMarker, Integer officialLength);
}
