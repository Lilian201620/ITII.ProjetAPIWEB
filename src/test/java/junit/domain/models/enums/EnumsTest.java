package junit.domain.models.enums;

import fr.itii.apiweb.domain.models.enums.DBTable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class EnumsTest {
    @Test
    public void TablesIdentifiersTest() {
        assertInstanceOf(DBTable.class, DBTable.COMMUNES);
        assertInstanceOf(DBTable.class, DBTable.ETABLISSEMENTS);
    }

    @Test
    public void TablesSqlNameTest() {
        assertEquals("communes", DBTable.COMMUNES.toString());
        assertEquals("etablissements", DBTable.ETABLISSEMENTS.toString());
    }

    @Test
    void CommunesColIdentifiersTest() {
        assertInstanceOf(DBTable.Commune.class, DBTable.Commune.ID);
        assertInstanceOf(DBTable.Commune.class, DBTable.Commune.NOM);
        assertInstanceOf(DBTable.Commune.class, DBTable.Commune.CODE_DEPARTEMENT);
        assertInstanceOf(DBTable.Commune.class, DBTable.Commune.CODE_POSTAL);
        assertInstanceOf(DBTable.Commune.class, DBTable.Commune.CODE_REGION);
        assertInstanceOf(DBTable.Commune.class, DBTable.Commune.POPULATION);
    }

    @Test
    void CommunesColSqlNameTest() {
        assertEquals("id", DBTable.Commune.ID.toString());
        assertEquals("nom", DBTable.Commune.NOM.toString());
        assertEquals("codecommune", DBTable.Commune.CODE_COMMUNE.toString());
        assertEquals("codedepartement", DBTable.Commune.CODE_DEPARTEMENT.toString());
        assertEquals("codepostal", DBTable.Commune.CODE_POSTAL.toString());
        assertEquals("coderegion", DBTable.Commune.CODE_REGION.toString());
        assertEquals("population", DBTable.Commune.POPULATION.toString());
    }

    @Test
    public void EtablissementsColIdentifiersTest() {
        assertInstanceOf(DBTable.Etablissement.class, DBTable.Etablissement.ID);
        assertInstanceOf(DBTable.Etablissement.class, DBTable.Etablissement.NOM);
        assertInstanceOf(DBTable.Etablissement.class, DBTable.Etablissement.TYPE);
        assertInstanceOf(DBTable.Etablissement.class, DBTable.Etablissement.MAIL);
        assertInstanceOf(DBTable.Etablissement.class, DBTable.Etablissement.STATUT);
        assertInstanceOf(DBTable.Etablissement.class, DBTable.Etablissement.CODE_COMMUNE);
        assertInstanceOf(DBTable.Etablissement.class, DBTable.Etablissement.NOM_COMMUNE);
    }

    @Test
    public void EtablissementsColSqlNameTest() {
        assertEquals("id",  DBTable.Etablissement.ID.toString());
        assertEquals("nom",  DBTable.Etablissement.NOM.toString());
        assertEquals("type",  DBTable.Etablissement.TYPE.toString());
        assertEquals("mail",  DBTable.Etablissement.MAIL.toString());
        assertEquals("statut",  DBTable.Etablissement.STATUT.toString());
        assertEquals("codecommune",  DBTable.Etablissement.CODE_COMMUNE.toString());
        assertEquals("nomcommune",   DBTable.Etablissement.NOM_COMMUNE.toString());
    }
}