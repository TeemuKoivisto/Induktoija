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
n=979.666666666666

onnistuneesti debugattuja lausekkeita:
3n+3*3(3+(3/n)*7)=2n+3
23x+234/34x=24x-24
34n*34n/34n*3-34/2=342+234
n + 1 +n+3 = n*9/3 + 5*n 2*4 + n
(6+6)(6+6) + (6+6)/(6+6) = n
(3+3)/(3+9) + 7*3n/4 + 5*6/n + 3n/6/7 = 234n
(3+n)(3+n)=1
(3+n)(3+n)=(n+3)(n+2)
(n(n+1)) = (n(n+1)) + 1 + n
(0(0+1))/2 = 0
((n+1)((n+1)+1))/2 = n + 1
n+1 = ((n+1)((n+1)+1))/2
sig(n,0,n+1) = ((n+1)((n+1)+1))/2
sig(n,0,n(n+1)) = (n(n+1)(n+2))/3

työn alla:
sig(n,1,1/(n(n+1))) = n/(n+1)
>> n/(n+ 1.0) + 1.0/((n+ 1.0)*((n+ 1.0)+1.0)) = (n + 1.0)/(2.0 + n)


(n+3)/(3+n) + 7*3n = 234n
sig(n, 1, n+3) = n*9/3 + 5*n 2*4 + n
23x+234/(34+1)=24x-24
23x+234/(34x+1)=24x-24

xxxxxxxxxxxxxx

mahdoton 2-asteen yhtälö:
n+3*3=34n*3n+142