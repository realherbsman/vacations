package opt.vacation.jpa.entities.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import opt.vacation.jpa.entities.DayMarker;
import opt.vacation.jpa.entities.ids.DayMarkerId;

public interface DayMarkerRepository extends JpaRepository<DayMarker, DayMarkerId> {
	public List<DayMarker> findAllByYearMarker(Integer yearMarker);
	public List<DayMarker> findAllByDateMarkerBetween(LocalDate firstDate, LocalDate lastDate);
}
