// ==================================================================
// Module:		IlluminantB
//
// Description:	a curve class for light source, B
//
// Reference:	Billmeyer & Saltzman, Principle of Color Technology
//
// Input:		x coordinate (wavelength)
// Output:		interpolated y coordinate data 
//				(percent reflectance, source)
//
// Author(s):	C.T. Yeung		cty
//
// History:
// 24Dec08		first started									cty
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
	public class IlluminantB extends SpectralData
	{
		public static const NAME:String = "B";
		public function IlluminantB()
		{
			super();
			name = NAME;
		}
		
		override public function onInitPercentData():void
		{						   
			percent = new Array(41.30, 52.10, 63.20, 73.10, 80.80, 
								85.40, 88.30, 92.00, 95.20, 96.50, 
								94.20, 90.70, 89.50, 92.20, 96.90, 
								101.00, 102.80, 102.60, 101.00, 98.20, 
								98.00, 98.50, 99.70, 101, 102.20, 
								103.90, 105, 104.90, 103.90, 101.60, 99.10);
		}
	}
}