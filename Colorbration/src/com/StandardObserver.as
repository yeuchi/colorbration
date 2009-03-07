// ==================================================================
//	Module:			StandardObserver
//
// 	Description:	Functions describing the standard observer curves.
//					Not to be limited by resolution, extending from
//					interpolation class to work with any resolution.
//
// Reference:		CIE Standard Observer z bar function, 2 degrees, 1931
//					source from Dr. Hunt's Color textbook.
//
// Input:			wavelength between 400 to 700 nm (visible)
// Output:			interpolated (by cubic spline) standard observer data
//
//	Author(s):		C.T. Yeung									cty
//
//	History:		
// 	20Dec08			first started								cty
//
// Copyright (c) 2009 C.T.Yeung
//
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:

// The above copyright notice and this permission notice shall be included
// in all copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
// IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
// CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
// TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
// SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
// ==================================================================
package com
{
	import flash.geom.Point;
	
	public class StandardObserver
	{
		public static const FUNC_2D_1931:String = "2degree1931";
		public static const FUNC_10D_1964:String = "10degree1964";
		
		public var stdObserver2Degree1931X:SpectralData;
		public var stdObserver2Degree1931Y:SpectralData;
		public var stdObserver2Degree1931Z:SpectralData;
		public var stdObserver10Degree1964X:SpectralData;
		public var stdObserver10Degree1964Y:SpectralData;
		public var stdObserver10Degree1964Z:SpectralData;
		public var count:int;
		public var functionType:String = FUNC_2D_1931;
		
		public function StandardObserver()
		{
			onInit();
		}

		protected function onInit():void
		{
			stdObserver2Degree1931X = new StandardObserver2Degree1931X();		
			stdObserver2Degree1931Y = new StandardObserver2Degree1931Y();	
			stdObserver2Degree1931Z = new StandardObserver2Degree1931Z();	
			stdObserver10Degree1964X = new StandardObserver10Degree1964X();		
			stdObserver10Degree1964Y = new StandardObserver10Degree1964Y();	
			stdObserver10Degree1964Z = new StandardObserver10Degree1964Z();	
			count = stdObserver2Degree1931X.count();				
		}
		
		public function get stdObserverX():SpectralData
		{
			if ( functionType == FUNC_2D_1931 )
				return stdObserver2Degree1931X;
			return stdObserver10Degree1964X;
		}
		
		public function get stdObserverY():SpectralData
		{
			if ( functionType == FUNC_2D_1931 )
				return stdObserver2Degree1931Y;
			return stdObserver10Degree1964Y;
		}
		
		public function get stdObserverZ():SpectralData
		{
			if ( functionType == FUNC_2D_1931 )
				return stdObserver2Degree1931Z;
			return stdObserver10Degree1964Z;
		}
	}
}