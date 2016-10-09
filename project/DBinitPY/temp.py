from Daily import *
from datetime import datetime,timedelta
import tushare as ts
now=datetime.now()
use=datetime(2016,9,8)
while use<now:
    if ts.is_holiday(date=datetime.strftime(use,format='%Y-%m-%d')) :
        print 'is holiday'
    else:
        daily(use)
    use=use+timedelta(1)
    
    print datetime.strftime(use,format='%Y-%m-%d')