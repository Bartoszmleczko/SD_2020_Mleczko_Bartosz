(clear)
(reset)



(deftemplate lam_podst_zdzbla_pszen
    (slot istnieje)
)

(deftemplate maczniak_prawdziwy_pszenicy
    (slot istnieje)
)

(deftemplate brunatna_plamistosc_lisci_pszenicy
    (slot istnieje)
)

(deftemplate septorioza_paskowana_lisci
    (slot istnieje)
)

(deftemplate septorioza_plew
    (slot istnieje)
)

(deftemplate fuzaryjna_zgorzel_zdzbla_korzeni
    (slot istnieje)
)

(deftemplate fuzarioza_klosow
    (slot istnieje)
)

(deftemplate rdza_brunatna
    (slot istnieje)
)

(deftemplate rdza_zdzblowa
    (slot istnieje)
)

(deftemplate plamistosc_siatkowa_jeczmienia
    (slot istnieje)
)

(deftemplate zgnilizna_twardzikowa_rzepaku
     (slot istnieje)
 )

(deftemplate sucha_zgnilizna_kapustnych
    (slot istnieje)
)

(deftemplate alternarioza_ziemniaka
    (slot istnieje)
)

(deftemplate zaraza_ziemniaka
    (slot istnieje)
)

( deftemplate risk_factors
    (slot wad_plodozmian)
    (slot wilg_jesien)
    (slot ciepla_zima)
    (slot pszenica)
    (slot rodzaj_gleby)
    (slot termin_siewu)
    (slot gesty_lan)
    (slot nawozenie_azotowe)
    (slot wilgotny_lan)
    (slot temperatura)
    (slot brak_plodozmianu)
    (slot krotki_plodozmian)
    (slot uprawa_bezorkowa)
    (slot sum_temperatur_aktywnych)
    (slot plodozmian_zbozowy)
    (slot temp_pol_doby)
    (slot wysoka_wilgotnosc)
    (slot uprawa_orkowa)
    (slot wilg_lisci)
    (slot temp_22_24)
    (slot temp_0_24_dlugo)
    (slot sloma_na_polu)
    (slot zle_zaprawiony_material_siewny)
    (slot zbytnie_skracanie_roslin)
    (slot udzial_kukurydzy_i_zboz)
    (slot susza)
    (slot temp_25_32)
    (slot obecnosc_berberysu)
    (slot gatunek_rdza_zdzblowa)
    (slot jeczmien)
    (slot czesta_uprawa_rzepaku)
    (slot uprawa_rzepaku_okolica)
    (slot dluga_ciepla_jesien)
    (slot suchy_cieply_maj)
    (slot temp_powyzej_20)
    (slot czesta_uprawa_ziemniaka)
    (slot temp_12_18)
    (slot wysoka_wilgotnosc_ziemniak)

)

(deftemplate symptoms
    (slot zolto_brun_plama_medalion)
    (slot bielenie_klosa)
    (slot polozone_zboze)
    (slot maczysty_nalot)
    (slot zolkniecie_roslin)
    (slot obumieranie_roslin)
    (slot czarno_brunatna_plamka)
    (slot zolte_brazowe_plamy_pasy_liscie)
    (slot zolte_brazowe_plamy_pasy_plewy)
    (slot brazowo_czarne_smugi_podst_zdzbla)
    (slot wylegniety_lan)
    (slot bialy_lub_rozowy_nalot_na_klosach)
    (slot zolte_kropki_piknidia)
    (slot pomarancz_brunatne_piknidia)
    (slot plamy_siateczka)
    (slot szaro_czarne_plamy)
    (slot suche_cylindryczne_plamy)
    (slot brunatne_koncetnryczne_plamy)
    (slot szare_plamy_na_spodzie_liscia)
)


(defrule lamPodstZdzbla01
         (risk_factors (wad_plodozmian 1 ) (wilg_jesien 1) ( pszenica 1) )
	=>
	(assert (lam_podst_zdzbla_pszen (istnieje 0.5)))
)

(defrule lamPodstZdzbla02
         (risk_factors (wad_plodozmian 1 ) (pszenica 1) ( termin_siewu 1) )
	=>
	(assert (lam_podst_zdzbla_pszen (istnieje 0.5)))
)

