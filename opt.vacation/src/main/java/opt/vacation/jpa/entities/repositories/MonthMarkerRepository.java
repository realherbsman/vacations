package opt.vacation.jpa.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import opt.vacation.jpa.entities.MonthMarker;
import opt.vacation.jpa.entities.ids.MonthMarkerId;

public interface MonthMarkerRepository extends JpaRepository<MonthMarker, MonthMarkerId> {

}
