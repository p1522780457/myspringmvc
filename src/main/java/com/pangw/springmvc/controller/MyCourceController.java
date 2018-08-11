package com.pangw.springmvc.controller;

import java.awt.Dialog.ModalExclusionType;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.pangw.springmvc.model.Course;
import com.pangw.springmvc.service.CourseService;

@Controller
@RequestMapping("courses")
public class MyCourceController {

	private CourseService courseService;

	@Autowired
	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	// courses/view?courseId=123 形式的URL
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewCource(@RequestParam("courseId") Integer courseId, Model model) {

		Course course = courseService.getCoursebyId(courseId);
		model.addAttribute(course);
		return "course_overview";

	}

	// 本方法将处理 /courses/view2/123 形式的URL
	@RequestMapping(value = "/view2/{courseId}", method = RequestMethod.GET)
	public String viewCource2(@PathVariable("courseId") Integer courseId, Model model) {
		Course course = courseService.getCoursebyId(courseId);
		model.addAttribute(course);
		return "course_overview";
	}

	// 本方法将处理 /courses/view3?courseId=123 形式的URL
	@RequestMapping("/view3")
	public String viewCourse3(HttpServletRequest request) {
		Integer integer = Integer.valueOf(request.getParameter("courseId"));
		Course course = courseService.getCoursebyId(integer);
		request.setAttribute("course", course);
		return "course_overview";
	}

	/// courses/admin?add=122
	@RequestMapping(value = "/admin", method = RequestMethod.GET, params = "add")
	public String createCource() {
		return "course_admin/edit";
	}

	// courses/save
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String doSava(@ModelAttribute Course course) {
		course.setCourseId(123);
		System.out.println("dsadsadsa:::;;;" + course.getTitle());
		return "redirect:view2/" + course.getCourseId();
	}
	
    //courses/upload
	//文件页面
	@RequestMapping(value="/upload",method = RequestMethod.GET)
	public String showUploadPage(){
		return "course_admin/file";
	}
	
	//文件上传成功
	@RequestMapping(value = "/doUpload",method = RequestMethod.POST)
	public String doUploadFile(@RequestParam("file") MultipartFile file) throws IOException{
		if(!file.isEmpty()){
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File("e:\\iii\\sss",System.currentTimeMillis()+file.getOriginalFilename()));
		}
		return "success";
	}
//	@RequestMapping(value="/doUpload2", method=RequestMethod.POST)
//	public String doUploadFile2(MultipartHttpServletRequest multiRequest) throws IOException{
//		
//		Iterator<String> filesNames = multiRequest.getFileNames();
//		while(filesNames.hasNext()){
//			String fileName =filesNames.next();
//			MultipartFile file =  multiRequest.getFile(fileName);
//			if(!file.isEmpty()){
//				log.debug("Process file: {}", file.getOriginalFilename()); 
//				FileUtils.copyInputStreamToFile(file.getInputStream(), new File("c:\\temp\\imooc\\", System.currentTimeMillis()+ file.getOriginalFilename()));
//			}
//			
//		}
//		
//		return "success";
//	}
	
	
	
//	@RequestMapping(value="/{courseId}",method=RequestMethod.GET)
//	public @ResponseBody Course getCourseInJson(@PathVariable Integer courseId){
//		return  courseService.getCoursebyId(courseId);
//	}
	
	
	//返回json
	@RequestMapping(value="/{courseId}",method = RequestMethod.GET)
	public @ResponseBody Course getCourseByIdToJson(@PathVariable Integer courseId){
		return courseService.getCoursebyId(courseId);
	}
	
//	@RequestMapping(value="/jsontype/{courseId}",method=RequestMethod.GET)
//	public  ResponseEntity<Course> getCourseInJson2(@PathVariable Integer courseId){
//		Course course =   courseService.getCoursebyId(courseId);		
//		return new ResponseEntity<Course>(course, HttpStatus.OK);
//	}
	@RequestMapping(value="/jsontype/{courseId}",method = RequestMethod.GET)
	public ResponseEntity<Course> getCourseInJson2( @PathVariable Integer courseId){
		Course course =   courseService.getCoursebyId(courseId);	
		return new ResponseEntity<Course>(course,HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	

}
