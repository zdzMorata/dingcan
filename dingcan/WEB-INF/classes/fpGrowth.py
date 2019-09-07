from pymysql import *

conn=connect(host="101.132.160.50",port=3306,user="root",passwd="a",db="order",charset='utf8')
csl=conn.cursor()
sql="SELECT cid from history"
csl.execute(sql)
datalist=[]
alldata=csl.fetchall()
for s in alldata:
    if len(s[0].split(',')[0]) == 5: 
        datalist.append(s[0])
    
datalist=datalist[:-2]

re=[]
for i in datalist:
    re.append(i.split(',')[:-1])



#创建节点
class treeNode:
    def __init__(self,nameValue,numOccur,parentNode):
        self.name=nameValue
        self.count=numOccur
        self.nodeLink=None
        self.parent=parentNode
        self.children={}
        
    def increte(self,numOccur):
        self.count+=numOccur
            
    def display(self,level=1):
        print('  '*level,self.name,'\t',self.count)
        for child in self.children.values():
            child.display(level+1)


def updataHeader(nodeToUpdate,targetNode):
    #从头指针表的nodeLink开始，一直沿着nodeLink直到到达链表最后
    while (nodeToUpdate.nodeLink!=None):
        nodeToUpdate=nodeToUpdate.nodeLink
    #在最后加入最新的相似节点
    nodeToUpdate.nodeLink=targetNode
    
def updateTree(orderedItems,rootTree,headerTable,count):
    if orderedItems[0] in rootTree.children:
        rootTree.children[orderedItems[0]].increte(count)
    else:
        rootTree.children[orderedItems[0]]=treeNode(orderedItems[0],count,rootTree)
        
        if headerTable[orderedItems[0]][1]==None:
            headerTable[orderedItems[0]][1]=rootTree.children[orderedItems[0]]
        else:
            updataHeader(headerTable[orderedItems[0]][1],rootTree.children[orderedItems[0]])
    
    if len(orderedItems)>1:
        
        updateTree(orderedItems[1::],rootTree.children[orderedItems[0]],headerTable,count)
        
   
def createTree(dataSet,minSup=1):
    headerTable={}
    for transaction in dataSet:
        for item in transaction:
            headerTable[item]=headerTable.get(item,0)+dataSet[transaction]
    for key in list(headerTable.keys()):
        if headerTable[key]<minSup:
            del(headerTable[key])
            
    freqItemSet=set(headerTable.keys())
    if len(freqItemSet)==0:
        return None,None
    for key in headerTable:
        headerTable[key]=[headerTable[key],None]
    rootTree=treeNode('Null Set',1,None)
    
    for transaction,count in dataSet.items():
        localD={}
        for item in transaction:
            if item in freqItemSet:
                localD[item]=headerTable[item][0]
        if len(localD)>0:
            orderedItems=[v[0] for v in sorted(localD.items(),key=lambda p:p[1],reverse=True)]
            updateTree(orderedItems,rootTree,headerTable,count)
    return rootTree,headerTable

def loadSimpleData():
    simpleData=(re)
    return simpleData
def  createInitSet(dataSet):
    retDict={}
    for trans in dataSet:
        retDict[frozenset(trans)]=1
    return retDict

dataSet=loadSimpleData()


transactionDict=createInitSet(dataSet)


rootTree,headerTable = createTree(transactionDict,2)



def findPrefixPath(baseRequenceList,treeNode):
    condPats={}
    
    while treeNode!=None:
        prefixPath=[]
        ascendTree(treeNode,prefixPath)
        if len(prefixPath)>1:
            condPats[frozenset(prefixPath[1:])]=treeNode.count
        treeNode=treeNode.nodeLink
    return condPats

def ascendTree(leafNode,prefixPath):
    if leafNode.parent!=None:
        prefixPath.append(leafNode.name)
        ascendTree(leafNode.parent,prefixPath)


def mineTree(rootTree, headerTable, minSup, preFix, freqItemList):
    bigL= [v[0]for v in sorted(headerTable.items(), key=lambda p: str(p[1]))]
   
    for basePat in bigL:
        newFreqSet= preFix.copy()
        newFreqSet.add(basePat)
        freqItemList.append(newFreqSet)
        conditionPatternBases= findPrefixPath(basePat, headerTable[basePat][1])
        myCondTree, myHead = createTree(conditionPatternBases, minSup)
 
        if myHead != None:
 
            mineTree(myCondTree, myHead, minSup, newFreqSet, freqItemList)
            
bigL= [v[0]for v in sorted(headerTable.items(), key=lambda p: str(p[1]))]

myFreqList=[]
mineTree(rootTree,headerTable,7,set([]),myFreqList)
myFreqList=myFreqList[:10]

if __name__ == '__main__':
    a=list(myFreqList[0])
    b=list(myFreqList[1])
    c=list(myFreqList[2])
    d=list(myFreqList[3])
    e=list(myFreqList[4])
    f=list(myFreqList[5])
    g=list(myFreqList[6])
    h=list(myFreqList[7])
    i=list(myFreqList[8])
    j=list(myFreqList[9])
    k=a+b+c+d+e+f+g+h+i+j
    k = [ int(x) for x in k ]
    print(k)



