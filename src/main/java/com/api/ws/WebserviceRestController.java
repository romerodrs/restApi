package com.api.ws;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import com.api.ws.entity.Users;
import com.api.ws.oozie.OozieService;
import com.api.ws.repository.UsersDao;

/**
 * Created by DLRR
 */
@RestController
public class WebserviceRestController {

	private static Logger logger = Logger.getLogger(WebserviceRestController.class);
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private OozieService oozieService;
	
	@RequestMapping(value="/oozie", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
    @ApiOperation(value = "oozie", nickname = "execute oozie job")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Failure")})
	public DeferredResult<ResponseEntity<?>> oozieExec(){
		logger.info("[Webservice Call] Calling oozie ...");
		HttpStatus httpStatus = HttpStatus.NO_CONTENT;
		String returnMessage = "NO_CONTENT";
		try {
			String jobId = oozieService.executeOozieJob();
			returnMessage = "Please, check if your job is running using /getOozieJobStatus/"+jobId;
			httpStatus = HttpStatus.CREATED;   
		} catch (Exception e) {
			logger.info("[Webservice Call] Error calling Oozie: " + e.getLocalizedMessage());
			returnMessage ="Error calling Oozie: " + e.getLocalizedMessage();
			httpStatus =HttpStatus.INTERNAL_SERVER_ERROR; 
		}
		logger.info("[Webservice Call] End oozie call!");
		DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<ResponseEntity<?>>();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(returnMessage, httpStatus);
		deferredResult.setResult(responseEntity);
		return deferredResult;
    }
    
    
    @RequestMapping(value="/getOozieJobStatus/{jobId}", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
    @ApiOperation(value = "getOozieJobStatus", nickname = "get oozie job status")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "jobId", value = "Job id", required = true, dataType = "String", paramType="path")
    })
    @ApiResponses(value = {
    		@ApiResponse(code = 100, message = "Continue"),
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Failure")})
    public DeferredResult<ResponseEntity<?>> getOozieJobStatus(@PathVariable String jobId){
    	logger.info("[Webservice Call] Oozie Job " + jobId  + " status ");
        HttpStatus http = HttpStatus.NO_CONTENT;
        String oozieJobStatus = "NO_CONTENT";
        try {
        	oozieJobStatus = oozieService.oozieJobStatus(jobId);
        	logger.info("[OOZIE JOB STATUS]" + oozieJobStatus);
            if (oozieJobStatus.equals(OozieService.statusRUNNING)) {                                                         
            	http = HttpStatus.CONTINUE;
            }else{
            	http = HttpStatus.OK;
            }
        } catch (Exception e) {
        	logger.info("[Webservice Call] Error getting Oozie Job {"+ jobId +"}: " + e.getLocalizedMessage());
        	http = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<ResponseEntity<?>>();
    	ResponseEntity<String> responseEntity = new ResponseEntity<String>(" Oozie Job status is" + oozieJobStatus, http);
    	deferredResult.setResult(responseEntity);
        return deferredResult;
    }
    
	@RequestMapping(value="/users", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
    @ApiOperation(value = "findAll", nickname = "getAll")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Users.class),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
	public DeferredResult<ResponseEntity<?>> getAll(){
		logger.info("[Webservice Call] Get all users ...");
		DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<ResponseEntity<?>>();
		ResponseEntity<List<Users>> responseEntity  = new ResponseEntity<List<Users>>(usersDao.findAll(), HttpStatus.OK);    
		deferredResult.setResult(responseEntity);
		return deferredResult;
//		return usersDao.findAll();
	}

	@RequestMapping(value="/users/{id}", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "findByuserId", nickname = "getById")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "User's id", required = true, dataType = "int", paramType="path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Users.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
	public DeferredResult<ResponseEntity<?>> getById(@PathVariable long id){
		logger.info("[Webservice Call] Get users by id { "+ id + "} ..." );
		// controlar id null
		DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<ResponseEntity<?>>();
		ResponseEntity<Users> responseEntity  = new ResponseEntity<Users>(usersDao.findByuserId(id), HttpStatus.OK);    
		deferredResult.setResult(responseEntity);
		return deferredResult;
//		return usersDao.findByuserId(id);
	}
	
    @RequestMapping(value="/createUser/{userName}/{password}", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
    @ApiOperation(value = "createUser", nickname = "createNewUser")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userName", value = "User name", required = true, dataType = "String", paramType="path"),
        @ApiImplicitParam(name = "password", value = "password", required = true, dataType = "String", paramType="path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Users.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public DeferredResult<ResponseEntity<?>> createUser(@PathVariable String userName, @PathVariable String password){
    	logger.info("[Webservice Call] Creating new user { "+ userName +" } { "+ password +" }...");
    	Users user = new Users(); 
    	// controlar username y/o password null
    	user.setUserName(userName);
    	user.setUserPassword(password);
//    	return usersDao.save(user);
    	DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<ResponseEntity<?>>();
		ResponseEntity<Users> responseEntity  = new ResponseEntity<Users>(usersDao.save(user), HttpStatus.OK);    
		deferredResult.setResult(responseEntity);
		return deferredResult;
    	
	}
	
    @RequestMapping(value="/deleteUser/{id}", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "deleteUser", nickname = "deleteUser")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "user id", required = true, dataType = "long", paramType="path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Failure")})
    public DeferredResult<ResponseEntity<?>> deleteUser(@PathVariable long id){
    	logger.info("[Webservice Call] Deleting user with id { "+ id +" } ...");
    	usersDao.delete(id);
    	logger.info("[Webservice Call] User deleted!");
    	DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<ResponseEntity<?>>();
		ResponseEntity<String> responseEntity  = new ResponseEntity<String>("User deleted!", HttpStatus.OK);    
		deferredResult.setResult(responseEntity);
		return deferredResult;
    }

    @RequestMapping(value="/updateUser/{id}/{userName}/{password}", method = RequestMethod.GET, headers="Accept=application/json")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "updateUser", nickname = "updateUser")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "id", value = "user id", required = true, dataType = "long", paramType="path"), 
        @ApiImplicitParam(name = "userName", value = "User name", required = true, dataType = "String", paramType="path"),
        @ApiImplicitParam(name = "password", value = "password", required = true, dataType = "String", paramType="path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public DeferredResult<ResponseEntity<?>> updateUser(@PathVariable long id, @PathVariable String userName, @PathVariable String password){
    	logger.info("[Webservice Call] Updating user { "+ id +" } { "+ userName +" } { "+ password +" }...");
    	Users user = usersDao.findByuserId(id);
    	if (user != null){
    		user.setUserName(userName);
    		user.setUserPassword(password);
    	}
    	logger.info("[Webservice Call] User updated!");
    	DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<ResponseEntity<?>>();
		ResponseEntity<String> responseEntity  = new ResponseEntity<String>("User updated!", HttpStatus.OK);    
		deferredResult.setResult(responseEntity);
		return deferredResult;
	}
    
}
