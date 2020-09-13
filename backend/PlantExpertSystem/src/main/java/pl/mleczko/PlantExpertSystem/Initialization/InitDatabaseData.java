package pl.mleczko.PlantExpertSystem.Initialization;

import org.springframework.stereotype.Component;
import pl.mleczko.PlantExpertSystem.Entity.*;
import pl.mleczko.PlantExpertSystem.Repository.DiseaseRepository;
import pl.mleczko.PlantExpertSystem.Repository.RiskFactorRepository;
import pl.mleczko.PlantExpertSystem.Repository.SymptomRepository;
import sun.jvm.hotspot.debugger.cdbg.EnumType;

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
        factor.setSlotName("temp_0_24_dlugo");
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
        symptomRepository.save(symptom);

        symptom.setSymptomId(2);
        symptom.setName("Bielenie kłosa");
        symptom.setSlotName("bielenie_klosa");
        symptomRepository.save(symptom);

        symptom.setSymptomId(3);
        symptom.setName("Położone zboże");
        symptom.setSlotName("polozone_zboze");
        symptomRepository.save(symptom);

        symptom.setSymptomId(4);
        symptom.setName("Pojawienie się mączystego nalotu na liściach i łodygach");
        symptom.setSlotName("maczysty_nalot");
        symptomRepository.save(symptom);

        symptom.setSymptomId(5);
        symptom.setName("Żółknięcie roślin");
        symptom.setSlotName("zolkniecie_roslin");
        symptomRepository.save(symptom);

        symptom.setSymptomId(6);
        symptom.setName("Obumarłe rośliny");
        symptom.setSlotName("obumieranie_roslin");
        symptomRepository.save(symptom);

        symptom.setSymptomId(7);
        symptom.setName("Początkowo czarno-brunatna plamka, później otaczająca się jasną obwódką.");
        symptom.setSlotName("czarno_brunatna_plamka");
        symptomRepository.save(symptom);

        symptom.setSymptomId(8);
        symptom.setName("Pojawianie się żółtych plamek na liściu, które przechodzą w brązowe plamy w kształcie pasów," +
                " a następnie ciemnobrązowe lub czarne kropki zawierające zarodniki do dalszego rozwoju");
        symptom.setSlotName("zolte_brazowe_plamy_pasy_liscie");
        symptomRepository.save(symptom);

        symptom.setSymptomId(9);
        symptom.setName("Na liściach plewach i plewkach plamy żółte przechodzące w brązowe, a po czasie czarne kropki.");
        symptom.setSlotName("zolte_brazowe_plamy_pasy_plewy");
        symptomRepository.save(symptom);

        symptom.setSymptomId(10);
        symptom.setName("Nieregularne brązowo-czarne smugi oraz plamy nieregularnego kształtu u podstawy źdźbła");
        symptom.setSlotName("brazowo_czarne_smugi_podst_zdzbla");
        symptomRepository.save(symptom);

        symptom.setSymptomId(11);
        symptom.setName("Wylęgnięty łan");
        symptom.setSlotName("wylegniety_lan");
        symptomRepository.save(symptom);

        symptom.setSymptomId(12);
        symptom.setName("Zainefkowane kłosy pokrywają się białym lub różowym nalotem " +
                "na których można zaobserwować zarodniki koloru łososiowego");
        symptom.setSlotName("bialy_lub_rozowy_nalot_na_klosach");
        symptomRepository.save(symptom);

        symptom.setSymptomId(13);
        symptom.setName("Pod słońce widać żółte kropki, następnie pojawiają się skupiska zarodników tzw piknidia.");
        symptom.setSlotName("zolte_kropki_piknidia");
        symptomRepository.save(symptom);

        symptom.setSymptomId(14);
        symptom.setName("Pomarańczowo-brunatne piknidia (skupiska zarodników) na źdźble, dokłosiu i liściach.");
        symptom.setSlotName("pomarancz_brunatne_piknidia");
        symptomRepository.save(symptom);

        symptom.setSymptomId(15);
        symptom.setName("Nieregularne plamy na liściach o strukturze siateczki widoczne pod słońce, z czasem zajmujące cały liść");
        symptom.setSlotName("plamy_siateczka");
        symptomRepository.save(symptom);

        symptom.setSymptomId(16);
        symptom.setName("Małe szaro-czarne plamy na styku liści i łodygi przerastające całą łodygę.");
        symptom.setSlotName("szaro_czarne_plamy");
        symptomRepository.save(symptom);

        symptom.setSymptomId(17);
        symptom.setName("Suche cylindryczne plamy na liściach przypominające przekrój pnia drzewa.");
        symptom.setSlotName("suche_cylindryczne_plamy");
        symptomRepository.save(symptom);

        symptom.setSymptomId(18);
        symptom.setName("Niewielkie brunatne plamy rozrastające się koncentrycznie, w końcu obejmujące całą blaszkę liściową.");
        symptom.setSlotName("brunatne_koncetnryczne_plamy");
        symptomRepository.save(symptom);

        symptom.setSymptomId(19);
        symptom.setName("Szare plamy na spodzie liścia z widocznym meszkiem, plamy dające wrażenie „mokrych” rozrastają się obejmując całe liście które usychają.");
        symptom.setSlotName("szare_plamy_na_spodzie_liscia");
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
        disease.setFactors(riskFactors);
        disease.setSymptoms(symptoms);
        diseaseRepository.save(disease);



    }

}
