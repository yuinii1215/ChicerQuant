from datetime import datetime
from AnyQuant import getSingleStock, getStockList
from pdCal import *
from sql import getCursor
from industData import getMap
import pandas.io.sql as pdsql

__industry_name_=getMap(dir='../datasrc/')
def query(id):
    idr=id[2:]
    num=int(idr)
    t=__industry_name_.query('code=='+str(num))
    a=t.at[num,'name']
    b=t.at[num,'c_name']
    return a,b
def oneStock(id):
    starttime=datetime(2016,1,1)
    endtime=datetime.now()
    df=getSingleStock(id,starttime,endtime)
    kdj(df)
    ema(df)
    rsi(df)
    bias(df)
    
    macd(df)
    poly(df)
    df.fillna(0,inplace=True)
    c_name,i_name=query(id)
    df['stock_id']=id
    df['stock_name']=c_name
    df['industry']=i_name
    cols = df.columns.tolist()
    cols = cols[-3:] + cols[:-3]
    df=df[cols]
#     print df
    [conn,cur]=getCursor()
    pdsql.to_sql(df, id, conn, 'mysql')
    return
def init():
    list=getStockList()
    total=len(list)
    count=1
    for v in list:
        print count+'/'+total,'start',v
        oneStock(v)
        print count+'/'+total,'end',v
    return