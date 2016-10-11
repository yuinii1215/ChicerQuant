# -*- coding: utf-8 -*-
from datetime import datetime
from AnyQuant import getBench
from pdCal import *
from sql import getE, setPrimaryKey
from industData import getMap
import pandas.io.sql as pdsql
def oneStock(id='sh000300',starttime=datetime(2016,1,1),endtime=datetime.now()):
    df=getBench(id,starttime,endtime)
    color(df)
    kdj(df)
    updown(df)
    ema(df)
    rsi(df)
    bias(df)  
    macd(df)
    poly(df)
    df.fillna(0,inplace=True)
    df['stock_id']=id
    df['stock_name']=u'沪深300'
    cols = df.columns.tolist()
    cols = cols[-3:] + cols[:-3]
    df=df[cols]
    eng=getE()
    pdsql.to_sql(df, id, eng,  if_exists='append',index_label='date')
    return 
if __name__ == '__main__':
    oneStock()

