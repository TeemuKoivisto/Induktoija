(3x^2 -5x -1) = l1
/
(x -3) = l2

if l2.ekaOt.getVar != l1.ekaaOt.getVar
l2.var++
else
// x^2 -3x
if l2.ekaOt.getArvo != l1.ekaOt.getArvo
double x1 = l1.ekaOt.getArvo/l2.ekaOt.getArvo
l2.ekaOt.kerro(x1)

// 3x^2 -9x = valiTulos
l1.vahenna(valiTulos)
// l1 = -14x -1

sama rumba, nyt vain suoraan
double x2 = l1.ekaOt.getArvo/l2.ekaOt.getArvo
l2.ekaOt.kerro(x2)

// -14x 42
l1.vahenna(valiTulos)
l1 = -43

eli vastaus x1*var + x2 + l1/l2
3x -14 -43/(x-3)

xxxxxxxxxx
INDUKTIO

sig(n, 0, n) = (n(n+1))/2 >> induktio pätee
lausekkeet supistuu >> (n^2 + n)/ 2 >> n^2/2 + n/2
sitten jätetään se siihen ja vasemmassa puolessa
lisätään n+1 eli >> n^2/2 + n/2 + n + 1 >> kerrotaan kahdella
>> n^2 + n + 2n + 2 >> n^2 + 3n + 2
oikeassa puolessa sijoitetaan n + 1
>> ((n+1)((n+1)+1))/2 >> ((n+1)(n+2))/2 >> (n^2 + 2n + n + 2)/2
>> n^2/2 + n + n/2 + 1 >>> kerrotaan kahdella
>> n^2 + 2n + n + 2 >> n^2 + 3n + 2
tätä verrataan vasempaan puoleen termi kerralla
kunnes kaikki vastaavat termit on löydetty

xxxxxxxxxxxxxx

debugatut yhdistelmät:
t*t + t/t + t*l + t/l + l*t + l/t + l*l + l/l + la*t + la/t + la*l + la/l
6*6 + 6/6 + 6*(6+6) + 6/(6+6) + (6+6)*6 + (6+6)/6 + (6+6)(6+6) + (6+6)/(6+6) + 6*6*6 + 6/6/6 + 6*6*(6+6) + 6*6/(6+6) = n
n=-979.666666666666

onnistuneesti debugattuja lausekkeita:
23x+234/34x=24x-24
34n*34n/34n*3-34/2=342+234
n + 1 +n+3 = n*9/3 + 5*n 2*4 + n
(6+6)(6+6) + (6+6)/(6+6) = n
(3+3)/(3+9) + 7*3n/4 + 5*6/n + 3n/6/7 = 234n
(3+n)(3+n)=1
(3+n)(3+n)=(n+3)(n+2)
(n(n+1)) = (n(n+1)) + 1 + n

työn alla:
-n - 1 = (n(n+1))/2
>> ongelma: laskutoimitus ei supistu komponenteiksi lausekkeen sisällä
korjattu. vaikkakin pitää laskutoimitus fiksaa myöhemmin..

(n+3)/(3+n) + 7*3n = 234n
sig(n, 1, n+3) = n*9/3 + 5*n 2*4 + n
23x+234/(34+1)=24x-24
23x+234/(34x+1)=24x-24

xxxxxxxxxxxxxx


23x+234/34x+34= 24x-24+24*34*234-234324
23x+234/34x=24x-24

2.3n*n + 4n + 5.6 = 0

2 * n * n + 5n -3 = 0

34+(7+n*3)/2=6*6

34*343/34-34+34=34n*34n/34n*3
34*343/34-34+34=((n*n)/n)*3
34*343/34-34+34=n*n/n*3

34n*34n/34n*3-34/2=342+234

347-234+34-34*343/34-34+34=34-34n*34n/34n*3-34/3-342+234

lausekkeita ovat:
2424+24-344*43n/4=234-30*234-34-n/34+3
2424+24-344*43x/4(686*687/798n)+453-4543*6