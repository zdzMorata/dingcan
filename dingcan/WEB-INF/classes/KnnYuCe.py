# -*- coding: utf-8 -*-
import pandas as pd
import math
from collections import defaultdict
import numpy as np
from pandas import Series,DataFrame
from sklearn.neighbors import KNeighborsRegressor
import os
import sys

env_dist = os.environ
tomcatpath = env_dist.get('CATALINA_HOME')
stop_txt_path = tomcatpath+os.path.sep+'webapps'+os.path.sep+'dingcan'+os.path.sep+'WEB-INF'+os.path.sep+'classes'+os.path.sep



def yuce(x_test):
    train=pd.read_csv(stop_txt_path+'caidan.csv')   # dataframe
    #train.head(5)    # tail
    
    train=train.drop(['Unnamed: 0','cname','cphoto'],axis=1)
    #print(train.head(5))
    
    train['caixi']=train['caixi'].replace('徽菜','1')
    train['caixi']=train['caixi'].replace('苏菜','2')
    train['caixi']=train['caixi'].replace('粤菜','3')
    train['caixi']=train['caixi'].replace('川菜','4')
    train['caixi']=train['caixi'].replace('浙菜','5')
    train['caixi']=train['caixi'].replace('鲁菜','6')
    train['caixi']=train['caixi'].replace('湘菜','7')
    train['caixi']=train['caixi'].replace('闽菜','8')
    
    train['leix']=train['leix'].replace('其他类','1')
    train['leix']=train['leix'].replace('肉类','2')
    train['leix']=train['leix'].replace('海鲜类','3')
    train['leix']=train['leix'].replace('鱼类','4')
    #print(train.head(5))
    
    
    Y=train['cprice']
    X=train.drop(['cprice','cid','didan'],axis=1)
    #x_test=pd.DataFrame([['1','1','67']])
    #print(X)
    #print(Y)
    knn = KNeighborsRegressor()
    knn.fit(X,Y)
    y_pre_knn = knn.predict(x_test)
    return y_pre_knn

def zhuanhuan(caixi):
    if caixi=='徽菜':
        x='1'
    if caixi=='苏菜':
        x='2'
    if caixi=='粤菜':
        x='3'
    if caixi=='川菜':
        x='4'
    if caixi=='浙菜':
        x='5'
    if caixi=='鲁菜':
        x='6'
    if caixi=='湘菜':
        x='7'
    if caixi=='闽菜':
        x='8'
    return x

def leixi(leix):
    if leix=='其他类':
        y='1'
    if leix=='肉类':
        y='2'
    if leix=='海鲜类':
        y='3'
    if leix=='鱼类':
        y='4'
    return y

if __name__ =='__main__':
    if len(sys.argv)>3:
        caixi=sys.argv[1]
        leix=sys.argv[2]
        lirun=sys.argv[3]
    x=zhuanhuan(caixi)
    y=leixi(leix)
    x_test=pd.DataFrame([[x,y,lirun]])
    result=yuce(x_test)
    res=float(result)
    print(res)
