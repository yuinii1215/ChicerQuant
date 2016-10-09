#! /usr/bin/env python
# -*- coding: utf-8 -*-
from __main__ import ERROR

def daily(now):
    import datetime
    from sql import getCursor,insertStock
    from stock import oneStock,getStockList
    import pandas.io.sql as pdsql
    before=now-datetime.timedelta(700)
    list=getStockList()
    total=len(list)
    count=1
    for v in list:
        print str(count)+'/'+str(total),'start',v
        try:
            df=oneStock(v,starttime=before,endtime=now)
            df=df.iloc[[-1]]
            [conn,cur]=getCursor()
            pdsql.to_sql(df, v, conn, flavor='mysql',  if_exists='append',index_label='date')
        except Exception:
            count+=1
            print Exception
            print v,'error'
            continue
        print str(count)+'/'+str(total),'end',v
        count+=1
    return

def test(now):
    import datetime
    from sql import getCursor,insertStock
    from stock import oneStock,getStockList
    import pandas.io.sql as pdsql
    before=now-datetime.timedelta(700)
    v='sh600216'
    df=oneStock(v,starttime=before,endtime=time)
    df=df.iloc[[-1]]
    [conn,cur]=getCursor()
    pdsql.to_sql(df, v, conn, flavor='mysql',  if_exists='append',index_label='date')
            
        
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
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

