package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TwoSumRequest;
import com.example.demo.model.TwoSumResponse;
import com.example.demo.service.TwoSumService;

/**
 * A Test Controller class.
 */
@RestController
@Slf4j
public class Controller
{
   @Autowired
   private TwoSumService service;

   /**
    * @param TwoSumRequest -- contains the target sum value.
    * @return TwoSumResponse -- return the summands; if there aren't any, the values -1L are returned.
    */
   @RequestMapping( value="/api/v1/two-sum" )
   public ResponseEntity< TwoSumResponse > twoSum( @RequestBody TwoSumRequest request )
   {
      log.info( "Controller::twoSum" );

      return ResponseEntity.ok( service.twoSum( request ) );
   }
}
