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

(defrule lamPodstZdzbla05
         (risk_factors (wad_plodozmian 1 ) (pszenica 1))
         (symptoms (zolto_brun_plama_medalion 1))
	=>
	(assert (lam_podst_zdzbla_pszen (istnieje 0.5)))
)

(defrule lam_podstawy_zapob
        (lam_podst_zdzbla_pszen (istnieje 0.5))
    =>
    ( bind ?*str* "lam_podst_zdzbla_pszen:zapob")
    (return ?*str*)
)

(defrule _lam_podstawy_interw
        (lam_podst_zdzbla_pszen (istnieje 1))
    =>
    ( bind ?*str* "lam_podst_zdzbla_pszen:interw")
    (return ?*str*)
)

(defrule maczniakPrawdziwy01
    (risk_factors   (gesty_lan 1)  (nawozenie_azotowe 1 ) )
    => (assert (maczniak_prawdziwy_pszenicy (istnieje 1) ))
)

(defrule maczniakPrawdziwy02
    (symptoms   (maczysty_nalot 1) )
    => (assert (maczniak_prawdziwy_pszenicy (istnieje 1) ))
)

(defrule maczniak_prawdziwy_pszenicy_interw
        (maczniak_prawdziwy_pszenicy (istnieje 1))
    =>
    ( bind ?*str2* "maczniak_prawdziwy_pszenicy:interw")
    (return ?*str2*)
)

(defrule brunatnaPlamistoscLisci01
         (symptoms (czarno_brunatna_plamka 1) )
    => (assert (brunatna_plamistosc_lisci_pszenicy (istnieje 1) ))
)

(defrule brunatnaPlamistoscLisci02
      (risk_factors {brak_plodozmianu == 1 || krotki_plodozmian == 1} )
    => (assert (brunatna_plamistosc_lisci_pszenicy (istnieje 1) ))
)

(defrule brunatnaPlamistocLisciPszenicyInterw
        (brunatna_plamistosc_lisci_pszenicy (istnieje 1))
    =>
    ( bind ?*str3* "brunatna_plamistosc_lisci_pszenicy:interw")
    (return ?*str3*)
)

(defrule septoriozaPaskowanaLisci01
     (risk_factors {plodozmian_zbozowy == 1 || temp_pol_doby == 1 || wysoka_wilgotnosc == 1 || gesty_lan == 1} )
    => (assert (septorioza_paskowana_lisci (istnieje 1) ))
)

(defrule septoriozaPaskowanaLisci02
     (symptoms  (zolte_brazowe_plamy_pasy_liscie 1) )
    => (assert (septorioza_paskowana_lisci (istnieje 1) ))
)

(defrule septoriozaPaskowanaLisciInterw
        (septorioza_paskowana_lisci (istnieje 1))
    =>
    ( bind ?*str4* "septorioza_paskowana_lisci:interw")
    (return ?*str4*)
)

(defrule septoriozaPlew01
        (risk_factors {uprawa_orkowa == 1 || wilg_lisci == 1 || temp_22_24 == 1}  )
    => (assert (septorioza_plew (istnieje 1) ))
)

(defrule septoriozaPlew02
     (symptoms  (zolte_brazowe_plamy_pasy_plewy 1) )
    => (assert (septorioza_plew (istnieje 1) ))
)
(defrule septoriozaPlewInterw
        (septorioza_plew (istnieje 1))
    =>
    ( bind ?*str5* "septorioza_plew:interw")
    (return ?*str5*)
)

(defrule fuzaryjnaZgorzelZdzblaKorzeni01
     (risk_factors  (temp_0_20_dlugo 1) (sloma_na_polu 1) (uprawa_bezorkowa 1)   )
    => (assert (fuzaryjna_zgorzel_zdzbla_korzeni (istnieje 1) ))
)

(defrule fuzaryjnaZgorzelZdzblaKorzeni02
                   ( symptoms {bielenie_klosa == 1 || wylegniety_lan == 1 || brazowo_czarne_smugi_podst_zdzbla == 1} )
    => (assert (fuzaryjna_zgorzel_zdzbla_korzeni (istnieje 1) ))
)

(defrule fuzaryjnaZgorzelZdzblaKorzeniInterw
        (fuzaryjna_zgorzel_zdzbla_korzeni (istnieje 1))
    =>
    ( bind ?*str6* "fuzaryjna_zgorzel_zdzbla_korzeni:interw")
    (return ?*str6*)
)

(defrule fuzariozaKlosa01
     ( risk_factors {zle_zaprawiony_material_siewny == 1 || uprawa_bezorkowa == 1 || zbytnie_skracanie_roslin == 1 || udzial_kukurydzy_i_zboz == 1 })
    => (assert (fuzarioza_klosow (istnieje 1) ))
)

(defrule fuzariozaKlosa02
     (symptoms (bialy_lub_rozowy_nalot_na_klosach 1)    )
    => (assert (fuzarioza_klosow (istnieje 1) ))
)

(defrule fuzariozaKlosaInterw
        (fuzarioza_klosow (istnieje 1))
    =>
    ( bind ?*str7* "fuzarioza_klosow:interw")
    (return ?*str7*)
)

(defrule rdzaBrunatna01
     (risk_factors (susza 1) (temp_25_32 1)   )
    => (assert (rdza_brunatna (istnieje 1) ))
)

(defrule rdzaBrunatna02
     (symptoms (zolte_kropki_piknidia 1)    )
    => (assert (rdza_brunatna (istnieje 1) ))
)

(defrule rdzaBrunatnaInterw
        (rdza_brunatna (istnieje 1))
    =>
    ( bind ?*str8* "rdza_brunatna:interw")
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
    ( bind ?*str9* "rdza_zdzblowa:zapob")
    (return ?*str9*)
)

(defrule rdzaZdzblowaInterw
        (rdza_zdzblowa   (istnieje 1))
    =>
    ( bind ?*str10* "rdza_zdzblowa:interw")
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
    ( bind ?*str11* "plamistosc_siatkowa_jeczmienia:zapob")
    (return ?*str11*)
)

(defrule plamistoscSiatkowaInterw
        (plamistosc_siatkowa_jeczmienia (istnieje 1))
    =>
    ( bind ?*str12* "plamistosc_siatkowa_jeczmienia:interw")
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
    ( bind ?*str13* "zgnilizna_twardzikowa_rzepaku:interw")
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
    ( bind ?*str14* "sucha_zgnilizna_kapustnych:interw")
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
     ( bind ?*str15* "alternarioza_ziemniaka:interw")
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
     ( bind ?*str16* "zaraza_ziemniaka: interw")
     (return ?*str16*)
 )