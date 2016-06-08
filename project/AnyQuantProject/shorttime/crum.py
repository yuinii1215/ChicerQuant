from GetData import *

def err(p,x,y):
    k,b=p;
    return k*x+b-y;

def crum():
    t=sys.argv;
    rp=parseDataForER(getData(t[1], t[2], t[3]));
    rf=getInterestRate(t[2], t[3]);
    rm=parseDataForER(getData('hs300', t[2], t[3]));
    rp=numpy.array(rp);
    rm=numpy.array(rm);
    rf=numpy.array(rf);
    y=rp-rf;
    x=rm-rf;
    model=opt.leastsq(err, [0.1,0.1], args=(x,y));
    k,b=model[0];
    print(k);
    print(b);
    return 0;