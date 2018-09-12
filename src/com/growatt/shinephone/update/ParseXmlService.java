package com.growatt.shinephone.update;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.Locale;


public class ParseXmlService {
	
	public UpdateInfo parseXml(InputStream xmlStream) throws Exception {
		
		UpdateInfo mUpdateInfo = null;
		String mSysLocale = getSysLocale();
		String mDefaultLog = "";
		
		// 实例化一个XML构建器工厂
		XmlPullParserFactory mXmlFactory = XmlPullParserFactory.newInstance(); 

		// 通过XML构建器工厂获取一个XML构建器
		XmlPullParser mXmlParser = mXmlFactory.newPullParser();
		
		// 输入XML文件流
		mXmlParser.setInput(xmlStream, "UTF-8");
		
		//根据PULL解析器的事件进行相应处理
		int mXmlEventType = mXmlParser.getEventType();
		
		
		while(mXmlEventType != XmlPullParser.END_DOCUMENT){
			switch(mXmlEventType){
				case XmlPullParser.START_DOCUMENT:
					mUpdateInfo = new UpdateInfo();
					break;
					
				case XmlPullParser.START_TAG:
					//版本号
					if(mXmlParser.getName().equals("version-code")){
						
						mUpdateInfo.setVersionCode(Integer.valueOf(mXmlParser.nextText()));
					}
					
					//版本名称
					else if(mXmlParser.getName().equals("version-name")){
						mUpdateInfo.setVersionName(mXmlParser.nextText());
					}
					
					//文件大小
					else if(mXmlParser.getName().equals("file-size")){
						mUpdateInfo.setFileSize(mXmlParser.nextText());
					}
					
					//下载地址
					else if(mXmlParser.getName().equals("download-url")){
						mUpdateInfo.setDownloadURL(mXmlParser.nextText());
					}

					//热更新版本
					else if(mXmlParser.getName().equals("version-code-tinker")){
						mUpdateInfo.setTinkerCode(Integer.valueOf(mXmlParser.nextText()));
					}
					//热更新url
					else if(mXmlParser.getName().equals("download-url-tinker")){
						mUpdateInfo.setTinkerUrl(mXmlParser.nextText());
					}

					//更新日志
					else if(mXmlParser.getName().equals("language")){
						
						//判断语言和地区
						String LocaleInfo = mXmlParser.getAttributeValue(0);
						if(LocaleInfo.equalsIgnoreCase("default")){
							mDefaultLog = mXmlParser.nextText();
						}
						
						if(LocaleInfo.equalsIgnoreCase(mSysLocale)){
							mUpdateInfo.setUpdateLog(mXmlParser.nextText());
						}
					}
					
					break;
					
				case XmlPullParser.END_TAG:
					
					if(mXmlParser.getName().equals("version-log")){
						
						//如果获取不到对应语言和地区的日志信息，则直接取默认的日志
						if(mUpdateInfo.getUpdateLog().length() == 0){
							mUpdateInfo.setUpdateLog(mDefaultLog);
						}
					}
					break;
					
				default:
					break;
			}
			mXmlEventType = mXmlParser.next();
		}
		return mUpdateInfo;
	}
	
	//获取系统语言及地区信息
	private String getSysLocale(){
		String strLanguage = Locale.getDefault().getLanguage();
		String strCountry = Locale.getDefault().getCountry();

		return strLanguage + "-" + strCountry;
	}
}