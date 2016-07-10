import tushare as ts
import pandas as pd


def getMap(dir=''):
    df=pd.read_csv(dir+'industry.csv')
    df.set_index('code' , drop=True, inplace=True)
    return df

def getCompany_info(dir=''):
    df=pd.read_csv(dir+'stock_info.csv')
    df['timeToMarket']=pd.to_datetime(df['timeToMarket'], format='%Y%m%d',errors='ignore')
    df=df.rename(columns={'code':'stock_id','name':'stock_name'})
    df.set_index('stock_id', drop=True, inplace=True)
    return df