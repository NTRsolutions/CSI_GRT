package com.danikula.videocache.file;

import android.text.TextUtils;

import com.danikula.videocache.ProxyCacheUtils;

/**
 * Implementation of {@link FileNameGenerator} that uses MD5 of url as file name
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
public class Md5FileNameGenerator implements FileNameGenerator {

    private static final int MAX_EXTENSION_LENGTH = 4;
    String videoName;
    public Md5FileNameGenerator(String videoName) {
    	this.videoName=videoName;
	}

	

	@Override
    public String generate(String url,String videoName) {
        String extension = getExtension(url);
//        String name = ProxyCacheUtils.computeMD5(url);
        String name=videoName;
        return TextUtils.isEmpty(extension) ? name : name + "." + extension;
    }

    private String getExtension(String url) {
        int dotIndex = url.lastIndexOf('.');
        int slashIndex = url.lastIndexOf('/');
        return dotIndex != -1 && dotIndex > slashIndex && dotIndex + 2 + MAX_EXTENSION_LENGTH > url.length() ?
                url.substring(dotIndex + 1, url.length()) : "";
    }
}
