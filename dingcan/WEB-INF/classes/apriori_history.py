from pymysql import *
import numpy as np
import pickle

def demo():
    conn = connect(host='101.132.160.50', port=3306, database='order',
                   user='root',
                   password='a', charset='utf8')
    cs1 = conn.cursor()
    sql = "SELECT cid FROM history"
    cs1.execute(sql)
    datalist = []
    alldata = cs1.fetchall()
    for s in alldata:
        #     print(len(s[0].split(',')[0]))
        if len(s[0].split(',')[0]) == 5:  # 这是菜品关联 菜的id是5位数，只取订单里第一个值为5位数的一列存下来
            datalist.append(s[0])


    datalist=datalist[-200:] #取后两百条订单数据

    re = []
    for i in datalist:
        re.append(i.split(',')[:-1])


    dataSet = np.array(re)
    return dataSet
    #print(re)


def createCandidateSet(dataSet):
    result = []  # 存所有的 k-1 项集  （ 非频繁项集， 因为还没有通过最小支持度比较 ）  格式: [[1],[2],[3]]
    for transaction in dataSet:  # 取出每条事务
        for item in transaction: # 从事务中取出每个商品
            if not [item] in result:
                result.append( [item] )  #  [ [1],[3],[4],[2],[5] ]
    result.sort()
    # 这里一定要用frozenset而不是set，因为以后要将这些集合作为字典键值使用，而字典的键必须是不可变的元素，而 []是可变的，所以要转换成 { } 才行
    return map( frozenset, result)
    # map() 是 python 内置的高阶函数， 它接收一个函数 f 和一个 list, 并通过把函数 f 依次作用在 list 的每个元素上，
    # 得到一个新的 list 并返回


def scanDataSet(dataSet, candidateSet, minSupport):
    z = list(candidateSet)  # 将 map 转为 list  { frozenset{{1}}, frozenset{{2}}}
    numItems = float(len(dataSet))  # 统计总共有多少条事务记录
    candidateCounter = {}  # { 候选项集1：支持度计数, 候选集2:支持度计数}  { frozenset({1}): 3 }
    # 循环交易记录
    for tid in dataSet:
        # 循环候选项集
        for candidate in z:
            # 如果候选项是记录的一部分， 给这个候选项增加计数
            if candidate.issubset(tid):
                candidateCounter[candidate] = candidateCounter.get(candidate, 0) + 1

    resultList = []  # 存频繁项集
    supportData = {}  # 各频繁项集的支持度字典  {频繁项集指的是  大于最小支持度的项集}
    for key in candidateCounter:
        # 支持度
        support = candidateCounter[key] / numItems  # 每个候选集的次数/总事务数  ->  支持度
        if support >= minSupport:
            resultList.insert(0, key)  # 保存到resultList中
        supportData[key] = support
    return resultList, supportData


def aprioriGeneratorRequenceList(requenceList, k):
    retList = []
    requenceListLength = len(requenceList)
    for i in range(requenceListLength):
        for j in range(i + 1, requenceListLength):
            # 注意其生成的过程中，首选对每个项集按元素排序，然后每次比较两个项集，
            # 只有在前k-1项相同时才将这两项合并，这样做是因为函数并非要两两合并各个集合，
            # 那样生成的集合并非都是k+1项的， 在限制项数为k+1的前提下，
            # 只有在前k-1项相同。最后一项不相同的情况下合并才为所需要的新候选项集，
            L1 = list(requenceList[i])[:k - 2]  # [:k-2]的实际作用为取列表的前k-1个元素
            L2 = list(requenceList[j])[:k - 2]
            L1.sort()
            L2.sort()
            if L1 == L2:
                retList.append(requenceList[i] | requenceList[j])  # s/t  相当于  s.union(t)
    return retList


'''
apriori主算法的实现

返回值：L 频繁项集
        supportData:支持度列表
'''


