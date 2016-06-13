from GetData import *


def autocorrelate(data,lags):
    x=numpy.array(data.values());
    result = stsmdts.acf(x,nlags=lags);
    return result;

def partialAuto(data,lags):
    x=numpy.array(data.values());
    res=stsmdts.pacf(x,lags);
    return res;

def relate():
    t=sys.argv;
    # t[1], t[2], t[3]
    # 'sh600315', '2016-03-01', '2016-06-01'
    data=parseDataForER(getData(t[1], t[2], t[3]));
    ans=autocorrelate(data, 20);
    n=len(data);
    print (1.96/sqrt(n));
    for n in ans:
        print(n);
    ans=partialAuto(data,20);
    print ('pacf');
    for n in ans:
        print (n);

if __name__ == "__main__":
    relate();