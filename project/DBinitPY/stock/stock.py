from datetime import datetime
from AnyQuant import getSingleStock, getStockList
from pdCal import *

def oneStock(id):
    starttime=datetime(2016,1,1)
    endtime=datetime.now()
    df=getSingleStock(id,starttime,endtime)
    ema(df)
    rsi(df)
    bias(df)
    kdj(df)
    macd(df)
    poly(df)
    return

oneStock('sh600000')

def init():
    list=getStockList()
    total=len(list)
    count=1
    for v in list:
        print count+'/'+total,'start',v
        oneStock(v)
        print count+'/'+total,'end',v
    return