(defrule lamPodstZdzbla03
         (risk_factors (wad_plodozmian 1 ) (wilg_jesien 1) ( termin_siewu 1) )
	=>
	(assert (lam_podst_zdzbla_pszen (istnieje 0.5)))
)

(defrule lamPodstZdzbla04
         (risk_factors (pszenica 1 ) )
         (symptoms (zolto_brun_plama_medalion 1))
	=>
	(assert (lam_podst_zdzbla_pszen (istnieje 0.5)))
)

(defrule lamPodstZdzbla04
         (risk_factors (wad_plodozmian 1 ) (pszenica 1))
         (symptoms (zolto_brun_plama_medalion 1))
	=>
	(assert (lam_podst_zdzbla_pszen (istnieje 0.5)))
)

(defrule lam_podstawy_zapob
        (lam_podst_zdzbla_pszen (istnieje 0.5))
    =>
    ( bind ?*str* "Potencjalne zagrożenie łamliwością podstawy źdźbła. Wykonać zapobiegawczo zabieg preparatem zawierającym prochloraz, protokolazol lub metrafenon.")
    (return ?*str*)
)

(defrule _lam_podstawy_interw
        (lam_podst_zdzbla_pszen (istnieje 1))
    =>
    ( bind ?*str* "100% symptom łamliwości źdźbła. Wykonać zabieg interwencyjny.")
    (return ?*str*)
)

(defrule maczniakPrawdziwy01
    (risk_factors   (gesty_lan 1)  (nawozenie_azotowe 1 ) )
    => (assert (maczniak_prawdziwy_pszenicy (istnieje 1) ))
)

(defrule maczniakPrawdziwy02
    (symptoms   (or (maczysty_nalot 1)  (nawozenie_azotowe 1) (obumieranie_roslin 1)) )
    => (assert (maczniak_prawdziwy_pszenicy (istnieje 1) ))
)

(defrule maczniak_prawdziwy_pszenicy_interw
        (maczniak_prawdziwy_pszenicy (istnieje 1))
    =>
    ( bind ?*str2* "Wykonać zabieg BBCH31 preparatami zawierającymi spiroksyaminę lub morfolinę np. fenprofidyne w
    mieszaninie z preparatami zawierającymi proquinazyd, metrafenon, lub preparatami zawierającymi składniki aktywne z grupy strobiluryn lub SDHI.”)
    (return ?*str2*)
)

(defrule brunatnaPlamistoscLisci01
    (and (risk_factors   (brak_plodozmianu 1) )
         (symptoms (czarno_brunatna_plamka 1) )
    )
    => (assert (brunatna_plamistosc_lisci_pszenicy (istnieje 1) ))
)

(defrule brunatnaPlamistoscLisci02
     (risk_factors   (or (brak_plodozmianu 1) (krotki_plodozmian 1) ) )
    => (assert (brunatna_plamistosc_lisci_pszenicy (istnieje 1) ))
)

(defrule brunatnaPlamistocLisciPszenicyInterw
        (brunatna_plamistosc_lisci_pszenicy (istnieje 1))
    =>
    ( bind ?*str3* "W fazie rozwoju liścia flagowego lub w razie wystąpienia plamek stosujemy preparat zawierający
     protiokonazol lub epoksykonazol w połączeniu z innym triazolem i substancją z grupy SDHI lub strobiluryn.”)
    (return ?*str3*)
)

(defrule septoriozaPaskowanaLisci01
     (risk_factors   (or (plodomian_zbozowy 1) (temp_pol_doby 1) (wysoka_wilgotnosc 1) (gesty_lan 1) ) )
    => (assert (septorioza_paskowana_lisci (istnieje 1) ))
)

(defrule septoriozaPaskowanaLisci02
     (symptoms  (zolte_brazowe_plamy_pasy_liscie 1) )
    => (assert (septorioza_paskowana_lisci (istnieje 1) ))
)

