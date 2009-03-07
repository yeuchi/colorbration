// ==================================================================
// Module:		StandardObserverX
//
// Description:	curve class for standard observer function
//
// Reference:	CIE Standard Observer x bar function, 2 degrees, 1931
//				source from Dr. Hunt's Measuring Color Text.
//
// Input:		wavelength between 400 to 700 nm (visible)
// Output:		interpolated standard observer data
//
// Author(s):	C.T. Yeung			cty
//
// History:
// 24Dec08		first started 									cty	
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
	
	public class StandardObserver2Degree1931X extends SpectralData
	{
		public function StandardObserver2Degree1931X()
		{
			super();
		}
		
		override public function onInitPercentData():void
		{
			percent = new Array(.0143, .0435, .1344, .2839, .3483, 
								 .3362, .2908, .1954, .0956, .0320, 
								 .0049, .0093, .0633, .1655, .2904, 
								 .4334, .5945, .7621, .9163, 1.0263, 
								 1.0622, 1.0026, .8544, .6424, .4479, 
								 .2835, .1649, .0874, .0468, .0227, 
								 .0114);
		}
	}
}