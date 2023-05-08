package rs.fon.studentska_sluzba.domain;

/**
 * Status koji ima molba u nekom trenutku.
 *
 * Pamti se da li je vec obradjena/razresena, ili se jos ceka na obradu.
 *
 * @author VeljkoBlagojevic
 */
public enum StatusMolbe {

    /**
     * Molba je i dalje u fazi obrade, nije gotova.
     */
    U_OBRADI,

    /**
     * Molba je obradjena/razresena, administrator je odgovorio na zahtev
     */
    RAZRESENA

}