(defrule septoriozaPaskowanaLisciInterw
        (septorioza_paskowana_lisci (istnieje 1))
    =>
    ( bind ?*str4* "Zabieg wykonywany preparatami zawierającymi epoksykonazol, azoksystrobinę, preparaty z grupy SDHI. ”)
    (return ?*str4*)
)

(defrule septoriozaPlew01
     (risk_factors   (or (uprawa_orkowa 1) (wilg_lisci 1) (temp_22_24 1)  ) )
    => (assert (septorioza_plew (istnieje 1) ))
)

(defrule septoriozaPlew02
     (symptoms  (zolte_brazowe_plamy_pasy_plewy 1) )
    => (assert (septorioza_plew (istnieje 1) ))
)
(defrule septoriozaPlewInterw
        (septorioza_plew (istnieje 1))
    =>
    ( bind ?*str5* "Wykonujemy zabieg preparatami zawierającymi triazole, SDHI i strobiluryny.”)
    (return ?*str5*)
)

(defrule fuzaryjnaZgorzelZdzblaKorzeni01
     (risk_factors  (temp_0_24 1) (sloma_na_polu 1) (uprawa_bezorkowa 1)   )
    => (assert (fuzaryjna_zgorzel_zdzbla_korzeni (istnieje 1) ))
)

(defrule fuzaryjnaZgorzelZdzblaKorzeni02
     (symptoms
                    (and (bielenie_klosa 1)
                    (or (wylegniety_lan 1) (brazowo_czarne_smugi_podst_zdzbla 1)) )  )
    => (assert (fuzaryjna_zgorzel_zdzbla_korzeni (istnieje 1) ))
)

(defrule fuzaryjnaZgorzelZdzblaKorzeniInterw
        (fuzaryjna_zgorzel_zdzbla_korzeni (istnieje 1))
    =>
    ( bind ?*str6* "Zabieg profilaktyczny w fazie BBCh31 wykonać preparatami zawierającymi substancje aktywne typu prochloraz,  tebukonazol, protriokonazol, metkonazol.”)
    (return ?*str6*)
)

(defrule fuzariozaKlosa01
     (risk_factors (or (zle_zaprawiony_material_siewny 1) (uprawa_bezorkowa 1) (zbytnie_skracanie_roslin 1) (udzial_kukurydzy_i_zboz 1))    )
    => (assert (fuzarioza_klosow (istnieje 1) ))
)

(defrule fuzariozaKlosa02
     (symptoms (bialy_lub_rozowy_nalot_na_klosach)    )
    => (assert (fuzarioza_klosow (istnieje 1) ))
)

(defrule fuzariozaKlosaInterw
        (fuzarioza_klosow (istnieje 1))
    =>
    ( bind ?*str7* "W momencie gdy pylniki z dolnych kłosków odpadają wykonujemy zabieg
                    preparatami zawierającymi protiokonazol, metkonazol, prochloraz, tebukonazol,  tiofanad metylu.
                    Trzeba pamiętać, aby zabieg wykonać przynajmniej dwoma substancjami czynnymi wybranymi z powyższych
”)
    (return ?*str7*)
)

(defrule rdzaBrunatna01
     (risk_factors (susza 1) (temp_25_32 1)   )
    => (assert (rdza_brunatna (istnieje 1) ))
)

(defrule rdzaBrunatna02
     (symptoms (zolte_kropki_piknidia)    )
    => (assert (rdza_brunatna (istnieje 1) ))
)

(defrule rdzaBrunatnaInterw
        (rdza_brunatna (istnieje 1))
    =>
    ( bind ?*str8* "Wykonujemy zabieg zawierający preparaty z grupy triazoli , SDHI i strobiluryn.”)
    (return ?*str8*)
)

(defrule rdzaZdzblowa01
     (risk_factors  (obecnosc_berberysu 1) (gatunek_rdza_zdzblowa 1)   )
    => (assert (rdza_zdzblowa (istnieje 0.5) ))
)

(defrule rdzaZdzblowa02
     (symptoms (pomarancz_brunatne_piknidia 1)    )
    => (assert (rdza_zdzblowa (istnieje 1) ))
)

(defrule rdzaZdzblowaZapobieg
        (rdza_zdzblowa   (istnieje 0.5))
    =>
    ( bind ?*str9* "Wycinamy krzewy berberysu”)
    (return ?*str9*)
)

(defrule rdzaZdzblowaInterw
        (rdza_zdzblowa (istnieje 1))
    =>
    ( bind ?*str10* "Wykonujemy zabiegi tebukonazolem, protokonazolem, strobiluryną lub SDHI.”)
    (return ?*str10*)
)

(defrule plamistoscSiatkowa01
     (risk_factors  (jeczmien 1) )
    => (assert (plamistosc_siatkowa_jeczmienia (istnieje 0.5) ))
)

(defrule plamistoscSiatkowa02
     (symptoms  (plamy_siateczka 1) )
    => (assert (plamistosc_siatkowa_jeczmienia (istnieje 1) ))
)

(defrule plamistoscSiatkowaZapobieg
        (plamistosc_siatkowa_jeczmienia (istnieje 0.5))
    =>
    ( bind ?*str11* "Zaprawić preparatem Systiva.”)
    (return ?*str11*)
)

(defrule plamistoscSiatkowaInterw
        (plamistosc_siatkowa_jeczmienia (istnieje 1))
    =>
    ( bind ?*str12* "Wykonać zabiegi preparatami zawierające triazole substancje z grupy SDHI i strobiluryn.”)
    (return ?*str12*)
)

(defrule zgniliznaTwardzikowaRzepaku01
      (risk_factors  (czesta_uprawa_rzepaku 1) (uprawa_rzepaku_okolica 1) )
     => (assert (zgnilizna_twardzikowa_rzepaku (istnieje 1) ))
 )

(defrule zgniliznaTwardzikowaRzepaku02
      (symptoms  (szaro_czarne_plamy 1) )
     => (assert (zgnilizna_twardzikowa_rzepaku (istnieje 1) ))
 )

(defrule zgniliznaTwardzikowaRzepakuInterw
        (zgnilizna_twardzikowa_rzepaku (istnieje 1))
    =>
    ( bind ?*str13* "Na początku kwitnienia wykonujemy zabiegi preparatami zawierającymi azoksystrobinę i triazol.
     W pełni kwitnienia wykonujemy zabieg preparatem Propulse.”)
    (return ?*str13*)
)

(defrule suchaZgniliznaKapustnych01
      (risk_factors  (czesta_uprawa_rzepaku 1) (uprawa_rzepaku_okolica 1) (dluga_ciepla_jesien 1) )
     => (assert (sucha_zgnilizna_kapustnych (istnieje 1) ))
 )

 (defrule suchaZgniliznaKapustnycu02
       (symptoms  (suche_cylindryczne_plamy 1) )
      => (assert (zgnilizna_twardzikowa_rzepaku (istnieje 1) ))
  )

(defrule suchaZgniliznaKapustnychInterw
        (sucha_zgnilizna_kapustnych (istnieje 1))
    =>
    ( bind ?*str14* "	Przy 4-6 liściach rzepaku wykonujemy zabieg preparatami Tilmor,
     Caryx i Toprex bądź innym preparatem zawierającym trebukonazol, difenokonazol bądź azoksystrobinę.”)
    (return ?*str14*)
)

(defrule alternariozaZiemniaka01
      (risk_factors  (suchy_cieply_maj 1) (temp_powyzej_20 1) )
     => (assert (alternarioza_ziemniaka (istnieje 1) ))
 )

(defrule alternariozaZiemniaka01
      (symptoms (brunatne_koncetnryczne_plamy 1) )
     => (assert (alternarioza_ziemniaka (istnieje 1) ))
 )

(defrule alternariozaInterw
         (alternarioza_ziemniaka (istnieje 1))
     =>
     ( bind ?*str15* "Gdy rośliny osiągną 10 cm nad ziemią wykonujemy zabiegi zapobiegawcze co 7 dni.”)
     (return ?*str15*)
 )

(defrule zarazaZiemniaka01
      (risk_factors  (czesta_uprawa_ziemniaka 1) (wysoka_wilgotnosc_ziemniak 1) (temp_12_18) )
     => (assert (zaraza_ziemniaka (istnieje 1) ))
 )

(defrule zarazaZiemniaka02
      (symptoms  (szare_plamy_na_spodzie_liscia 1) )
     => (assert (zaraza_ziemniaka (istnieje 1) ))
 )

(defrule zarazaZiemniakaInterw
         (zaraza_ziemniaka (istnieje 1))
     =>
     ( bind ?*str16* "W okresie takiej pogody przed kwitnieniem wykonujemy zabiegi preparatami Infinito,
      Ridomil co 10-14 dni zależnie od pogody. W czasie kwitnienia i po kwitnieniu możemy wykonać zabieg
       preparatami Acrobat Cabrio Duo, Curzate, Tanos. Wtedy zabiegi wykonujemy co 5-7 dni.
”)
     (return ?*str16*)
 )

; ##############
(facts)
(run)
