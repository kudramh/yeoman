package com.citigroup.nga.crud.moneymovement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citigroup.nga.crud.moneymovement.beans.RequestCancelCredit;
import com.citigroup.nga.crud.moneymovement.beans.RequestCancelDebit;
import com.citigroup.nga.crud.moneymovement.beans.RequestCredit;
import com.citigroup.nga.crud.moneymovement.beans.RequestDebit;
import com.citigroup.nga.crud.moneymovement.beans.ResponseMoneyMovement;
import com.citigroup.nga.crud.moneymovement.constants.StaticValues;
import com.citigroup.nga.crudcore.CRUDCore;
import com.citigroup.nga.crudcore.assembler.DataAssembler;
import com.citigroup.nga.crudcore.exceptions.CRUDCoreException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * 
 * @author bazalduai
 *
 */
@Api
@RestController
public class Controller {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CRUDCore crudCore;
	
	@Autowired
	private StaticValues staticValues;
	
	//TODO Quitar este codigo
	@Autowired
	DataAssembler assembler;
	
	@RequestMapping( value="debit", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ApiOperation(value = "debit", nickname = "debit", response = ResponseEntity.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), 
            @ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<ResponseMoneyMovement> doOperationDebit(@RequestBody RequestDebit request ) {
		ResponseMoneyMovement response = new ResponseMoneyMovement();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {

			response = crudCore.callCRUD(staticValues.getDebit(), request, ResponseMoneyMovement.class);
			
		} catch(CRUDCoreException e) {
			logger.error(e.getMessage(), e);
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<ResponseMoneyMovement>( response, status );
	}
	
	@RequestMapping( value="credit", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ApiOperation(value = "credit", nickname = "credit", response = ResponseEntity.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), 
            @ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<ResponseMoneyMovement> doOperationCredit(@RequestBody RequestCredit request ) {
		ResponseMoneyMovement response = new ResponseMoneyMovement();
		HttpStatus status = HttpStatus.ACCEPTED;
		logger.info("!!!!!!Llamando a CRUD!!!!!");
		try {
			
			response = crudCore.callCRUD(staticValues.getCredit(), request, ResponseMoneyMovement.class);
			
		} catch(CRUDCoreException e) {
			logger.error(e.getMessage(), e);
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<ResponseMoneyMovement>( response, status );
	}
	
	@RequestMapping( value="cancel/debit", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ApiOperation(value = "cancel/debit", nickname = "cancel/debit", response = ResponseEntity.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), 
            @ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<ResponseMoneyMovement> doOperationCancelDebit(@RequestBody RequestCancelDebit request ) {
		ResponseMoneyMovement response = new ResponseMoneyMovement();
		HttpStatus status = HttpStatus.ACCEPTED;
		logger.info("!!!!!!Llamando a CRUD!!!!!");
		try {
			
			response = crudCore.callCRUD(staticValues.getCancelDebit(), request, ResponseMoneyMovement.class);
			
		} catch(CRUDCoreException e) {
			logger.error(e.getMessage(), e);
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<ResponseMoneyMovement>( response, status );
	}
	
	@RequestMapping( value="cancel/credit", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ApiOperation(value = "cancel/credit", nickname = "cancel/credit", response = ResponseEntity.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"), 
            @ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<ResponseMoneyMovement> doOperationCancelCredit(@RequestBody RequestCancelCredit request ) {
		ResponseMoneyMovement response = new ResponseMoneyMovement();
		HttpStatus status = HttpStatus.ACCEPTED;
		logger.info("!!!!!!Llamando a CRUD!!!!!");
		try {
			
			response = crudCore.callCRUD(staticValues.getCancelCredit(), request, ResponseMoneyMovement.class);
			
		} catch(CRUDCoreException e) {
			logger.error(e.getMessage(), e);
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<ResponseMoneyMovement>( response, status );
	}
}