def apriori(dataSet, minSupport):
    candidateSet = createCandidateSet(dataSet)  # 生成K-1候选项集
    L1, supportData = scanDataSet(dataSet, candidateSet, minSupport)  # 生成   频繁一项集
    L = [L1]  # L1 -> [frozenset({5}), frozenset({2}), frozenset({3}), frozenset({1})]

    k = 2
    while (len(L[k - 2]) > 0):  # [frozenset({5}), frozenset({2}), frozenset({3}), frozenset({1})]
        Ck = aprioriGeneratorRequenceList(L[k - 2], k)  # 依次生成  2,3,... 项 候选项集
        Lk, supK = scanDataSet(dataSet, Ck, minSupport)  # 依据minSpport进行剪枝， 生成  k 项频繁项集，及支持度列表
        supportData.update(supK)
        L.append(Lk)  # 将新生成的频繁项集加入到 L 中。
        #  [frozenset({5}), frozenset({2}), frozenset({3}), frozenset({1})]

        k += 1
    return L, supportData


def calcConf(freqSet, premise, supportData, rules, minConf):
    ''' 对候选规则集进行评估 '''
    prunedH = []  # 满足最小置信度要求的规则列表
    # premise的多种组合，  2->3   3->2
    for conseq in premise:
        conf = supportData[freqSet] / supportData[freqSet - conseq]
        if conf >= minConf:
            print('前提:', freqSet - conseq, ' -->结论:  ', conseq, ', 此规则置信度为:', conf)
            rules.append((freqSet - conseq, conseq, conf))
            prunedH.append(conseq)
    return prunedH  # 生成结果集合


'''
功能描述: 计算规则的置信度， 并过滤出满足最小置信度要求的规则
    参数：freqSet: k项频繁项集   frozenset([2,3])
          premise:  构件   对于  frozenset([2,3])频繁项集而言， 它的构件为  [frozenset({2}), frozenset({3})]
          supportData:  所有候选项集的支持度列表
          rules: 生成的满足最小置信度要求的关联规则
          minConf:最小置信度
'''


def rulesFromConseq(freqSet, premise, supportData, rules, minConf):
    '''生成候选规则集'''
    m = len(premise[0])
    # 每个构件有多种组合, 如 [frozenset({2}), frozenset({3})]  可以形成  2->3 , 3->2, 所以它们的都要计算置信度
    while len(freqSet) > m:  # 控制循环的次数，
        premise = calcConf(freqSet, premise, supportData, rules, minConf)
        # Apriori使用一种逐层方法来产生关联规则， 其中每层对应于规则后件的项数， 如：  {acd} ->{b}和{abd}->{c}是高置信度，
        # 则通过合并这两规则的后件产生候选规则{ad}->{bc}
        if len(premise) > 1:
            premise = aprioriGeneratorRequenceList(premise, m + 1)  # fk-1   fk-1
            m += 1
        else:  # 不能生成下一层候选关联规则， 提前退出循环
            break


'''
功能描述： 关联规则生成函数
    参数: freqSet:   频繁项集
          supportData:  所有候选项集的支持度列表
          minConf:最小置信度
    返回值： 一个包含可信度的规则列表  bigRuleList

'''


def generateRules(freqSet, supportData, minConf):
    bigRuleList = []
    for i in range(1, len(freqSet)):  # 从k2频繁项列表开始循环， 构建规则
        # i=1  [frozenset({2, 3}), frozenset({3, 5}), frozenset({2, 5}), frozenset({1, 3})]
        # i=2  [frozenset({2, 3, 5})]
        for fs in freqSet[i]:
            H1 = [frozenset([item]) for item in fs]  # fs分别为: [frozenset({2, 3}), frozenset({3, 5})
            rulesFromConseq(fs, H1, supportData, bigRuleList, minConf)  # frozenset({2}), frozenset({3})
    return bigRuleList



if __name__ == '__main__':
    dataSet = demo()

    requenceList, supportData = apriori(dataSet, minSupport=0.01)
    # print( requenceList )
    # requenceList=map(set, requenceList )
    # 3. 利用频繁项集， 及支持度列表求有效规则
    rules = generateRules(requenceList, supportData, minConf=0.2)
    a = dict()  # 创建一个字典，把rules存到字典中
    for i in rules:
        if i[0] not in a.keys(): # 如果rules中的前提不在字典的键中
            a[i[0]] = i[1]       # 就把结论存到字典中键对应的值里
        else:
            a[i[0]] = a[i[0]].union(i[1]) # 如果前提在字典中已存在，就把值合并在一起
    with open('rules.pk', 'wb') as fp:  # 用pickle把字典存到文件中
        pickle.dump(a, fp)
    print(a)



