package domain.models.db_models;

import fr.itii.apiweb.domain.models.enumlist.db.DBEtablissement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class EtablissementsColTest {
    @Test
    public void EtablissementsColIdentifiersTest()
    {
        assertInstanceOf(DBEtablissement.class, DBEtablissement.ID);
        assertInstanceOf(DBEtablissement.class, DBEtablissement.NOM);
        assertInstanceOf(DBEtablissement.class, DBEtablissement.TYPE);
        assertInstanceOf(DBEtablissement.class, DBEtablissement.MAIL);
        assertInstanceOf(DBEtablissement.class, DBEtablissement.STATUT);
        assertInstanceOf(DBEtablissement.class, DBEtablissement.CODE_COMMUNE);
        assertInstanceOf(DBEtablissement.class, DBEtablissement.NOM_COMMUNE);
    }

    @Test
    public void EtablissementsColSqlNameTest()
    {
        assertEquals("id",  DBEtablissement.ID.toString());
        assertEquals("nom",  DBEtablissement.NOM.toString());
        assertEquals("type",  DBEtablissement.TYPE.toString());
        assertEquals("mail",  DBEtablissement.MAIL.toString());
        assertEquals("statut",  DBEtablissement.STATUT.toString());
        assertEquals("codecommune",  DBEtablissement.CODE_COMMUNE.toString());
        assertEquals("nomcommune",   DBEtablissement.NOM_COMMUNE.toString());
    }
}
