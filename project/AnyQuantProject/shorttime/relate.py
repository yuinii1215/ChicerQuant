from GetData import *


def autocorrelate(data,lags):
    x=numpy.array(data.values());
    n=len(x);
    result = [numpy.correlate(x[i:]-x[i:].mean(),x[:n-i]-x[:n-i].mean())[0]\
        /(x[i:].std()*x[:n-i].std()*(n-i)) \
        for i in range(1,lags+1)]
    return result;


def relate():
    t=sys.argv;
    data=parseDataForER(getData('sh600315', '2016-03-01', '2016-06-01'));
    ans=autocorrelate(data, 10);
    for n in ans:
        print(n);
        
relate();