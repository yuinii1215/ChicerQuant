from GetData import *;
import  statsmodels.tsa.stattools as ts
def balanceTest():
    t=sys.argv;
    # data = parseDataForER(getData('sh600315', '2016-04-01', '2016-06-01'));
    er=parseDataForER(getData(t[1],t[2],t[3]));
    array=numpy.array(er.values());
    res=ts.adfuller(array);
    print (res[0]);
    print (res[4]['10%']);
    print (res[4]['5%']);
    print (res[4]['1%']);
    return 0;

if __name__ == '__main__':
    balanceTest();