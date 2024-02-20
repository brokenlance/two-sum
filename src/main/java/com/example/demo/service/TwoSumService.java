package com.example.demo.service;

import com.example.demo.config.Config;
import com.example.demo.model.TwoSumRequest;
import com.example.demo.model.TwoSumResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static java.lang.Math.abs;
import static java.lang.Math.random;
import static java.util.Arrays.stream;
import static java.util.Collections.sort;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class TwoSumService
{
   @Autowired
   private Config config;
   private static List< String > data = new ArrayList<>();

   /**
    * @param TwoSumRequest -- contains the target sum value.
    * @return TwoSumResponse -- return the summands; if there aren't any, the values -1L are returned.
    */
   public TwoSumResponse twoSum( TwoSumRequest request )
   {
      log.info( "TwoSumService::twoSum algorithm: {}", config.algorithm() );
      data.add( randomUUID().toString() );

      List< Long > numbers = stream( callService( config.url() ).split( "," ) ).mapToLong( Long::parseLong ).boxed().toList();

      log.info( "The random list of nubmers is: {}", numbers );

      if( "two-pointers".equals( config.algorithm() ) )
      {
         return twoPointers( new ArrayList< Long >( numbers ), request.getTargetSum() );
      }
      else if( "hash".equals( config.algorithm() ) )
      {
         return hash( new ArrayList< Long >( numbers ), request.getTargetSum() );
      }

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
      data.add( randomUUID().toString() );

      for( int i=0; i<numbers.size(); i++ )
      {
         if( i % 40 == 0 )
         {
            data.add( randomUUID().toString() );
         }
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

   /**
    * Solves the two-sum problems using the two-pointer approach.
    */
   public TwoSumResponse twoPointers( List< Long > numbers, long target )
   {
      sort( numbers );

      int  left  = 0;
      int  right = numbers.size() - 1;
      long sum   = 0;
      int  i = 0;

      while( left < right )
      {
         sum = numbers.get( left ) + numbers.get( right );
         if( sum == target )
         {
            log.info( "Found solution: {} and {}", numbers.get( left ), numbers.get( right ) );
            return new TwoSumResponse( numbers.get( left ), numbers.get( right ) );
         }
         else if( sum < target )
         {
            left++;
         }
         else if( sum > target )
         {
            right--;
         }
      }

      return new TwoSumResponse( -1L, -1L );
   }

   /**
    * Solves the two-sum approach using hashes.
    */
   public TwoSumResponse hash( List< Long > numbers, long target )
   {
      Set< Long > set = new HashSet<>();
      Long        t   = 0L;

      for( Long number : numbers )
      {
         t = target - number;

         if( set.contains( t ) )
         {
            log.info( "Found solution: {} and {}", t, number );
            return new TwoSumResponse( t, number );
         }
         else
         {
            set.add( number );
         }
      }

      return new TwoSumResponse( -1L, -1L );
   }
}
