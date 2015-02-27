Käyttäjä syöttää ohjelmalle syötteenä yhtälön, joka on
joko tavallinen toisen asteen yhtälö tai INDUKTIO-lause.

Ohjelma induktoi lausekkeet kuten
sig(n,0,n+1) = ((n+1)((n+1)+1))/2
ensin sijoittamalla n+1 vasemmalle puolelle
(eli lausekkeen n+1 sisään >> (n+1)+1)
sitten laskemalla sen yhteen oikean puolen kanssa.
Tästä saadaan jokin lauseke.

Sitten sijoitetaan oikealle puolelle n+1
((n+1)((n+1)+1))/2 >> (((n+1)+1)(((n+1)+1)+1))/2
Josta saatavaa lauseketta verrataan edelliseen,
joka jos induktioväite pätee on samanlainen eli
induktioväite pätee.