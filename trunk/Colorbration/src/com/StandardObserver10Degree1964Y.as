// ==================================================================
// Module:		StandardObserverY
//
// Description:	curve class for standard observer function
//
// Reference:	CIE Standard Observer y bar function, 10 degrees, 1964
//				source from Dr. Hunt's Color textbook.
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
	
	public class StandardObserver10Degree1964Y extends SpectralData
	{
		public function StandardObserver10Degree1964Y()
		{
			super();
		}
				
		override public function onInitPercentData():void
		{
			percent = new Array(.002, .0088, .0214, .0387, .0621, 
								.0895, .1282, .1852, .2536, .3391, 
								.4608, .6067, .7618, .8752, .9620, 
								.9918, .9973, .9556, .8689, .7774, 
								.6583, .5280, .3981, .2835, .1798, 
								.1076, .0603, .0318, .0159, .0077, .0037);
		}
	}
}