from GetData import *
from relate import autocorrelate
from relate import partialAuto
import statsmodels.stats.api



def getpq(data):
    le = len(data);
    for i in range(0, le):
        if abs(data[i]) < 0.05:
            return i-1;
    return le-1;


quick = [-2, -1, 0, 1, 2];


def arma():
    t = sys.argv;
    src=getData('sh600315', '2016-04-01', '2016-06-01');
    data=parseDataForER(src);
    acf = autocorrelate(data, 20);
    pacf = partialAuto(data, 20);
    pm = getpq(pacf);
    qm = getpq(acf);
    mi = float('inf');
    p = 0;
    q = 0;
    finalmod=None;
    dta=parseTimeER(src);
    dtf=pandas.DataFrame(dta, columns=['Date', 'DateValue']);

    dtf['Date']=pandas.to_datetime(dtf['Date'],format='%Y-%m-%d');

    for i in range(2, 3):
        for j in range(2, 3):
            pt = pm + quick[i];
            qt = qm + quick[j];
            arma_mod = stsmdts.ARMA(dtf['DateValue'], (pt, qt),dates=dtf['Date']).fit();
            if arma_mod.aic < mi:
                mi = arma_mod.aic;
                p = pt;
                q = qt;
                finalmod=arma_mod;
    print (finalmod.aic);
    print (p, q);
    print(statsmodels.stats.api.durbin_watson(finalmod.resid.values));#1-d correlation
    array = numpy.array(finalmod.resid.values);
    res = stsmdsd.kstest_normal(array);
    print(res[1]);
    res = stsmdsd.acorr_ljungbox(array,12);
    print (res[1]);
    le=len(dtf['Date']);
    pre=finalmod.predict(start=dtf['Date'][le-3],end=dtf['Date'][le-1],dynamic=True);
    print (pre);
    return 0;


if __name__ == "__main__":
    arma();
