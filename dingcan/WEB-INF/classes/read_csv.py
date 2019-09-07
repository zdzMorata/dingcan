# -*- coding: utf-8 -*-
import pandas as pd
import pymysql

conn = pymysql.connect(host='101.132.160.50', 
               user='root',
               password='a', 
               db='order',
               charset='utf8',
               use_unicode=True)

sql = 'select * from caidan'
df = pd.read_sql(sql, con=conn)
print(df.head())

df.to_csv("data.csv")
conn.close()
