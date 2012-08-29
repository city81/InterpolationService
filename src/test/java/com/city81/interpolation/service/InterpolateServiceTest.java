package com.city81.interpolation.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class InterpolateServiceTest {

	private InterpolateService interpolateService;
		
	@Before
	public void setup() {
		interpolateService = new InterpolateService();
	}
	
	@Test
	public void testInterpolateNullValueOne() {
		
		try {
			interpolateService.interpolate(null, 25.0, 4);
		} catch(Exception exc) {
			assertTrue(exc instanceof IllegalArgumentException);
			assertEquals(InterpolateService.VALUE_ARG_NOT_NULL_EXCP_TEXT, exc.getMessage());
		}
		
	}		
	
	@Test
	public void testInterpolateNullValueTwo() {
		
		try {
			interpolateService.interpolate(5.0, null, 4);
		} catch(Exception exc) {
			assertTrue(exc instanceof IllegalArgumentException);
			assertEquals(InterpolateService.VALUE_ARG_NOT_NULL_EXCP_TEXT, exc.getMessage());
		}
		
	}	
	
	@Test
	public void testInterpolateNullSteps() {
		
		try {
			interpolateService.interpolate(5.0, 25.0, null);
		} catch(Exception exc) {
			assertTrue(exc instanceof IllegalArgumentException);
			assertEquals(InterpolateService.STEPS_ARG_NOT_VALID_EXCP_TEXT, exc.getMessage());
		}
		
	}	
	
	@Test
	public void testInterpolateZeroSteps() {
		
		try {
			interpolateService.interpolate(5.0, 25.0, 0);
		} catch(Exception exc) {
			assertTrue(exc instanceof IllegalArgumentException);
			assertEquals(InterpolateService.STEPS_ARG_NOT_VALID_EXCP_TEXT, exc.getMessage());
		}
		
	}		
	
	@Test
	public void testInterpolateNullValueList() {
		
		try {
			interpolateService.interpolate(null, 4);
		} catch(Exception exc) {
			assertTrue(exc instanceof IllegalArgumentException);
			assertEquals(InterpolateService.VALUE_LIST_NOT_VALID_EXCP_TEXT, exc.getMessage());
		}
		
	}
	
	@Test
	public void testInterpolateTwoValues() {
		
		List<Double> interpolatedList = 
				interpolateService.interpolate(5.0, 25.0, 4);
		
		assertEquals(5, interpolatedList.size());
		assertEquals(5.0, interpolatedList.get(0), 0);
		assertEquals(10.0, interpolatedList.get(1), 0);
		assertEquals(15.0, interpolatedList.get(2), 0);
		assertEquals(20.0, interpolatedList.get(3), 0);
		assertEquals(25.0, interpolatedList.get(4), 0);
	}	
	
	@Test
	public void testInterpolateTwoValuesOddSteps() {
		
		List<Double> interpolatedList = 
				interpolateService.interpolate(6.0, 18.0, 3);
		
		assertEquals(4, interpolatedList.size());		
		assertEquals(6.0, interpolatedList.get(0), 0);
		assertEquals(10.0, interpolatedList.get(1), 0);
		assertEquals(14.0, interpolatedList.get(2), 0);
		assertEquals(18.0, interpolatedList.get(3), 0);
	}		

	@Test
	public void testInterpolateTwoValueList() {
		
		List<Double> inputList = new ArrayList<Double>();
		inputList.add(5.0);
		inputList.add(-25.0);		
				
		List<Double> interpolatedList = 
				interpolateService.interpolate(inputList, 4);
		
		assertEquals(5, interpolatedList.size());
		assertEquals(5.0, interpolatedList.get(0), 0);
		assertEquals(-2.5, interpolatedList.get(1), 0);
		assertEquals(-10.0, interpolatedList.get(2), 0);
		assertEquals(-17.5, interpolatedList.get(3), 0);
		assertEquals(-25.0, interpolatedList.get(4), 0);
	}
	
	@Test
	public void testInterpolateFourValueList() {
		
		List<Double> inputList = new ArrayList<Double>();
		inputList.add(5.0);
		inputList.add(25.0);
		inputList.add(20.0);
		inputList.add(0.0);		
				
		List<Double> interpolatedList = 
				interpolateService.interpolate(inputList, 4);
		
		assertEquals(13, interpolatedList.size());		
		assertEquals(5.0, interpolatedList.get(0), 0);
		assertEquals(10.0, interpolatedList.get(1), 0);
		assertEquals(15.0, interpolatedList.get(2), 0);
		assertEquals(20.0, interpolatedList.get(3), 0);
		assertEquals(25.0, interpolatedList.get(4), 0);
		assertEquals(23.75, interpolatedList.get(5), 0);
		assertEquals(22.5, interpolatedList.get(6), 0);
		assertEquals(21.25, interpolatedList.get(7), 0);
		assertEquals(20.0, interpolatedList.get(8), 0);
		assertEquals(15.0, interpolatedList.get(9), 0);
		assertEquals(10.0, interpolatedList.get(10), 0);
		assertEquals(5.0, interpolatedList.get(11), 0);
		assertEquals(0.0, interpolatedList.get(12), 0);
	}
		
	@Test
	public void testInterpolateFourValueListOddSteps() {
		
		List<Double> inputList = new ArrayList<Double>();
		inputList.add(5.0);
		inputList.add(25.0);
		inputList.add(20.0);
		inputList.add(0.0);		
				
		List<Double> interpolatedList = 
				interpolateService.interpolate(inputList, 5);
		
		assertEquals(16, interpolatedList.size());		
		assertEquals(5.0, interpolatedList.get(0), 0);
		assertEquals(9.0, interpolatedList.get(1), 0);
		assertEquals(13.0, interpolatedList.get(2), 0);
		assertEquals(17.0, interpolatedList.get(3), 0);
		assertEquals(21.0, interpolatedList.get(4), 0);
		assertEquals(25.0, interpolatedList.get(5), 0);
		assertEquals(24.0, interpolatedList.get(6), 0);
		assertEquals(23.0, interpolatedList.get(7), 0);
		assertEquals(22.0, interpolatedList.get(8), 0);
		assertEquals(21.0, interpolatedList.get(9), 0);
		assertEquals(20.0, interpolatedList.get(10), 0);
		assertEquals(16.0, interpolatedList.get(11), 0);
		assertEquals(12.0, interpolatedList.get(12), 0);
		assertEquals(8.0, interpolatedList.get(13), 0);
		assertEquals(4.0, interpolatedList.get(14), 0);
		assertEquals(0.0, interpolatedList.get(15), 0);
	}	
	
}
