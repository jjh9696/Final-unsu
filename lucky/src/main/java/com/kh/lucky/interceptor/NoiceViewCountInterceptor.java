package com.kh.lucky.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.lucky.dao.NoticeDao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class NoiceViewCountInterceptor implements HandlerInterceptor{
	@Autowired
	private NoticeDao noticeDao;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
