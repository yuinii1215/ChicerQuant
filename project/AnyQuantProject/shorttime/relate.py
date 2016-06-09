from GetData import *


def autocorrelate(data,lags):
    x=numpy.array(data.values());
    n=len(x);
    result = stsmdts.acf(x,nlags=lags);
    return result[1:];

def partialAuto(data,lags):
    x=numpy.array(data.values());
    res=stsmdts.pacf(x,lags);
    return res[1:];

def relate():
    t=sys.argv;
    data=parseDataForER(getData('sh600315', '2016-01-01', '2016-06-01'));
    ans=autocorrelate(data, 20);
    print ('acf');
    for n in ans:
        print(n);
    ans=partialAuto(data,20);
    print ('pacf');
    for n in ans:
        print (n);

if __name__ == "__main__":
    relate();