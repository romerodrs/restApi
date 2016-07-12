package com.api.ws;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import com.api.ws.oozie.OozieService;
import com.api.ws.oozie.job.OozieJob;

/**
 * Created by DLRR
 */
@RestController
public class WebserviceRestController {

	private static Logger logger = Logger.getLogger(WebserviceRestController.class);
	
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
		OozieJob oozieJob = null;
		try {
			oozieJob = oozieService.executeOozieJob();
			
			returnMessage = "Please, check if your job is running using /getOozieJobStatus/"+ oozieJob.getOozieJobId();
			httpStatus = HttpStatus.CREATED;   
		} catch (Exception e) {
			logger.info("[Webservice Call] Error calling Oozie: " + e.getLocalizedMessage());
			returnMessage ="Error calling Oozie: " + e.getLocalizedMessage();
			httpStatus =HttpStatus.INTERNAL_SERVER_ERROR; 
		}
		logger.info("[Webservice Call] End oozie call!");
		DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<ResponseEntity<?>>();
		
		try {
			oozieService.updateOozieJobStatus(oozieJob);
		} catch (Exception e) {
			logger.info("[Webservice Call] Oozie update error: " + e.getLocalizedMessage());
		}
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(returnMessage, httpStatus);
		deferredResult.setResult(responseEntity);
		return deferredResult;
    }
    
    
    @RequestMapping(value="/getOozieJobStatus/{jobId}", method = RequestMethod.GET, headers="Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
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
    public String getOozieJobStatus(@PathVariable String jobId){
    	logger.info("[Webservice Call] Oozie Job " + jobId  + " status ");
        String oozieJobStatus = "NO_CONTENT";
        try {
        	oozieJobStatus = oozieService.oozieJobStatus(jobId);
        	logger.info("[OOZIE JOB STATUS]" + oozieJobStatus);
        } catch (Exception e) {
        	logger.info("[Webservice Call] Error getting Oozie Job {"+ jobId +"}: " + e.getLocalizedMessage());
        }
        return " Oozie Job status is" + oozieJobStatus;
    }
  
}
