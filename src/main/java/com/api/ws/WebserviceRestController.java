package com.api.ws;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.api.ws.entity.Users;
import com.api.ws.oozie.OozieService;
import com.api.ws.oozie.job.OozieJob;
import com.api.ws.repository.UsersDao;

/**
 * Created by DLRR on 6/14/16.
 */
@RestController
public class WebserviceRestController {

	private static Logger logger = Logger.getLogger(WebserviceRestController.class);
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private OozieService oozieService;
	
	private HashMap<String, OozieJob> oozieJobStatusMap = new HashMap<String, OozieJob>();
	
	@RequestMapping(value="/oozie", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public String oozieExec(){
		logger.info("[Webservice Call] Calling oozie ...");
		OozieJob oozieJob = null;
		try {
			oozieJob =  oozieService.executeOozieJob();
			oozieJobStatusMap.put(oozieJob.getOozieJobId(), oozieJob);
			
		} catch (Exception e) {
			logger.info("[Webservice Call] Error calling Oozie: " + e.getLocalizedMessage());
		}
		logger.info("[Webservice Call] End oozie call!");
		return "Please, check if your job is running using /getOozieJobStatus/"+oozieJob.getOozieJobId();
    }
    
    
    @RequestMapping("/getOozieJobStatus/{jobId}")
    @ApiOperation(value = "getOozieJobStatus", nickname = "getOozieJobStatus")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "jobId", value = "Job id", required = true, dataType = "String", paramType="path")
    })
    public String getOozieJobStatus(@PathVariable String jobId){
    	logger.info("[Webservice Call] Oozie Job " + jobId  + " status ");
        String result = "Job not found";
        try {
            if (oozieJobStatusMap.get(jobId).getStatus() == OozieJob.statusRUNNING ) {                                                         
                // future ended, get and return the result
            	logger.info("[Webservice Call] Oozie Job " + oozieJobStatusMap.get(jobId).getOozieJobId() + " Done");
            	oozieJobStatusMap.get(jobId).setStatus(OozieJob.statusSUCCEDED);
            	result = "Oozie Job " + oozieJobStatusMap.get(jobId).getOozieJobId() + "Done";
            } else {
            	logger.info("[Webservice Call] Oozie Job " + oozieJobStatusMap.get(jobId).getOozieJobId() + "already PROCESING ");
            	oozieJobStatusMap.get(jobId).setStatus(OozieJob.statusRUNNING);
            	result = "Oozie Job " + oozieJobStatusMap.get(jobId).getOozieJobId() + "already PROCESING";
            }
        } catch (Exception e) {
            // error, return errorObject
        	logger.info("[Webservice Call] Error getting Oozie Job {"+ jobId +"}: " + e.getLocalizedMessage());
        	result  = "Error getting Oozie Job {"+ jobId +"}: " + e.getLocalizedMessage();
        	try{
        		oozieJobStatusMap.get(jobId).setStatus(OozieJob.statusKILLED);
        	}catch(Exception ex){
        		logger.info("[Webservice Call] Error getting Oozie Job {"+ jobId +"}: " + e.getLocalizedMessage());
        		result  = "Error getting Oozie Job {"+ jobId +"}: " + e.getLocalizedMessage();
        	}
        }
        return result;
    }
    
	@RequestMapping(value="/users", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
    @ApiOperation(value = "findAll", nickname = "getAll")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Users.class),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
	public List<Users> getAll(){
		logger.info("[Webservice Call] Get all users ...");
		return usersDao.findAll();
	}

	@RequestMapping(value="/users/{id}", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
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
	public Users getById(@PathVariable long id){
		logger.info("[Webservice Call] Get users by id { "+ id + "} ..." );
		// controlar id null
		return usersDao.findByuserId(id);
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
    public Users createUser(@PathVariable String userName, @PathVariable String password){
    	logger.info("[Webservice Call] Creating new user { "+ userName +" } { "+ password +" }...");
    	Users user = new Users(); 
    	// controlar username y/o password null
    	user.setUserName(userName);
    	user.setUserPassword(password);
    	return usersDao.save(user);
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
    public void deleteUser(@PathVariable long id){
    	logger.info("[Webservice Call] Deleting user with id { "+ id +" } ...");
    	usersDao.delete(id);
    	logger.info("[Webservice Call] User deleted!");
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
    public void updateUser(@PathVariable long id, @PathVariable String userName, @PathVariable String password){
    	logger.info("[Webservice Call] Updating user { "+ id +" } { "+ userName +" } { "+ password +" }...");
    	Users user = usersDao.findByuserId(id);
    	if (user != null){
    		user.setUserName(userName);
    		user.setUserPassword(password);
    	}
    	logger.info("[Webservice Call] User updated!");
	}
    
}
