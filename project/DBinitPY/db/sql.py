# -*- coding: UTF-8 -*-
import MySQLdb as mdb


ins_str='INSERT INTO '
cre_str='CREATE TABLE IF NOT EXISTS '

def getCursor():
    conn=mdb.connect(host='127.0.0.1',user='chicer',passwd='chicer2016',db='chicer',port=3306,charset='utf8')
    cursor=conn.cursor()
    return [conn,cursor]
 
def createIndustry(table_name):
    [conn,cursor]=getCursor()
    tail='''( industry_name varchar(20) not null , 
    date date not null, 
    open double default 0 , 
    close double default 0 , 
    max double default 0 , 
    min double default 0 , 
    volumn bigint default 0 , 
    updown double default 0 , 
    pure double default 0 , 
    total double default 0 , 
    companySum int default 0 , 
    leader varchar(10), 
    leaderName varchar(20), 
    leaderPrice double default 0 , 
    leaderUpdown double default 0 , 
    primary key(date) 
    ) 
    ENGINE=InnoDB DEFAULT CHARSET=utf8;'''
    try:
        cursor.execute(cre_str+table_name+tail)
        conn.commit()
        cursor.close()
        conn.close()
    except mdb.Error,e:
        print "Mysql Error %d: %s" % (e.args[0], e.args[1])
    return

def insertIndustry(table_name,data):
    [conn,cursor]=getCursor()
    tail='''
     ( industry_name , date , open , close , max , min , volumn , updown , pure , total ,companysum ,
    leader , leaderName , leaderPrice , leaderUpdown ) VALUES (
    %s, %s, %f, %f, %f, %f, %d, %f, %f, %f, %d, %s, %s, %f, %f );
    '''
    try:
        cursor.execute(ins_str+table_name+tail,data)
        conn.commit()
        cursor.close()
        conn.close()
    except mdb.Error,e:
        print "Mysql Error %d: %s" % (e.args[0], e.args[1])
    return

def createStock(table_name):
    [conn,cursor]=getCursor()
    tail='''
     ( date date not null, stock_id varchar(10) not null, stock_name varchar(20),
     open double default 0, high double default 0, low double default 0, close double default 0,
     volumn int default 0, adj_price double default 0, turnover double default 0, pe_ttm double default 0,
     pb double default 0, industry varchar(20), PMA5_day double default 0, PMA10_day double default 0,
     PMA30_day double default 0, PMA5_week double default 0, PMA10_week double default 0,
     PMA30_week double default 0,
     PMA5_month double default 0, PMA10_month double default 0, PMA30_month double default 0,
     RSI6 double default 0, RSI12 double default 0, RSI24 double default 0, BIAS6 double default 0,
     BIAS12 double default 0, BIAS24 double default 0, K double default 0, D double default 0,
     J double default 0, DIF double default 0, DEA double  default 0, MACDBar double default 0,
     poly double default 0, primary key(date)) 
     ENGINE=InnoDB DEFAULT CHARSET=utf8;
    '''
    try:
        cursor.execute(cre_str+table_name+tail)
        conn.commit()
        cursor.close()
        conn.close()
    except mdb.Error,e:
        print "Mysql Error %d: %s" % (e.args[0], e.args[1])
    return


def insertStock(table_name,data):
    [conn,cursor]=getCursor()
    tail='''
     ( date, stock_id, stock_name, open, high, low, close, volumn, adj_price, turnover, pe_ttm, pb,
     industry, PMA5_day, PMA10_day, PMA30_day, PMA5_week, PMA10_week, PMA30_week, PMA5_month, PMA10_month,
     PMA30_month, RSI6, RSI12, RSI24, BIAS6, BIAS12, BIAS24, K, D, J, DIF, DEA, MACDBar, poly) VALUES (
     %s, %s, %s, %f, %f, %f, %f, %d, %f, %f, %f, %f, %s, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f,
     %f, %f, %f, %f, %f, %f, %f, %f, %f, %f )
    '''
    for d in data:
        try:
            cursor.execute(ins_str+table_name+tail,d)
        except mdb.Error,e:
            continue
    conn.commit()
    cursor.close()
    conn.close()
    return 

def setPrimaryKey(table_name):
    sql='ALTER TABLE '+table_name+' ADD PRIMARY KEY(`date`);'
    [conn,cursor]=getCursor()
    try:
        cursor.execute(sql)
        conn.commit()
    except mdb.Error,e:
        print "Mysql Error %d: %s" % (e.args[0], e.args[1])
    finally:
        cursor.close()
        conn.close()
    return 
        
    
