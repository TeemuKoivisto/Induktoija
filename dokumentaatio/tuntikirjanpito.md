Joululoma 20-30 h

9.1 - 16.1
3h

17.1 - 23.1
3h

24.1 - 30.1
4h - Tein Summa-luokan sek� Induktoi()-metodin laskimeen, jahka viimein Induktoija alkaisi
induktoimaan.

31.1 - 6.2
0h

7.2 - 13.2
8.2 4.5h - Paljon refaktorointia. Uudelleen nimesin muutamat komponenteista jonka vuoksi
jouduin uudelleen nime�m��n paljon metodeja ja muuttujia.
Aloitin Lauseke-luokan parantamisen k�ytett�v��n muotoon.
Ensimm�isen� aion saada yksinkertaiset (6+6) -tyyliset lausekkeet toimimaan.
My�s aloitin Summa-luokan muokkaamista k�ytett�v�ksi.

9.2 3h - Lausekkeen parantamista ja yleist� koodin j�sentely�.
10.2 3h - Yksinkertaiset lausekkeet vihdoin toimivat. Tai toimi. Ainakin hetken.

14.2 - 20.2
15.2 2h - Aloitin Laskutoimituksen uudelleen muokkaamisen siihen kuntoon, ett� my�s
supistamattomia laskutoimituksia (kuten (n(n+1))/2) pystyt��n k�sittelem��n. Sain
my�s Lausekkeen kerro-metodin toimimaan halutulla tavalla (x+1)(x+1) tapauksissa.
Jouduin lis��m��n kopioi()-metodin t�m�n tapauksen johdosta.

16.2 4.5h - Jatkoin Laskutoimituksen korjaamista ja lis�sin muutamia metodeja Komponentti-
rajapinnalle kuten sisaltaakoMuuttujan(). Yrit�n saada laskimen ratkaisemaan my�s
yht�l�it�, jotka eiv�t supistu suoraan kuten juuri (n(n+1))/2). Lis�sin my�s boolean-
parametrin Laskutoimitukselle josko se on mahdotonta jakaa kuten (x+2)/(x+1). T�m�n
toiminallisuuden toteutus j��k��n my�hemp��n ajankohtaan.
(3+n)(3+n)=(n+3)(n+2) -yht�l�t ovat nyt ratkaistavissa. Laskin-luokkaan luotiin uusi
toiminnallisuus yht�l�it� varten, jotka eiv�t supistu suoraan termeiksi.
(n(n+1)) -lausekkeet supistuvat nyt oikein.

21.2 - 27.2
21.2 2.5h - Poistin turhan tulos-muuttujan Laskutoimituksesta ja Lausekkeesta sek�
paransin supista()-metodin toiminnallisuutta. Nyt ohjelma ei en�� sekoile getTuloksen
kanssa tai turhaan muuta komponentteja termeiksi.
Pieni� ongelmia Lukija-luokan kanssa mutta nyt kun supista()-metodi toimii huomattavasti
paremmin korjaaminen on suht koht helppoa. Lis�sin siis my�s palautaTulosListana-metodin
Komponentti rajapinnalle, jonka kaikki komponentit nyt toteuttavat. Nyt lausekkeet,
joita ei onnistuta supistamaan yhteen termiin oksentavat koko sis�lt�ns� eteenp�in kuten
my�s Laskutoimitukset.
Sain my�s pit-raportit viimein toimimaan. Kenties teen lis�� testej�.

22.2 5h - Poistin viimeisetkin rippeet typer�st� getTulos-metodista ja tulos-muuttujasta.
Koodi toimii nyt paljon smoothimmin ja sill� saatta nyt jopa pysty� laskemaan induktioita.
Piirsin my�s luokkakaavion ja aloitin testejen tekemisen.

23.2 1.5h - Parantelin sijoitaMuuttujanTilalle-metodin toiminallisuutta, joka nyt osaa
sijoittaa muuttujan tilalle n+1 kuten induktio-oletuksessa halutaan. En�� tarvitaan vain
kunnollinen metodi, joka tuottaa induktio-v�itt�m�n ja vertaa ovatko puolet identtiset.

25.2 3h - Kommentoin turhia metodeja ja suoraviivaistin koko laskinta. Paransin my�s
induktointia.