package com.example.demo.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.config.Config;
import com.example.demo.model.TwoSumRequest;
import com.example.demo.model.TwoSumResponse;
import static java.util.Arrays.stream;

@Slf4j
@Service
public class TwoSumService
{
   @Autowired
   private Config config;

   /**
    * @param TwoSumRequest -- contains the target sum value.
    * @return TwoSumResponse -- return the summands; if there aren't any, the values -1L are returned.
    */
   public TwoSumResponse twoSum( TwoSumRequest request )
   {
      log.info( "TwoSumService::twoSum algorithm: {}", config.algorithm() );

      List< Long > numbers = stream( callService( config.url() ).split( "," ) ).mapToLong( Long::parseLong ).boxed().toList();

      log.info( "The random data is: {}", numbers );

      return bruteForce( numbers, request.getTargetSum() );
   }

   /**
    * @return String -- of random numbers separated by commas.
    */
   public String callService( String url )
   {
      return WebClient.create()
                      .get()
                      .uri( url )
                      .retrieve()
                      .bodyToMono( String.class )
                      .block();
   }

   /**
    * The brute force algorithm runs in O( n^2 ) time and O( 1 ) space.
    */
   private TwoSumResponse bruteForce( List< Long > numbers, long target )
   {
      for( int i=0; i<numbers.size(); i++ )
      {
         for( int j=i+1; j<numbers.size(); j++ )
         {
            if( numbers.get( i ) + numbers.get( j ) == target )
            {
               log.info( "Found solution: {} and {}", numbers.get( i ), numbers.get( j ) );
               return new TwoSumResponse( numbers.get( i ), numbers.get( j ) );
            }
         }
      }

      return new TwoSumResponse( -1L, -1L );
   }
}
