#! /usr/bin/env python
# -*- coding: utf-8 -*-



if __name__ == '__main__':
    import pandas as pd
    import sys
 
    if len(sys.argv) < 2:
        print("error: file not found")
        sys.exit(1)
    chose=['alpha','sharpe','annualized_returns','benchmark_annualized_returns','benchmark_daily_returns',
           'benchmark_total_returns','daily_returns','total_returns']
    direction = sys.argv[1]  # xx/xx/xx.pickle 'result.pkl'
    perf = pd.read_pickle(direction)
    
    direction = direction[:len(direction)-4]+'.json'
#     print perf
    df=perf[chose]
    rj=df.to_json(date_format='iso',double_precision=3,force_ascii=False,orient='index')
    print rj
