from GetData import *
from numpy import sqrt

p_value=-1.96;

def downside(data):
    filted=filter(lambda x:x.real<0, data.values());
    arr=numpy.array(filted);
    arr2=arr*arr;
    summ=arr2.sum();
    return  sqrt(summ/(len(arr)));


def VaR(data):
    array=numpy.array(data.values());
    e=array.mean();
    s=array.var()*(len(array)/(len(array)-1));
    varr=-(p_value*sqrt(s)+e);
    return varr;

def risk():
    t=sys.argv;
    data=parseDataForER(getData(t[1], t[2], t[3]));
    print(downside(data));
    print(VaR(data));
    return 0;

if __name__ == '__main__':
    risk();