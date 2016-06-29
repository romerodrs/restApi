package com.api.ws;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import com.api.ws.entity.Users;
import com.api.ws.oozie.OozieApp;
import com.api.ws.repository.UsersDao;

/**
 * Created by DLRR on 6/14/16.
 */
@RestController
@Async
public class WebserviceRestController {

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private OozieApp oozieApp;
	
	@RequestMapping(value="/users", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
    @ApiOperation(value = "findAll", nickname = "getAll")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Users.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
	public List<Users> getAll(){
		return usersDao.findAll();
	}
	
	@RequestMapping(value="/users/{id}", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
    @ApiOperation(value = "findByuserId", nickname = "getById")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "User's id", required = false, dataType = "int")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Users.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
	public Users getById(@PathVariable int id){
		return usersDao.findByuserId(id);
	}
	

    @RequestMapping(value="/oozie", method = RequestMethod.GET, headers="Accept=application/json")
    public String oozieExec(){
    	return oozieApp.runOozie();
    }



}
