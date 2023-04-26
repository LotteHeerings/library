package nl.belastingdienst.library.damage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamageRepository extends JpaRepository<Damage, Long> {

    Damage findByPaid (Boolean paid);

}
