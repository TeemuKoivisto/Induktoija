Joululoma 20-30 h

9.1 - 16.1
3h

17.1 - 23.1
3h

24.1 - 30.1
4h - Tein Summa-luokan sekä Induktoi()-metodin laskimeen, jahka viimein Induktoija alkaisi
induktoimaan.

31.1 - 6.2
0h

7.2 - 13.2
8.2 4.5h - Paljon refaktorointia. Uudelleen nimesin muutamat komponenteista jonka vuoksi
jouduin uudelleen nimeämään paljon metodeja ja muuttujia.
Aloitin Lauseke-luokan parantamisen käytettävään muotoon.
Ensimmäisenä aion saada yksinkertaiset (6+6) -tyyliset lausekkeet toimimaan.
Myös aloitin Summa-luokan muokkaamista käytettäväksi.

9.2 3h - Lausekkeen parantamista ja yleistä koodin jäsentelyä.
10.2 3h - Yksinkertaiset lausekkeet vihdoin toimivat. Tai toimi. Ainakin hetken.

14.2 - 20.2
15.2 2h - Aloitin Laskutoimituksen uudelleen muokkaamisen siihen kuntoon, että myös
supistamattomia laskutoimituksia (kuten (n(n+1))/2) pystytään käsittelemään. Sain
myös Lausekkeen kerro-metodin toimimaan halutulla tavalla (x+1)(x+1) tapauksissa.
Jouduin lisäämään kopioi()-metodin tämän tapauksen johdosta.

16.2 4.5h - Jatkoin Laskutoimituksen korjaamista ja lisäsin muutamia metodeja Komponentti-
rajapinnalle kuten sisaltaakoMuuttujan(). Yritän saada laskimen ratkaisemaan myös
yhtälöitä, jotka eivät supistu suoraan kuten juuri (n(n+1))/2). Lisäsin myös boolean-
parametrin Laskutoimitukselle josko se on mahdotonta jakaa kuten (x+2)/(x+1). Tämän
toiminallisuuden toteutus jääköön myöhempään ajankohtaan.
(3+n)(3+n)=(n+3)(n+2) -yhtälöt ovat nyt ratkaistavissa. Laskin-luokkaan luotiin uusi
toiminnallisuus yhtälöitä varten, jotka eivät supistu suoraan termeiksi.
(n(n+1)) -lausekkeet supistuvat nyt oikein.

21.2 - 27.2
21.2 2.5h - Poistin turhan tulos-muuttujan Laskutoimituksesta ja Lausekkeesta sekä
paransin supista()-metodin toiminnallisuutta. Nyt ohjelma ei enää sekoile getTuloksen
kanssa tai turhaan muuta komponentteja termeiksi.
Pieniä ongelmia Lukija-luokan kanssa mutta nyt kun supista()-metodi toimii huomattavasti
paremmin korjaaminen on suht koht helppoa. Lisäsin siis myös palautaTulosListana-metodin
Komponentti rajapinnalle, jonka kaikki komponentit nyt toteuttavat. Nyt lausekkeet,
joita ei onnistuta supistamaan yhteen termiin oksentavat koko sisältönsä eteenpäin kuten
myös Laskutoimitukset.
Sain myös pit-raportit viimein toimimaan. Kenties teen lisää testejä.

22.2 5h - Poistin viimeisetkin rippeet typerästä getTulos-metodista ja tulos-muuttujasta.
Koodi toimii nyt paljon smoothimmin ja sillä saatta nyt jopa pystyä laskemaan induktioita.
Piirsin myös luokkakaavion ja aloitin testejen tekemisen.

23.2 1.5h - Parantelin sijoitaMuuttujanTilalle-metodin toiminallisuutta, joka nyt osaa
sijoittaa muuttujan tilalle n+1 kuten induktio-oletuksessa halutaan. Enää tarvitaan vain
kunnollinen metodi, joka tuottaa induktio-väittämän ja vertaa ovatko puolet identtiset.

25.2 3h - Kommentoin turhia metodeja ja suoraviivaistin koko laskinta. Paransin myös
induktointia.