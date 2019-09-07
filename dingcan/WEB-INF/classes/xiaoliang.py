# -*- coding: utf-8 -*-
import pymysql
import numpy as np

pymysql.install_as_MySQLdb()
conn=pymysql.connect(
        host='101.132.160.50',
        port=3306,
        user='root',
        passwd='a',
        db='order',
        charset='utf8'
        )
cur=conn.cursor()
sql='select cname,caixi,cprice,leix,didan from caidan'
cur.execute(sql)
dataSet=cur.fetchall()
#print((dataSet))


class decisionnode:
    def __init__(self,col=-1,value=None,results=None,tb=None,fb=None):
        self.col=col
        self.value=value
        self.results=results
        self.tb=tb
        self.fb=fb

def divideset(rows,column,value):
    split_function=None
    if isinstance(value,int) or isinstance(value,float):
        split_function=lambda row:row[column]>=value
    else:
        split_function=lambda row:row[column]==value
    set1=[row for row in rows if split_function(row)]
    set2=[row for row in rows if not split_function(row)]
    
    return (set1,set2)

#对每种可能的结果标签进行计数
def uniquecount(rows):
    result={}
    for row in rows:
        label=row[len(row)-1]
        result[label]=result.get(label,0) +1
    return result

#熵的算法
from math import log
def entropy(rows):
    result=uniquecount(rows)
    shannonEnt=0
    #计算熵
    for key in result.keys():
        p=float(result[key]/ len(rows))
        shannonEnt-=p*log(p,2)
    return shannonEnt


#基尼系数：随机放置的数据出现于错误分类的概率
def gini(rows):
    totalrows=len(rows)
    result=uniquecount(rows)
    impurity=0
    for k1 in result.keys():
        p1=float(result[k1])/totalrows
        for k2 in result.keys():
            if k1==k2:
                continue
            p2=float(result[k2])/totalrows
            impurity+=p1*p2
    return impurity


#构建数
def buildtree(rows,scoref=entropy):
    if len(rows)==0:
        return decisionnode()
    
    current=scoref(rows)  #计算熵与基尼不纯度
    #用于划分最优特征的参数
    best_gain=0.0
    best_criteria=None
    best_set=None
    
    column_count=len(rows[0])-1  #列数
    #循环每个列
    for col in range(0,column_count):
        column_value={}
        for row in rows:
            column_value[row[col]]=1
        #安照第col列  value 切分数据集
        for value in column_value.keys():
            (set1,set2)=divideset(rows,col,value)
            
            #计算信息增益
            p=float(len(set1))/len(rows)
            gain=current-p*scoref(set1)-(1-p)*scoref(set2)
            if gain>best_gain and len(set1)>0 and len(set2)>0:
                best_gain=gain   #最佳信息增益
                best_criteria=(col,value)   #最优切分的列和值
                best_set=(set1,set2)  #切分好的数据集
                
    #根据上面找到的最优列来递归切分数据集
    if best_gain>0:
        trueBranch=buildtree(best_set[0])
        falseBranch=buildtree(best_set[1])
        return decisionnode(col=best_criteria[0],value=best_criteria[1],
                           tb=trueBranch,fb=falseBranch)
    else:
        return decisionnode(results=uniquecount(rows)) #叶子节点不能再分所以节点有值
    
#前序输出显示数
def printtree(tree,indent=' '):
    if tree.results !=None:
        print(indent,str(tree.results))
    else:
        print(indent,str(tree.col)+':'+str(tree.value)+'?')
        print(indent+'T->')
        printtree(tree.tb,indent+' ')
        print(indent+'F->')
        printtree(tree.fb,indent+' ')
        
#分类
def classify(observation,tree):
    #如果有results,说明是叶节点，那就直接取值
    if tree.results !=None:
        return tree.results
    else:
        v=observation[tree.col]
        branch=None
        if isinstance(v,int) or isinstance(v,float):
            if v>=tree.value:
                branch=tree.tb
            else:
                branch=tree.fb
        else:
            if v==tree.value:
                branch=tree.tb
            else:
                branch=tree.fb
        return classify(observation,branch)
    

    
    
if __name__=='__main__':
    
    import sys
    if len(sys.argv)>3:
        name=sys.argv[1]
        caixi=sys.argv[2]
        price=sys.argv[3]
        leix=sys.argv[4]
        rows=[]
        for i in dataSet:
        
            row=list(i)
            rows.append(row)
    
        tree=buildtree(rows)
        t=classify([name,caixi,price,leix],tree)
        print(list(t.keys()))
   
    if len(sys.argv) in [1,2,3]:
        taocan=sys.argv[1]
        price=sys.argv[2]
        sql2='select taocan,cprice,zuoshu,didan from history where hid>%s'
        cur.execute(sql2,(6021))
        data2=cur.fetchall()
        rows2=[]
        for j in data2:
            row=list(j)
            cprice=row[1]/row[2]
            didan=row[3]*row[2]
            list1=[]
            list1.append(row[0])
            list1.append(cprice)
            list1.append(didan)
            rows2.append(list1)
        tree2=buildtree(rows2)
        t2=classify([taocan,price],tree2)
        m=list(t2.keys())
        print(int(np.mean(m)))
    

