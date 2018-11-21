package com.atguigu.springmvc.handlers;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.atguigu.springmvc.entities.User;

@SessionAttributes(value={"user"}, types= {String.class})
@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {
	
//	@RequestMapping("/testViewAndViewResolver")
//	public String testViewAndViewResolver() {
//		System.out.println("user" );
//		return SUCCESS;
//	}
	private static final String SUCCESS = "success";
	
	/**
	 * 以redirect开头，则是重定向
	 * @return
	 */
	@RequestMapping("/testRedirect")
	public String testRedirect() {
		System.out.println("testRedirect");
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/testView")
	public String testView() {
		System.out.println("testView");
		return "helloView";
	}
	
	@RequestMapping("/testViewAndViewResolver")
	public String testViewAndViewResolver() {
		
		return SUCCESS;
	}
	/**
	 *1、 由@ModelAttribute标记的方法，会在每个目标方法执行之前被SpringMVC调用！
	 *2、@ModelAttribute注解也可以来修饰目标方法POJO类型的入参，其value属性的值有如下作用：
	 *1）SpringMVC会使用value属性值在implicitModel中查找对应的对象，若存在，则会直接传入到目标方法的入参中
	 *2）SpringMVV会以value的值为key, POJO类型的对象为value，存入在request中
	 * 运行流程：
	 * 1、执行由@ModelAttribute标记的方法：从数据库中取出对象，把对象放入到Map中，键为：user
	 * 2、SpringMVC中Map中取出User对象，并把表单的请求参数赋给User对象的对象属性
	 * 3、SpringMVC把上述对象传入目标方法的参数
	 * 
	 * 注意：在ModelAttribute修饰的方法中，放入在Map时的键需要和目标方法入参类型的名字一致！
	 * @param id
	 * @param map
	 * 
	 * SpringMVC 确定目标方法POJO类型入参的过程
	 * 1、确定一个key；
	 * 1）若目标方法的POJO类型的参数没有使用@ModelAttribute作为修饰，则key为POJO类名第一个字母的小写
	 * 2）若使用了@ModelAttribute修饰，则key为@ModelAttribute注解的value属性值
	 * 2、在implicitModel 中查找key 对应的对象，若存在，则作为入参传出
	 * 1）即若在@ModelAttribute修饰的方法中的Map保存过key属性，则作为入参
	 * 3、若不存在，则检查当前的Hander是否使用@SessionAttributes注释修饰，
	 * 如果使用，且@SessionAttributes注解的value属性值中包含了key，则会从HttpSession中获取key所对应的value值
	 * 若value值存在，则传入目标方法的入参，若不存在则抛出异常
	 * 4、若Hander没有标识@SessionAttributes注解或注解的value值中不包含key，则会通过反射来创建POJO类型的参数，作为入参
	 * 5、SpringMVC会把key和POJO类型的对象保存在implicitModel中，进而保存到request中
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value="id", required=false) Integer id, 
			Map<String, Object> map) {
		System.out.println("modelAttribute method");
		if(id != null) {
			User user = new User(1, "Tom", "123456", "tom@atguigu.com", 12);
			System.out.println("从数据库中获取一个对象：" + user);
			
			map.put("user2", user);
		}
			
	}
	//testModelAttribute(@ModelAttribute(value="user") User user)中的value值要和上个方法map中的键一致
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(@ModelAttribute(value="user2") User user) {
		System.out.println("修改：" + user);
		return SUCCESS;
	}
	
	/**
	 * @SessionAttributes可以将指定属性名（实际上使用的是value）或者属性类型将属性放到会话中（实际上使用的是type）
	 * 注意：该注解只能放在类的上面，而不能放在方法的上面
	 * @param map
	 * @return
	 */
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map) {
		User user = new User("Tom", "123456", "tom@qq.com", 15);
		map.put("user", user);
		return SUCCESS;
	}
	/**
	 * 目标方法可以添加 Map 类型（实际上也可以是Model 类型和ModelMap 类型）的参数
	 * @param map
	 * @return
	 */
	@RequestMapping("/testMap")
	public String testMap(Map<String, Object> map) {
		System.out.println(map.getClass().getName());
		map.put("names", Arrays.asList("Tom", "Jerry", "Mike"));		
		return SUCCESS;
	}
	
	/**
	 * 目标方法的返回值可以是ModelAndView 类型
	 * 其中可以包含视图和模型信息
	 * SpringMVCh 会把ModelAndView 的model 中的数据放入request 域对象中。
	 * @return
	 */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView() {
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		
		//添加模型数据到modelAndView中
		modelAndView.addObject("time", new Date());
		
		return modelAndView;
	}
	
	
	/**
	 *可以使用Sevlet 原生的API作为目标方法的参数，具体支持以下类型
	 *
	 * HttpServletRequest
	 * HttpServletResponse
	 * HttpSession
	 * java.security.Principal
	 * Locale InputStream
	 * OutputStream
	 * Reader
	 * Writer
	 */
	@RequestMapping("/testSevletAPI")
	public String testSevletAPI(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("testSevletAPI, Request: " + request + " Response: " + response);
		return SUCCESS;
	}

	/**
	 * 自动匹配，支持级联属性 
	 * 是重点
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/testPojo")
	public String testPojo(User user) {
		System.out.println("testPojo: " + user);
		return SUCCESS;
	}

	/**
	 * 了解
	 * 
	 * @CookieValue：映射一个Cookie值
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value = "/testCookieValue")
	public String testCookieValue(@CookieValue("JSESSIONID") String sessionId) {
		System.out.println("testCookieValue, JSESSIONID: " + sessionId);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRequestHeader")
	public String testRequestHeader(@RequestHeader(value = "Accept-Language") String al) {
		System.out.println("testRequestHeader, Accept-Language: " + al);
		return SUCCESS;
	}

	/**
	 * @RequestParam 来映射请求参数 value值即请求参数的参数名 required 该参数是否必须 defaultValue 请求参数的默认值
	 */
	@RequestMapping(value = "/testRequestParam")
	public String testRequestParam(@RequestParam(value = "username") String un,
			@RequestParam(value = "age", required = false, defaultValue = "0") int age) {
		System.out.println("testRequestParam, username: " + un + " age: " + age);
		return SUCCESS;
	}

	/**
	 * Rest风格的URL 以CRUD为例 新增：/order POST 修改：/order/1 PUT 获取：/order/1 GET 删除：/order/1
	 * DELET
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.PUT)
	public String testRestPUT(@PathVariable Integer id) {
		System.out.println("testRest PUT: " + id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.DELETE)
	public String testRestDELETE(@PathVariable Integer id) {
		System.out.println("testRest DELETE: " + id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest", method = RequestMethod.POST)
	public String testRestPOST() {
		System.out.println("testRest POST");
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.GET)
	public String testRestGET(@PathVariable Integer id) {
		System.out.println("testRest GET: " + id);
		return SUCCESS;
	}

	/**
	 * @PathVariable 可以来映射URL中的占位符到目标方法的参数中
	 * @param id
	 */
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable(value = "id") Integer id) {
		System.out.println("testPathVariable: " + id);
		return SUCCESS;
	}

	@RequestMapping("/testAntPat/*/abc")
	public String testAntPath() {
		System.out.println("testAntPath");
		return SUCCESS;
	}

	@RequestMapping(value = "/testParamsAndHeaders", params = { "username", "age!=10" }, headers = {
			"Accept-Language=zh-CN,zh;q=0.8" })
	public String testParamsAndHeaders() {
		System.out.println("testParamsAndHeaders");
		return SUCCESS;
	}

	/**
	 * 常用：使用method属性来指定请求方式
	 * 
	 * @return
	 */
	@RequestMapping(value = "testMethod", method = RequestMethod.POST)
	public String testMethod() {
		System.out.println("testMethod");
		return SUCCESS;
	}

	@RequestMapping(value = "testMethod", method = RequestMethod.GET)
	public String testMethod2() {
		System.out.println("testMethod2");
		return SUCCESS;
	}

	/**
	 * 1、@RequestMapping除了修饰方法，还可以修饰类 
	 * 2、类定义处，提供相对于WEB应用的根目录；方法处，提供相对于类定义处
	 * 
	 * @return
	 */
	@RequestMapping("/testRequestMapping")
	public String testRequestMapping() {
		System.out.println("testRequestMapping");
		return SUCCESS;

	}
}
