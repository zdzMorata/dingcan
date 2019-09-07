import pickle
import sys

import os

env_dist = os.environ
tomcatpath = env_dist.get('CATALINA_HOME')
stop_txt_path = tomcatpath+os.path.sep+'webapps'+os.path.sep+'dingcan'+os.path.sep+'WEB-INF'+os.path.sep+'classes'+os.path.sep


def tuijian(cids):
		#print(type(cids))
		
		with open(stop_txt_path+'rules.pk', 'rb') as fp:  #读取文件
			rules = pickle.load(fp)
		s = cids  #  按 18526,18251,18373,18366
		str = s.split(',')    # 按逗号分隔
		#print(str)
		li = [i for i in str]
		#print(li)
		demo = frozenset(li)    #  转成forzenset()
		result = [int(i) for i in rules[demo]]   # 传进来的菜号转成forzenset后在字典里匹配相应的值，并把结果转成整型数字的列表
		return result

if __name__ == '__main__':
	cids=[]
	if len(sys.argv)>1:
		cids=sys.argv[1]
	result=tuijian(cids)
	print(result)