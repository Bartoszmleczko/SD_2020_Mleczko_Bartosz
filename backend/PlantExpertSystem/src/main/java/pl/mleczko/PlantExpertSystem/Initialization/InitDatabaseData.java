package pl.mleczko.PlantExpertSystem.Initialization;

import org.springframework.stereotype.Component;
import pl.mleczko.PlantExpertSystem.Entity.*;
import pl.mleczko.PlantExpertSystem.Repository.DiseaseRepository;
import pl.mleczko.PlantExpertSystem.Repository.RiskFactorRepository;
import pl.mleczko.PlantExpertSystem.Repository.SymptomRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InitDatabaseData {

    private final RiskFactorRepository riskFactorRepository;
    private final SymptomRepository symptomRepository;
    private final DiseaseRepository diseaseRepository;

    public InitDatabaseData(RiskFactorRepository riskFactorRepository, SymptomRepository symptomRepository, DiseaseRepository diseaseRepository) {
        this.riskFactorRepository = riskFactorRepository;
        this.symptomRepository = symptomRepository;
        this.diseaseRepository = diseaseRepository;
    }

    @PostConstruct
    public void initRiskFactors(){


        RiskFactor factor = new RiskFactor();
        factor.setRiskId(1);
        factor.setName("Wadliwy Płodozmian");
        factor.setSlotName("wad_plodozmian");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(2);
        factor.setName("Wilgotna jesień (Temperatura pomiędzy 8 - 10 stopni Celsjusza)");
        factor.setSlotName("wilg_jesien");
        factor.setFactorType(FactorType.TEMPERATURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(3);
        factor.setName("Ciepła zima");
        factor.setSlotName("ciepla_zima");
        factor.setFactorType(FactorType.TEMPERATURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(4);
        factor.setName("Pszenica");
        factor.setSlotName("pszenica");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(5);
        factor.setName("Rodzaj gleby");
        factor.setSlotName("rodzaj_gleby");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(6);
        factor.setName("Wczesny siew");
        factor.setSlotName("termin_siewu");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);


        factor.setRiskId(7);
        factor.setName("Uprawa bezorkowa");
        factor.setSlotName("uprawa_bezorkowa");
        factor.setFactorType(FactorType.CULTIVATION);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(8);
        factor.setName("Gesty łan");
        factor.setSlotName("gesty_lan");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(9);
        factor.setName("Nawożenie azotowe");
        factor.setSlotName("nawozenie_azotowe");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(10);
        factor.setName("Wilgotny łan");
        factor.setSlotName("wilgotny_lan");
        factor.setFactorType(FactorType.MOISTURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(11);
        factor.setName("Temperatura 10-15 stopni");
        factor.setSlotName("temperatura");
        factor.setFactorType(FactorType.TEMPERATURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(12);
        factor.setName("Brak płodozmianu");
        factor.setSlotName("brak_plodozmianu");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(13);
        factor.setName("Krótki płodozmian");
        factor.setSlotName("krotki_plodozmian");
        factor.setPlantType(PlantType.CEREALS);
        factor.setFactorType(FactorType.OTHER);
        riskFactorRepository.save(factor);

        factor.setRiskId(14);
        factor.setName("Uprawa orkowa");
        factor.setSlotName("Uprawa orkowa");
        factor.setFactorType(FactorType.CULTIVATION);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(15);
        factor.setName("Suma temperatur aktywnych powyżej 300 st. ( suma temperatur z dni kiedy była > 5 st. Celsjusza)");
        factor.setSlotName("sum_temperatur_aktywnych");
        factor.setFactorType(FactorType.TEMPERATURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(16);
        factor.setName("Płodozmian z dużą zawartością zbóż");
        factor.setSlotName("plodozmian_zbozowy");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(17);
        factor.setName("Temperatura 10-15 st. Celsjusza przez przynajmniej 12 godzin");
        factor.setSlotName("temp_pol_doby");
        factor.setFactorType(FactorType.TEMPERATURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(18);
        factor.setName("Wilgotność 98% przez 24 godziny");
        factor.setSlotName("wysoka_wilgotnosc");
        factor.setFactorType(FactorType.MOISTURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(19);
        factor.setName("Wysoka wilgotność liści przez 3-6 godzin");
        factor.setSlotName("wilg_lisci");
        factor.setFactorType(FactorType.MOISTURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(20);
        factor.setName("Temperatura 20-24 stopnie Celsjusza");
        factor.setSlotName("temp_22_24");
        factor.setFactorType(FactorType.TEMPERATURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(21);
        factor.setName("Długi okres temperatur 0-20 st. Celsjusza jednocześnie z wysoką wilgotnością powietrza.");
        factor.setSlotName("temp_0_20_dlugo");
        factor.setFactorType(FactorType.TEMPERATURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(22);
        factor.setName("Pozostawianie słomy na polu");
        factor.setSlotName("sloma_na_polu");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(23);
        factor.setName("Źle lub niezaprawiony materiał siewny");
        factor.setSlotName("zle_zaprawiony_material_siewny");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(24);
        factor.setName("Zbytnie skracanie roślin");
        factor.setSlotName("zbytnie_skracanie_roslin");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(25);
        factor.setName("Duży udział w płodozmianie kukurydzy i zbóż");
        factor.setSlotName("udzial_kukurydzy_i_zboz");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(26);
        factor.setName("Susza");
        factor.setSlotName("susza");
        factor.setFactorType(FactorType.MOISTURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(27);
        factor.setName("Wysokie temperatury pomiędzy 25-32 stopni Celsjusza");
        factor.setSlotName("temp_25_32");
        factor.setFactorType(FactorType.TEMPERATURE);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(28);
        factor.setName("Obecność berberysu w okolicy uprawy");
        factor.setSlotName("obecnosc_berberysu");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(29);
        factor.setName("Jeczmień");
        factor.setSlotName("jeczmien");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.CEREALS);
        riskFactorRepository.save(factor);

        factor.setRiskId(30);
        factor.setName("Częsta uprawa rzepaku na polu");
        factor.setSlotName("czesta_uprawa_rzepaku");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.COLZA);
        riskFactorRepository.save(factor);

        factor.setRiskId(31);
        factor.setName("Uprawy rzepaku w okolicy");
        factor.setSlotName("uprawa_rzepaku_okolica");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.COLZA);
        riskFactorRepository.save(factor);

        factor.setRiskId(32);
        factor.setName("Długa i ciepla jesień");
        factor.setSlotName("dluga_ciepla_jesien");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.COLZA);
        riskFactorRepository.save(factor);

        factor.setRiskId(33);
        factor.setName("Suchy, ciepły maj");
        factor.setSlotName("suchy_ciepły_maj");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.POTATOES);
        riskFactorRepository.save(factor);

        factor.setRiskId(34);
        factor.setName("Temperatura powyżej 20 stopni");
        factor.setSlotName("temp_powyzej_20");
        factor.setFactorType(FactorType.TEMPERATURE);
        factor.setPlantType(PlantType.POTATOES);
        riskFactorRepository.save(factor);

        factor.setRiskId(35);
        factor.setName("Częsta uprawa ziemniaków");
        factor.setSlotName("czesta_uprawa_ziemniaka");
        factor.setFactorType(FactorType.OTHER);
        factor.setPlantType(PlantType.POTATOES);
        riskFactorRepository.save(factor);

        factor.setRiskId(36);
        factor.setName("Temperatura 12-18 stopni");
        factor.setSlotName("temp_12_18");
        factor.setFactorType(FactorType.TEMPERATURE);
        factor.setPlantType(PlantType.POTATOES);
        riskFactorRepository.save(factor);

        factor.setRiskId(37);
        factor.setName("Wysoka wilgotnosc");
        factor.setSlotName("wysoka_wilgotnosc_ziemniak");
        factor.setFactorType(FactorType.MOISTURE);
        factor.setPlantType(PlantType.POTATOES);
        riskFactorRepository.save(factor);

    }


    @PostConstruct
    public void initSymptoms(){
        Symptom symptom = new Symptom();

        symptom.setSymptomId(1);
        symptom.setName("Żółto brunatna plama w kształcie medalionu");
        symptom.setSlotName("zolto_brun_plama_medalion");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(2);
        symptom.setName("Bielenie kłosa");
        symptom.setSlotName("bielenie_klosa");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(3);
        symptom.setName("Położone zboże");
        symptom.setSlotName("polozone_zboze");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(4);
        symptom.setName("Pojawienie się mączystego nalotu na liściach i łodygach");
        symptom.setSlotName("maczysty_nalot");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(5);
        symptom.setName("Żółknięcie roślin");
        symptom.setSlotName("zolkniecie_roslin");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(6);
        symptom.setName("Obumarłe rośliny");
        symptom.setSlotName("obumieranie_roslin");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(7);
        symptom.setName("Początkowo czarno-brunatna plamka, później otaczająca się jasną obwódką.");
        symptom.setSlotName("czarno_brunatna_plamka");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(8);
        symptom.setName("Pojawianie się żółtych plamek na liściu, które przechodzą w brązowe plamy w kształcie pasów," +
                " a następnie ciemnobrązowe lub czarne kropki zawierające zarodniki do dalszego rozwoju");
        symptom.setSlotName("zolte_brazowe_plamy_pasy_liscie");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(9);
        symptom.setName("Na liściach plewach i plewkach plamy żółte przechodzące w brązowe, a po czasie czarne kropki.");
        symptom.setSlotName("zolte_brazowe_plamy_pasy_plewy");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(10);
        symptom.setName("Nieregularne brązowo-czarne smugi oraz plamy nieregularnego kształtu u podstawy źdźbła");
        symptom.setSlotName("brazowo_czarne_smugi_podst_zdzbla");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(11);
        symptom.setName("Wylęgnięty łan");
        symptom.setSlotName("wylegniety_lan");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(12);
        symptom.setName("Zainefkowane kłosy pokrywają się białym lub różowym nalotem " +
                "na których można zaobserwować zarodniki koloru łososiowego");
        symptom.setSlotName("bialy_lub_rozowy_nalot_na_klosach");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(13);
        symptom.setName("Pod słońce widać żółte kropki, następnie pojawiają się skupiska zarodników tzw piknidia.");
        symptom.setSlotName("zolte_kropki_piknidia");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(14);
        symptom.setName("Pomarańczowo-brunatne piknidia (skupiska zarodników) na źdźble, dokłosiu i liściach.");
        symptom.setSlotName("pomarancz_brunatne_piknidia");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(15);
        symptom.setName("Nieregularne plamy na liściach o strukturze siateczki widoczne pod słońce, z czasem zajmujące cały liść");
        symptom.setSlotName("plamy_siateczka");
        symptom.setPlantType(PlantType.CEREALS);
        symptomRepository.save(symptom);

        symptom.setSymptomId(16);
        symptom.setName("Małe szaro-czarne plamy na styku liści i łodygi przerastające całą łodygę.");
        symptom.setSlotName("szaro_czarne_plamy");
        symptom.setPlantType(PlantType.COLZA);
        symptomRepository.save(symptom);

        symptom.setSymptomId(17);
        symptom.setName("Suche cylindryczne plamy na liściach przypominające przekrój pnia drzewa.");
        symptom.setSlotName("suche_cylindryczne_plamy");
        symptom.setPlantType(PlantType.COLZA);
        symptomRepository.save(symptom);

        symptom.setSymptomId(18);
        symptom.setName("Niewielkie brunatne plamy rozrastające się koncentrycznie, w końcu obejmujące całą blaszkę liściową.");
        symptom.setSlotName("brunatne_koncetnryczne_plamy");
        symptom.setPlantType(PlantType.POTATOES);
        symptomRepository.save(symptom);

        symptom.setSymptomId(19);
        symptom.setName("Szare plamy na spodzie liścia z widocznym meszkiem, plamy dające wrażenie „mokrych” rozrastają się obejmując całe liście które usychają.");
        symptom.setSlotName("szare_plamy_na_spodzie_liscia");
        symptom.setPlantType(PlantType.POTATOES);
        symptomRepository.save(symptom);

    }


    @PostConstruct
    public void initDiseases(){
        Disease disease = new Disease();


        List<RiskFactor> riskFactors = new ArrayList<>();
        List<Symptom> symptoms = new ArrayList<>();


        riskFactors = riskFactorRepository.findAllByRiskIdIsBetween(1,8);
        symptoms = symptomRepository.findAllBySymptomIdIsBetween(1,3);
        disease.setDiseaseId(1);
        disease.setName("łamliwość podstawy źdźbła pszenicy");
        disease.setTemplateName("lam_podst_zdzbla_pszen");
        disease.setPrecautionDiagnose("Potencjalne zagrożenie łamliwością podstawy źdźbła. Wykonać zapobiegawczo zabieg preparatem zawierającym prochloraz, protokolazol lub metrafenon.");
        disease.setInterventionDiagnose("100% symptom łamliwości źdźbła. Wykonać zabieg interwencyjny");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.CEREALS);
        diseaseRepository.save(disease);


        disease.setDiseaseId(2);
        disease.setName("Mączniak prawdziwy pszenicy");
        disease.setTemplateName("maczniak_prawdziwy_pszenicy");
        disease.setInterventionDiagnose("Wykonać zabieg BBCH31 preparatami zawierającymi spiroksyaminę lub morfolinę np. fenprofidyne w mieszaninie z preparatami zawierającymi proquinazyd, metrafenon, lub preparatami zawierającymi składniki aktywne z grupy strobiluryn lub SDHI.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.CEREALS);
        diseaseRepository.save(disease);


        disease.setDiseaseId(3);
        disease.setName("Brunatna plamistość liści pszenicy");
        disease.setTemplateName("brunatna_plamistosc_lisci_pszenicy");
        disease.setInterventionDiagnose("W fazie rozwoju liścia flagowego lub w razie wystąpienia plamek stosujemy preparat zawierający protiokonazol lub epoksykonazol w połączeniu z innym triazolem i substancją z grupy SDHI lub strobiluryn.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.CEREALS);
        diseaseRepository.save(disease);


        disease.setDiseaseId(4);
        disease.setName("Septorioza paskowana pszenicy");
        disease.setTemplateName("septorioza_paskowana_lisci");
        disease.setInterventionDiagnose("Zabieg wykonywany preparatami zawierającymi epoksykonazol, azoksystrobinę, preparaty z grupy SDHI.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.CEREALS);
        diseaseRepository.save(disease);


        disease.setDiseaseId(5);
        disease.setName("Septorioza plew pszenicy");
        disease.setTemplateName("septorioza_plew");
        disease.setInterventionDiagnose("Wykonujemy zabieg preparatami zawierającymi triazole, SDHI i strobiluryny.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.CEREALS);
        diseaseRepository.save(disease);


        disease.setDiseaseId(6);
        disease.setName("Fuzaryjna gorzel źdźbła i korzeni pszenicy");
        disease.setTemplateName("fuzaryjna_zgorzel_zdzbla_korzeni");
        disease.setInterventionDiagnose("Zabieg profilaktyczny w fazie BBCh31 wykonać preparatami zawierającymi substancje aktywne typu prochloraz,  tebukonazol, protriokonazol, metokonazol.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.CEREALS);
        diseaseRepository.save(disease);


        disease.setDiseaseId(7);
        disease.setName("Fuzarioza kłosów pszenicy" );
        disease.setTemplateName("fuzarioza_klosow");
        disease.setInterventionDiagnose("W momencie gdy pylniki z dolnych kłosków odpadają wykonujemy zabieg preparatami zawierającymi protiokonazol, metkonazol, prochloraz, tebukonazol,  tiofanad metylu. Trzeba pamiętać, aby zabieg wykonać przynajmniej dwoma substancjami czynnymi wybranymi z powyższych.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.CEREALS);
        diseaseRepository.save(disease);


        disease.setDiseaseId(8);
        disease.setName("Rdza brunatna pszenicy");
        disease.setTemplateName("rdza_brunatna");
        disease.setInterventionDiagnose("Wykonujemy zabieg zawierający preparaty z grupy triazoli, SDHI i strobiluryn.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.CEREALS);
        diseaseRepository.save(disease);


        disease.setDiseaseId(9);
        disease.setName("Rdza źdźbłowa pszenicy");
        disease.setTemplateName("rdza_zdzblowa");
        disease.setPrecautionDiagnose("Wycinamy krzewy berberysu.");
        disease.setInterventionDiagnose("Wykonujemy zabiegi tebukonazolem, protokonazolem, strobiluryną lub SDHI.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.CEREALS);
        diseaseRepository.save(disease);


        disease.setDiseaseId(10);
        disease.setName("Plamistość siatkowa jęczmienia");
        disease.setTemplateName("plamistosc_siatkowa_jeczmienia");
        disease.setPrecautionDiagnose("Zaprawić preparatem Systiva.");
        disease.setInterventionDiagnose("Wykonać zabiegi preparatami zawierające triazole substancje z grupy SDHI i strobiluryn.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.CEREALS);
        diseaseRepository.save(disease);


        disease.setDiseaseId(11);
        disease.setName("Zgnilizna tawrdzikowa rzepaku");
        disease.setTemplateName("zgnilizna_twardzikowa_rzepaku");
        disease.setInterventionDiagnose("Na początku kwitnienia wykonujemy zabiegi preparatami zawierającymi azoksystrobinę i triazol. W pełni kwitnienia wykonujemy zabieg preparatem Propulse.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.COLZA);
        diseaseRepository.save(disease);


        disease.setDiseaseId(12);
        disease.setName("Sucha zgnilizna kapustnych");
        disease.setTemplateName("sucha_zgnilizna_kapustnych");
        disease.setInterventionDiagnose("Przy 4-6 liściach rzepaku wykonujemy zabieg preparatami Tilmor, Caryx i Toprex bądź innym preparatem zawierającym trebukonazol, difenokonazol bądź azoksystrobinę.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.COLZA);
        diseaseRepository.save(disease);


        disease.setDiseaseId(13);
        disease.setName("Alternarioza ziemniaka");
        disease.setTemplateName("alternarioza_ziemniaka");
        disease.setInterventionDiagnose("Gdy rośliny osiągną 10 cm nad ziemią wykonujemy zabiegi zapobiegawcze co 7 dni.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.POTATOES);
        diseaseRepository.save(disease);


        disease.setDiseaseId(14);
        disease.setName("Zaraza ziemniaka");
        disease.setTemplateName("zaraza_ziemniaka");
        disease.setInterventionDiagnose("W okresie takiej pogody przed kwitnieniem wykonujemy zabiegi preparatami Infinito, Ridomil co 10-14 dni zależnie od pogody. W czasie kwitnienia i po kwitnieniu możemy wykonać zabieg preparatami Acrobat Cabrio Duo, Curzate, Tanos. Wtedy zabiegi wykonujemy co 5-7 dni.");
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        disease.setPlantType(PlantType.POTATOES);
        diseaseRepository.save(disease);


    }

}
