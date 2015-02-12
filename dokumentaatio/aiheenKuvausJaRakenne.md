Induktoija4000 on laskin, jonka olisi tarkoitusta ratkaista
yksinkertaisia ensimm�isen ja toiseen asteen yht�l�it� kuten
my�s INDUKTOIDA summia k�ytt�en induktio-oletusta.

Sovellus koostuu nelj�st� pakkauksesta;
Kayttoliittyma, Komponentit, Laskin, Main.

N�ist� Komponentit-pakkaus sis�lt�� nelj� eri komponenttia,
jotka kaikki toteuttavat Komponentti-rajapinnan sek�
Yht�l�-luokan, johon kaikki komponentit s�il�t��n.

Laskin-pakkaus sis�lt�� Laskin-luokan joka on vastuussa
yht�l�n ratkaisemisesta, Lukija-luokan joka hoitaa sy�tteen
muuntamisen Yht�l�-luokan ilmentym�ksi sek� TermiComparator-luokan
joka toteuttaa Comparator-rajapinnan.

Kayttoliittyma-pakkaus sis�lt�� pelk�n Kayttoliittyma-luokan, mutta
johon olisi my�hemmin tarkoitus sis�llytt�� sovelluksen grafiikka.

Main-pakkaus sis�lt�� ohjelman k�ynnist�v�n App-luokan.

KOMPONENTIT
-----

Termi
Pienin kaikista komponenteista se on pelkk� luku, jolla on
muuttujan arvo ja/tai eksponentti.
3, 5n, -3n^2

Laskutoimitus
Tarkoitetaan kerto- ja jakolaskuja, jotka koostuvat kahdesta
tekij�st�, jotka voivat olla joko termej�, lausekkeita tai
supistamattomia toisia laskutoimituksia.
3*3, 3/2n, (3+3)/(n*3), (23+n)(2n+2), 3*4/2

Lauseke
Tarkoitetaan sulkujen sis�ll� olevia termej�, jotka ovat useasti
vaikea supistaa alasp�in termeiksi.
(34+3n), (2*n(3n-3))

Summa
Tarkoitetaan sigma-merkint��, jossa on arvoina yl�raja, alaraja
sek� itse lauseke. Ei tue kahden termin yl�rajaa tai alarajaa,
joka on muuttujaa.
sig(n,0,n+1)

-----

N�m� kaikki luokat kuuluvat Yht�l�-luokkaan, joka pit�� sis�ll��n
yht�l�n vasemman sek� oikean puolen. Niill� tarkoitetaan
lausekkeita yht�kuin merkin molemmilla puolilla:

List<Komponentti> oikeapuoli = List<Komponentti> vasenpuoli

Yht�l� luokalla on supista()-metodi kuten kaikilla Komponentti-
rajapinnan luokilla.
Jos ja kun molemmat puolet ovat mahdollisia supistaa termeiksi
muutetaan molemmat puolet yhdeksi List<Termi> termit -listaksi.

LASKIN
-----

Laskin-luokka pit�� sis�ll��n yhden Yht�l�-luokan ilmentym�n
ja Lukija-luokan ilmentym�n, joka lukee k�ytt�j�n sy�tteen ja
palauttaa Yht�l�-luokan.

Laskin ratkaisee Yht�l�n ja pit�� sis�ll��n suurimman osan
koko ohjelman logiikasta.

KAYTTOLIITTYMA
-----

K�ytt�liittym�-luokka sis�lt�� pelk�n k�ynnist�()-metodin,
yhden Laskin-luokka ilmentym�n ja Scanner-luokan ilmentym�n,
jolla se lukee k�ytt�j�n sy�tteen ja antaa sen Laskin-luokalle.

MAIN
-----

App-luokka k�ynnist�� sovelluksen.

-----