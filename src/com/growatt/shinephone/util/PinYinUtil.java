package com.growatt.shinephone.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
	/**
	 * ���غ���ƴ����ʽ������
	 * "����"������"BEIJING"
	 * "��" 
	 * @param name
	 * @return
	 */
	public static String getPinYin(String name){
		String result="";
		try {
			//ʹ��pinyin4������תƴ������
			//1)�趨��ƴ���ĸ�ʽ
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			//2)����һ����̬�������յ�һ���趨�ĸ�ʽ����ת��
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<name.length();i++){
				//������תƴ��֮ǰҪ��һ���жϣ���һ�µ�ǰҪת�����ַ����ǲ��Ǻ���
				String string = name.substring(i,i+1);
				if(string.matches("[\u4E00-\u9FFF]")){
					//"DAN","SHAN"
					String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(name.charAt(i), format);
					sb.append(pinyin[0]);
				}else{
					sb.append(string);
				}
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
