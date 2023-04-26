package nl.belastingdienst.library.damage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_damage")
public class Damage {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "archived_book_lending_id")
    private Long idBookLendingArchive;

    @Column(name = "type_of_damage")
    private String typeOfDamage;

    @Column(name = "cost_of_damage")
    private Integer costOfDamage;

    @Column(name = "paid")
    private Boolean paid;
}
