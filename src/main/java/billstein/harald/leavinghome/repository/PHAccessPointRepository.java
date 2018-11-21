package billstein.harald.leavinghome.repository;

import billstein.harald.leavinghome.entity.AccessPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PHAccessPointRepository extends JpaRepository<AccessPoint, Long> {

}
