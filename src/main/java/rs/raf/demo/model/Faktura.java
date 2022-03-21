package rs.raf.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Faktura {
    @Id
    @Getter private Long id;

    @Getter @Setter private Long prodajnaVrednost;
    @Getter @Setter private Long rabat;
    @Getter @Setter private Long porez;
    @Getter @Setter private Long zaNaplatu;

    @Getter private boolean tipFakture;
}
