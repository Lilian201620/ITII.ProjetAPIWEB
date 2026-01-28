package domain.models.db_models;

import fr.itii.apiweb.domain.models.db_models.EtablissementsCol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class EtablissementsColTest {
    @Test
    public void EtablissementsColIdentifiersTest()
    {
        assertInstanceOf(EtablissementsCol.class, EtablissementsCol.ID);
        assertInstanceOf(EtablissementsCol.class, EtablissementsCol.NOM);
        assertInstanceOf(EtablissementsCol.class, EtablissementsCol.TYPE);
        assertInstanceOf(EtablissementsCol.class, EtablissementsCol.MAIL);
        assertInstanceOf(EtablissementsCol.class, EtablissementsCol.STATUT);
        assertInstanceOf(EtablissementsCol.class, EtablissementsCol.CODE_COMMUNE);
        assertInstanceOf(EtablissementsCol.class, EtablissementsCol.NOM_COMMUNE);
    }

    @Test
    public void EtablissementsColSqlNameTest()
    {
        assertEquals("id",  EtablissementsCol.ID.toString());
        assertEquals("nom",  EtablissementsCol.NOM.toString());
        assertEquals("type",  EtablissementsCol.TYPE.toString());
        assertEquals("mail",  EtablissementsCol.MAIL.toString());
        assertEquals("statut",  EtablissementsCol.STATUT.toString());
        assertEquals("codecommune",  EtablissementsCol.CODE_COMMUNE.toString());
        assertEquals("nomcommune",   EtablissementsCol.NOM_COMMUNE.toString());
    }
}
