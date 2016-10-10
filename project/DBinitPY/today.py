#! /usr/bin/env python
# -*- coding: utf-8 -*-
from sql import getE

def daily(now):
    import pandas
    import datetime
    from sql import getCursor,insertStock
    from stock import oneStock,getStockList
    import pandas.io.sql as pdsql
    before=now-datetime.timedelta(700)
    list=getStockList()
    total=len(list)
    today=pandas.DataFrame()
    count=1
    for v in list:
        print str(count)+'/'+str(total),'start',v
        try:
            df=oneStock(v,starttime=before,endtime=now)
            df=df.iloc[[-1]]
            eng=getE()
            pandas.DataFrame.replace(df,{float('inf'):0,float('NaN'):0})
            pdsql.to_sql(df, 'today', eng,  if_exists='append',index=False,index_label='stock_id')
    
        except Exception:
            count+=1
            print Exception.message
            print v,'error'
            continue
        print str(count)+'/'+str(total),'end',v
        count+=1
    
    today.fillna(0, inplace=True)
    pandas.DataFrame.replace(today,{float('inf'):0,float('NaN'):0})
    pdsql.to_sql(today, 'today', eng,  if_exists='replace',index=False,index_label='stock_id',chunksize=100)
    return


if __name__ == '__main__':
    import sys
    import tushare as ts
    from datetime import datetime
    t=sys.argv
    if len(t)>1:
        time=datetime.strptime(t[1],'%Y-%m-%d')
    else:
        time=datetime.now()
        
    print datetime.strftime(time,format='%Y-%m-%d')
    if ts.is_holiday(date=datetime.strftime(time,format='%Y-%m-%d')) :
        print 'is holiday'
    else:
        daily(time)
        
        
