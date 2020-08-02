package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.OtherMapper;

@Service
public class OtherService {
	@Autowired
	private OtherMapper otherMapper;

	/**
	 * @return
	 */
	public Date getNowDate() {
		return otherMapper.getNowDate();
	}

	/**
	 * @param image
	 * プロフィール画像の登録(仮実装)
	 */
	public void uploadImage(MultipartFile image) {
//		byte[] imageBinary;
	    byte[] b = new byte[1];
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try {
//	    	InputStream isImage = new ByteArrayInputStream(byteImage);
	    	InputStream isImage = image.getInputStream();
	    	byte[] uploadImage = IOUtils.toByteArray(isImage);
//	    	byte[] uploadFile = profileForm.getImage().getBytes();
	    	while (isImage.read(b) > 0) {
	    		baos.write(b);
	    	}
	    	baos.close();
	    	isImage.close();
	    	b = baos.toByteArray();

	    	otherMapper.uploadImage(uploadImage);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
