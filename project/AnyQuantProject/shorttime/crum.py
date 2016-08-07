from GetData import *

def err(p,x,y):
    k,b=p;
    return k*x+b-y;

def crum():
    t=sys.argv;
    rp=parseDataForER(getData(t[1],t[2],t[3])).values();
    rf=float(t[4]);
    rm=parseDataForER(getData('sh000300', t[2],t[3])).values();
    if len(rp)>len(rm):
        rp=rp[:len(rm)]
    elif len(rp)<len(rm):
        rm=rm[:len(rp)]

    for num in range(0,len(rp)):
        rp[num]-=rf;
        rm[num]-=rf;

    rp = numpy.array(rp);
    rm = numpy.array(rm);
    model=opt.leastsq(err, [0.1,0.1], args=(rp,rm));
    k,b=model[0];
    print(k);
    print(b);
    for num in range(0,len(rp)):
        print rp[num];
        print rm[num];
    return 0;

if __name__ == '__main__':
    crum();