import tushare as ts
import pandas as pd


def getMap():
    df=pd.read_csv('industry.csv')
    df.set_index('code' , drop=True, inplace=True)
    return df

def getCompany_info():
    df=pd.read_csv('stock_info.csv')
    df['timeToMarket']=pd.to_datetime(df['timeToMarket'], format='%Y%m%d',errors='ignore')
    df=df.rename(columns={'code':'stock_id','name':'stock_name'})
    return df
