package com.growatt.shinephone.util;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssUserBean;
import com.growatt.shinephone.bean.RegisterMap;
import com.growatt.shinephone.bean.UserBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cons {
	public static String hour;//ѡ��Сʱ
	public static String min;//ѡ�����
	public static String userId="";
	public static String plant;
	public static String url="";
	public static String guestyrl="";
//	public static boolean flag=false;
	public static boolean isflag=false;
	public static ArrayList<Map<String, Object>> plants=new ArrayList<Map<String,Object>>();
	public static RegisterMap regMap=new RegisterMap();
	public static UserBean userBean=new UserBean();
	public static boolean addQuestion=false;//���ʿ���
	public static boolean isFirst=false;//�Ƿ��һ�ν���ҳ��
	public static boolean isExit=false;//�Ƿ��˳�Ӧ��
	public static int num=0;//kill����
	//��Ƶ����
	public static List<Map<String, String>> videoList=new ArrayList<Map<String,String>>();
	public static boolean isCodeUpdate;//app�汾���Ƿ���£�����б仯�򵯴�
	public static String server = "http://server.growatt.com";

	//oss登录用户实体
	public static OssUserBean ossUserBean;
	//oss服务器列表map集合
	public static List<Map<String,String>> ossServerList;

	//添加的问题类型
	public static int[] questionType = {R.string.PutingAct_etQuestion,R.string.putin_problem_item2,R.string.putin_problem_item3
			,R.string.putin_problem_item4,R.string.putin_problem_item5,R.string.putin_problem_item6
			,R.string.putin_problem_item7};
	//登录接口中是否通过手机或邮箱验证
	public static boolean isValiEmail;
	public static boolean isValiPhone ;
}
