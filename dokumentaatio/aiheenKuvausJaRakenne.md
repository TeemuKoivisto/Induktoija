Induktoija4000 on laskin, jonka olisi tarkoitusta ratkaista
yksinkertaisia ensimmäisen ja toiseen asteen yhtälöitä kuten
myös INDUKTOIDA summia käyttäen induktio-oletusta.

Sovellus koostuu neljästä pakkauksesta;
Kayttoliittyma, Komponentit, Laskin, Main.

Näistä Komponentit-pakkaus sisältää neljä eri komponenttia,
jotka kaikki toteuttavat Komponentti-rajapinnan sekä
Yhtälö-luokan, johon kaikki komponentit säilötään.

Laskin-pakkaus sisältää Laskin-luokan joka on vastuussa
yhtälön ratkaisemisesta, Lukija-luokan joka hoitaa syötteen
muuntamisen Yhtälö-luokan ilmentymäksi sekä TermiComparator-luokan
joka toteuttaa Comparator-rajapinnan.

Kayttoliittyma-pakkaus sisältää pelkän Kayttoliittyma-luokan, mutta
johon olisi myöhemmin tarkoitus sisällyttää sovelluksen grafiikka.

Main-pakkaus sisältää ohjelman käynnistävän App-luokan.

KOMPONENTIT
-----

Termi
Pienin kaikista komponenteista se on pelkkä luku, jolla on
muuttujan arvo ja/tai eksponentti.
3, 5n, -3n^2

Laskutoimitus
Tarkoitetaan kerto- ja jakolaskuja, jotka koostuvat kahdesta
tekijästä, jotka voivat olla joko termejä, lausekkeita tai
supistamattomia toisia laskutoimituksia.
3*3, 3/2n, (3+3)/(n*3), (23+n)(2n+2), 3*4/2

Lauseke
Tarkoitetaan sulkujen sisällä olevia termejä, jotka ovat useasti
vaikea supistaa alaspäin termeiksi.
(34+3n), (2*n(3n-3))

Summa
Tarkoitetaan sigma-merkintää, jossa on arvoina yläraja, alaraja
sekä itse lauseke. Ei tue kahden termin ylärajaa tai alarajaa,
joka on muuttujaa.
sig(n,0,n+1)

-----

Nämä kaikki luokat kuuluvat Yhtälö-luokkaan, joka pitää sisällään
yhtälön vasemman sekä oikean puolen. Niillä tarkoitetaan
lausekkeita yhtäkuin merkin molemmilla puolilla:

List<Komponentti> oikeapuoli = List<Komponentti> vasenpuoli

Yhtälö luokalla on supista()-metodi kuten kaikilla Komponentti-
rajapinnan luokilla.
Jos ja kun molemmat puolet ovat mahdollisia supistaa termeiksi
muutetaan molemmat puolet yhdeksi List<Termi> termit -listaksi.

LASKIN
-----

Laskin-luokka pitää sisällään yhden Yhtälö-luokan ilmentymän
ja Lukija-luokan ilmentymän, joka lukee käyttäjän syötteen ja
palauttaa Yhtälö-luokan.

Laskin ratkaisee Yhtälön ja pitää sisällään suurimman osan
koko ohjelman logiikasta.

KAYTTOLIITTYMA
-----

Käyttöliittymä-luokka sisältää pelkän käynnistä()-metodin,
yhden Laskin-luokka ilmentymän ja Scanner-luokan ilmentymän,
jolla se lukee käyttäjän syötteen ja antaa sen Laskin-luokalle.

MAIN
-----

App-luokka käynnistää sovelluksen.

-----