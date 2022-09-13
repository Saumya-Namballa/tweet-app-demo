package com.tweetapp.tweet.service.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateFormatter {
	
	public String dateformatter(Date date) {
		SimpleDateFormat ft = 
			      new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a");
		return ft.format(date);
	}

}
