# -*- coding: UTF-8 -*-
from industData import getMap, getCompany_info
import datetime
import pandas as pd
import numpy as np
from AnyQuant import getSingleStock
from pdCal import updown
from sql import getCursor,setPrimaryKey
import pandas.io.sql as pdsql

def apply_lambda(df):
    ans=df.sort_values(by='updown')[-1:]
    if ans['updown'].item()==float('inf'):
        ans['updown']=float(0)
    return ans

def transTable(id):
    idr=str(id).zfill(6)
    if id>=600000:
        idr='sh'+idr
    else:
        idr='sz'+idr
    return idr

__company_info__=getCompany_info()


def oneIndustry(industry,i_c):
    set=pd.DataFrame()
    for id in i_c.index:
        te=__company_info__.query('stock_id=='+str(id))
        set=set.append(te)
    pure=set['outstanding'].sum()*10000
    pure=int(pure)
    total=int((set['outstanding']*set['bvps']).sum()*10000)
    set['percent']=(set['outstanding']*set['bvps']*10000)/total
    count=len(set.index)
    #get src
    starttime=datetime.datetime(2006,1,1)
    endtime=datetime.datetime.now()
    use=[]
    for id in i_c.index:
        te=getSingleStock(transTable(id),starttime,endtime)
        updown(te)
        te.reset_index(inplace=True)
        te['stock_id']=id
        resu=pd.merge(left=te[['stock_id','open','high','close','low','volume','updown','date']],right=set[['stock_id','stock_name','percent']],on='stock_id')
        resu['open']=resu['open']*resu['percent']
        resu['realclose']=resu['close']
        resu['close']=resu['close']*resu['percent']
        resu['high']=resu['high']*resu['percent']
        resu['low']=resu['low']*resu['percent']
        resu['volume']=resu['volume']*resu['percent']
        use.append(resu)
    data=use[0]
    for i in range(1,len(use)):
        data=data.append(use[i])
    data.fillna(0,inplace=True)
    df1=data[['open','high','low','close','volume']].groupby(data['date']).sum()
    df1.reset_index(level=0, inplace=True)
    df2=data[['stock_name','updown','stock_id','date','realclose']].groupby(data['date'],as_index=False).apply(apply_lambda)
    df=pd.merge(left=df1,right=df2,on='date')
    df['industry_name']=industry
    df['pure']=pure
    df['total']=total
    df['companySum']=count
    df = df.rename(columns={'volume': 'volumn', 'stock_name': 'leaderName','stock_id':'leader',
                            'realclose':'leaderPrice','updown':'leaderUpdown'})
    df.set_index('date', drop=True, inplace=True)

    [conn, cur] = getCursor()
    pd.DataFrame.replace(df,{float('inf'):0})
    pdsql.to_sql(df, unicode(industry), conn, flavor='mysql')
    setPrimaryKey(industry)
    return
    
    


def init():
    all=getMap()
    ans=all.c_name.unique()
    for v in ans:
        print v,'start'
        try:
            oneIndustry(v,all[all['c_name']==v])
        except:
            continue


    return

if __name__ == '__main__':
    import sys

    reload(sys)
    sys.setdefaultencoding('utf8')
    init()
