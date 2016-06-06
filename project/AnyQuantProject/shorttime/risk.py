from GetData import *
from numpy import sqrt

p_value=-1.96;

def downside(data):
    filted=filter(lambda x:x.real<0, data.values());
    arr=numpy.array(filted);
    arr2=arr*arr;
    sum=arr2.sum();
    return  sqrt(sum/(len(arr)));


def VaR(data):
    array=numpy.array(data.values());
    e=array.mean();
    s=array.var();
    


def risk():
    t=sys.argv;
    data=parseDataForER(getData('sh600315', '2016-03-01', '2016-05-01'));
    print(downside(data));
    return 0;

risk();