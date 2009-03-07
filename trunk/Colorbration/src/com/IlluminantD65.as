// ==================================================================
// Module:		IlluminantD65
//
// Description:	a curve class for light source, D65
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
	public class IlluminantD65 extends SpectralData
	{
		public static const NAME:String = "D65";
		public function IlluminantD65()
		{
			super();
			name = NAME;
		}
		
		override public function onInitPercentData():void
		{						   
			percent = new Array(82.75, 91.49, 93.43, 86.68, 104.86, 
								117.01, 117.81, 114.86, 115.92, 108.81, 
								109.35, 107.80, 104.79, 107.69, 104.41, 
								104.05, 100.0, 96.33, 95.79, 88.69,
								90.01, 89.60, 87.70, 83.29, 83.70, 
								80.03, 80.21, 82.28, 78.28, 69.72,71.61);
		}
	}
}