package com.example.demo.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OtherMapper {
	Date getNowDate();
	// プロフィール画像の登録(仮実装)
	void uploadImage(byte[] uploadImage);
}
