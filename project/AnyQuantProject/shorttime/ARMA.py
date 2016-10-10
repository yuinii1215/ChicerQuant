from GetData import *
from relate import autocorrelate
from relate import partialAuto
import statsmodels.stats.api
from datetime import datetime,timedelta

def parse_ymd(s):
    year_s, mon_s, day_s = s.split('-')
    return datetime(int(year_s), int(mon_s), int(day_s))


def getpq(data):
    le = len(data);
    for i in range(0, le):
        if abs(data[i]) < 0.05:
            return i-1;
    return le-1;

def QQ(array):
    array.sort();
    le=len(array);

    array.sort();
    y=[0]*le;
    for num in range(0,le):
        y[num]=(num+0.5)/le;
    y=numpy.array(y);
    y=stats.norm.ppf(y);
    ans={};
    for num in range(0,le):
        ans[array[num]]=y[num];
    return ans;

quick = [-2, -1, 0, 1, 2];


def arma():
    t = sys.argv;
    # 'sh600315','2016-04-01','2016-06-16' t[1], t[2], t[3]
    src=getData('sh600000','2016-06-01','2016-09-30');


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
    time = parse_ymd(dta[len(dta)-1][0][:10]);
    quse=[time]*3;
    for num in range(1, 4):
        usetime = time + timedelta(days=num);
        tempt = (usetime.strftime('%Y-%m-%d'), 0);
        quse[num-1]=usetime;
        dta.append(tempt);
    dtf=pandas.DataFrame(dta, columns=['Date', 'DateValue']);

    dtf['Date']=pandas.to_datetime(dtf['Date'],format='%Y-%m-%d');
    for i in range(2, 3):
        for j in range(2, 3):
            pt = pm + quick[i];
            qt = qm + quick[j];
            # try:
            arma_mod = stsmdts.ARMA(dtf['DateValue'], (pt, qt),dates=dtf['Date']).fit();
            finalmod = arma_mod;
            if arma_mod.aic < mi:
                mi = arma_mod.aic;
                p = pt;
                q = qt;
                finalmod=arma_mod;
            # except:
            #     continue;
    print ('begin')
    print(statsmodels.stats.api.durbin_watson(finalmod.resid.values));#1-d correlation
    array = numpy.array(finalmod.resid.values);
    res = stsmdsd.kstest_normal(array);
    print(res[1]);
    print ('e')
    evalue=QQ(array);
    for k,v in evalue.items():
        print (k*100)
        print (v)
    res = stsmdsd.acorr_ljungbox(array,12);
    print ('ljungbox');
    for value in res[1]:
        print (value);
    le=len(dtf['Date']);
    pre=finalmod.predict(start=dtf['Date'][le-3],end=dtf['Date'][le-1],dynamic=True);
    print ('predict')

    for num in range(0,len(dta)-3):
        print (dta[num][0])
        print (dta[num][1])
    for num in range(0,3):
        print (quse[num].strftime("%Y-%m-%d"))
        print (pre[num]);

    return 0;


if __name__ == "__main__":
    arma();
