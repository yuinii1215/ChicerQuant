# -*- coding: UTF-8 -*-
import MySQLdb as mdb



def getCursor():
    conn=mdb.connect(host='127.0.0.1',user='chicer',passwd='chicer2016',db='chicer',port=3306,charset='utf8')
    cursor=conn.cursor()
    return [conn,cursor]
 
def createIndustry(table_name):
    [conn,cursor]=getCursor()
    cre_str='CREATE TABLE IF NOT EXISTS '
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
    ins_str='INSERT INTO '
    tail=''' ( industry_name , date , open , close , max , min , volumn , updown , pure , total ,companysum ,
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


