// ==================================================================
// Module:		StandardObserverX
//
// Description:	curve class for standard observer function
//
// Reference:	CIE Standard Observer x bar function, 10 degrees, 1964
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
	
	public class StandardObserver10Degree1964X extends SpectralData
	{
		public function StandardObserver10Degree1964X()
		{
			super();
		}
		
		override public function onInitPercentData():void
		{
			percent = new Array(.0191, .0847, .2045, .3147, .3837,  
								.3707, .3023, .1956, .0805, .0162, 
								.0038, .0375, .1177, .2365, .3768, 
								.5298, .7052, .8787, 1.0142, 1.1185, 
								1.1240, 1.0305, .8563, .6475, .4316, 
								.2683, .1526, .0813, .0409, .0199, .0096);
		}
	}
}